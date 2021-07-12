package suncaper.net.userprofile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.*;

/**
 * HBase工具类 Author zzq
 */
public class HBaseUtils {
    private static Connection connection;
    private static Configuration configuration;
    private static HBaseUtils hBaseUtils;
    private static Properties properties;

    /**
     * 创建连接池并初始化环境配置
     */
    public void init() {
        properties = System.getProperties();
        // 实例化HBase配置类
        if (configuration == null) {
            configuration = HBaseConfiguration.create();
        }
        try {
            // 加载本地hadoop二进制包，换成你解压的地址
            properties.setProperty("hadoop.home.dir", "E:\\springboot\\hadoop-common-2.7.3-bin-master");
            // zookeeper集群的URL配置信息
            configuration.set("hbase.zookeeper.quorum", "master,slave01,slave02");
            // HBase的Master
            configuration.set("hbase.master", "master:90000");
            // 客户端连接zookeeper端口
            configuration.set("hbase.zookeeper.property.clientPort", "2181");
            // HBase RPC请求超时时间，默认60s(60000)
            configuration.setInt("hbase.rpc.timeout", 20000);
            // 客户端重试最大次数，默认35
            configuration.setInt("hbase.client.retries.number", 10);
            // 客户端发起一次操作数据请求直至得到响应之间的总超时时间，可能包含多个RPC请求，默认为2min
            configuration.setInt("hbase.client.operation.timeout", 30000);
            // 客户端发起一次scan操作的rpc调用至得到响应之间的总超时时间
            configuration.setInt("hbase.client.scanner.timeout.period", 200000);
            // 获取hbase连接对象
            if (connection == null || connection.isClosed()) {
                connection = ConnectionFactory.createConnection(configuration);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接池
     */
    public static void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 私有无参构造方法
     */
    private HBaseUtils() {
    }

    /**
     * 唯一实例，线程安全，保证连接池唯一
     *
     * @return
     */
    public static HBaseUtils getInstance() {
        if (hBaseUtils == null) {
            synchronized (HBaseUtils.class) {
                if (hBaseUtils == null) {
                    hBaseUtils = new HBaseUtils();
                    hBaseUtils.init();
                }
            }
        }
        return hBaseUtils;
    }


    /**
     * 单行插入数据
     *
     * @param tablename
     * @param rowkey
     * @param family
     * @param cloumns
     * @throws IOException
     */
    public static void put(String tablename, String rowkey, String family, Map<String, String> cloumns)
            throws IOException {
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf(tablename));
            Put put = new Put(rowkey.getBytes());
            for (Map.Entry<String, String> entry : cloumns.entrySet()) {
                put.addColumn(family.getBytes(), entry.getKey().getBytes(), entry.getValue().getBytes());
            }
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            table.close();
            close();
        }
    }

    /**
     * 获取单条数据
     *
     * @param tablename
     * @param row
     * @return
     * @throws IOException
     */
    public static Result getRow(String tablename, String row) throws IOException {
        Table table = null;
        Result result = null;
        try {
            table = connection.getTable(TableName.valueOf(tablename));
            Get get = new Get(row.getBytes());
            result = table.get(get);
        } finally {
            table.close();
        }
        return result;
    }

    /**
     * 查询多行信息
     *
     * @param tablename
     * @param rows
     * @return
     * @throws IOException
     */
    public static Result[] getRows(String tablename, List<byte[]> rows) throws IOException {
        Table table = null;
        List<Get> gets = null;
        Result[] results = null;
        try {
            table = connection.getTable(TableName.valueOf(tablename));
            gets = new ArrayList<Get>();
            for (byte[] row : rows) {
                if (row != null) {
                    gets.add(new Get(row));
                }
            }
            if (gets.size() > 0) {
                results = table.get(gets);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            table.close();
        }
        return results;
    }

    /**
     * 获取整表数据
     *
     * @param tablename
     * @return
     */
    public static ResultScanner get(String tablename) throws IOException {
        Table table = null;
        ResultScanner results = null;
        try {
            table = connection.getTable(TableName.valueOf(tablename));
            Scan scan = new Scan();
            scan.setCaching(1000);
            results = table.getScanner(scan);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            table.close();
        }
        return results;
    }
    /**
     * 查找一行记录
     *
     * @param tablename         表名
     * @param rowKey            行名
     */
    public static String selectRow(String tablename, String rowKey) throws IOException {
        String record = "";
        TableName name=TableName.valueOf(tablename);
        Table table = connection.getTable(name);
        Get g = new Get(rowKey.getBytes());
        Result rs = table.get(g);
        NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map = rs.getMap();
        for (Cell cell : rs.rawCells()) {
            StringBuffer stringBuffer = new StringBuffer()
                    .append(Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength())).append("\t")
                    .append(Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength())).append("\t")
                    .append(Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength())).append("\t")
                    .append(Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength())).append("\n");
            String str = stringBuffer.toString();
            record += str;
        }
        return record;
    }

    /**
     * 查找单行单列族单列记录
     *
     * @param tablename         表名
     * @param rowKey            行名
     * @param columnFamily      列族名
     * @param column            列名
     * @return
     */
    public static String selectValue(String tablename, String rowKey, String columnFamily, String column) throws IOException {
        TableName name=TableName.valueOf(tablename);
        Table table = connection.getTable(name);
        Get g = new Get(rowKey.getBytes());
        g.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
        Result rs = table.get(g);
        return Bytes.toString(rs.value());
    }

    /**
     * 删除数据
     *
     * @param tablename
     * @param family
     * @param column
     * @param row
     * @throws IOException
     */
    public static void delete(String tablename, String family, String column, String row) throws IOException {
        Table table = null;

        try {
            table = connection.getTable(TableName.valueOf(tablename));
            Delete del = new Delete(row.getBytes());
            del.addColumns(family.getBytes(), column.getBytes());
            table.delete(del);
        } finally {
            table.close();
        }

    }

}
