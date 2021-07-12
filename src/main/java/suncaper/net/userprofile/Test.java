package suncaper.net.userprofile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import java.io.IOException;
import java.util.*;

@Controller
public class Test {

    @ResponseBody
    @RequestMapping("/a")
    public String hello() {
        return "hello";
    }

    /**
     * 增加数据
     */
    /*@ResponseBody
    @RequestMapping("/putdata")
    public void putdata() {
        HBaseUtils.getInstance();
        try {
            Map<String, String> cloumns = new HashMap<String, String>();
            cloumns.put("name", "zzq");
            cloumns.put("age", "22");
            HBaseUtils.put("user_profile", "id", "cf", cloumns);
            System.out.println("增加成功");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("增加失败");
        } finally {
            HBaseUtils.close();
        }
    }*/

    /**
     * 获取整表数据
     */
    @ResponseBody
    @RequestMapping("/getTableAllData")
    public void getTableAllData() {
        HBaseUtils.getInstance();
        ResultScanner results = null;
        try {
            results = HBaseUtils.get("user_profile");

            for (Result result : results) {
                NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> navigableMap = result.getMap();
                for (byte[] family : navigableMap.keySet()) {
                    System.out.println("列族:" + new String(family));
                    for (byte[] column : navigableMap.get(family).keySet()) {
                        System.out.println("列:" + new String(column));
                        for (Long t : navigableMap.get(family).get(column).keySet()) {
                            System.out.println("值:" + new String(navigableMap.get(family).get(column).get(t)));
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            results.close();
            HBaseUtils.close();
        }
    }



    /**
     * 删除数据
     */
    @ResponseBody
    @RequestMapping("/deletedata")
    public void delete() {
        HBaseUtils.getInstance();
        try {
            HBaseUtils.delete("person", "info", "name", "1");
            System.out.println("删除成功");
        } catch (IOException e) {
            System.out.println("删除失败");
            e.printStackTrace();
        }
    }

}

