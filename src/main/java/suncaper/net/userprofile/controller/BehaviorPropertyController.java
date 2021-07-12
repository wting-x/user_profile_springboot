package suncaper.net.userprofile.controller;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Table;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import suncaper.net.userprofile.HBaseUtils;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/behaveProperty")
public class BehaviorPropertyController {
    @ResponseBody
    private List getColumns(String label){

        ArrayList<String> list = new ArrayList<>();

        HBaseUtils.getInstance();
        ResultScanner results = null;

        NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> navigableMap = null;

//        System.out.println("getColumns1");
        try {
            results = HBaseUtils.get("user_profile");
//            System.out.println("getColumns1");
            for (Result result : results) {
                navigableMap = result.getMap();
                for (byte[] family : navigableMap.keySet()) {
//                    System.out.println("列族:" + new String(family));
                    for (byte[] column : navigableMap.get(family).keySet()) {
//                            System.out.println("列:" + new String(column));
                        if (new String(column).equals(label)) {
                            for (Long t : navigableMap.get(family).get(column).keySet()) {
                                String temp = new String(navigableMap.get(family).get(column).get(t));
                                list.add(temp);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } /*finally {
            results.close();
            HBaseUtils.close();
        }*/
        System.out.println("#########"+label+"  "+list);
        return list;
    }

    //最近登录
    @RequestMapping("/getLatestLogin")
    @ResponseBody
    public Collection<Integer> getLatestLogin() {
        Map<String, Integer> LatestLogin = new TreeMap<>();
        LatestLogin.put("1天内",0);
        LatestLogin.put("7天内",0);
        LatestLogin.put("14天内",0);
        LatestLogin.put("30天内",0);
        LatestLogin.put("超过30天未登录",0);


        List<String> temp = getColumns("LatestLogin");

        for(String x:temp){
            LatestLogin.put(x,LatestLogin.get(x)+1);
        }

//        System.out.println(ageRange);
//        System.out.println(ageRange.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(LatestLogin.get("1天内"));
        list.add(LatestLogin.get("7天内"));
        list.add(LatestLogin.get("14天内"));
        list.add(LatestLogin.get("30天内"));
        list.add(LatestLogin.get("超过30天未登录"));

        System.out.println(list);

        return list;
    }

    //登录频率
    @RequestMapping("/getLoginFrequency")
    @ResponseBody
    public Collection<Integer> getLoginFrequency() {
        Map<String, Integer> LoginFrequency = new TreeMap<>();
        LoginFrequency.put("无",0);
        LoginFrequency.put("较少",0);
        LoginFrequency.put("一般",0);
        LoginFrequency.put("经常",0);


        List<String> temp = getColumns("LoginFrequency");

        for(String x:temp){
            LoginFrequency.put(x,LoginFrequency.get(x)+1);
        }


//        System.out.println(gender);
//        System.out.println(gender.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(LoginFrequency.get("无"));
        list.add(LoginFrequency.get("较少"));
        list.add(LoginFrequency.get("一般"));
        list.add(LoginFrequency.get("经常"));

        System.out.println(list);

        return list;
    }

    //浏览时段
    @RequestMapping("/getReadedTime_Browser")
    @ResponseBody
    public Collection<Integer> getReadedTime_Browser() {
        Map<String, Integer> ReadedTime_Browser = new TreeMap<>();
        ReadedTime_Browser.put("1点-7点",0);
        ReadedTime_Browser.put("8点-12点",0);
        ReadedTime_Browser.put("13点-17点",0);
        ReadedTime_Browser.put("18点-21点",0);
        ReadedTime_Browser.put("22点-24点",0);


        List<String> temp = getColumns("ReadedTime_Browser");

        for(String x:temp){
            ReadedTime_Browser.put(x,ReadedTime_Browser.get(x)+1);
        }

//        System.out.println(policy);
//        System.out.println(policy.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(ReadedTime_Browser.get("1点-7点"));
        list.add(ReadedTime_Browser.get("8点-12点"));
        list.add(ReadedTime_Browser.get("13点-17点"));
        list.add(ReadedTime_Browser.get("18点-21点"));
        list.add(ReadedTime_Browser.get("22点-24点"));

        System.out.println(list);

        return list;
    }

    //浏览页面
    @RequestMapping("/getbrowser_loc_url")
    @ResponseBody
    public Collection<Integer> getbrowser_loc_url() {
        Map<String, Integer> browser_loc_url = new TreeMap<>();
        browser_loc_url.put("登录页",0);
        browser_loc_url.put("首页",0);
        browser_loc_url.put("分类页",0);
        browser_loc_url.put("商品页",0);
        browser_loc_url.put("我的订单页",0);
        browser_loc_url.put("其他",0);

        List<String> temp = getColumns("browser_loc_url");

        for(String x:temp){
            browser_loc_url.put(x,browser_loc_url.get(x)+1);
        }

//        System.out.println(policy);
//        System.out.println(policy.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(browser_loc_url.get("登录页"));
        list.add(browser_loc_url.get("首页"));
        list.add(browser_loc_url.get("分类页"));
        list.add(browser_loc_url.get("商品页"));
        list.add(browser_loc_url.get("我的订单页"));
        list.add(browser_loc_url.get("其他"));


        System.out.println(list);

        return list;
    }

    //设备类型
    @RequestMapping("/getdevice")
    @ResponseBody
    public Collection<Integer> getdevice() {
        Map<String, Integer> device = new TreeMap<>();
        device.put("Windows",0);
        device.put("Mac",0);
        device.put("Linux",0);
        device.put("Android",0);
        device.put("IOS",0);

        List<String> temp = getColumns("device");

        for(String x:temp){
            device.put(x,device.get(x)+1);
        }

//        System.out.println(policy);
//        System.out.println(policy.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(device.get("Windows"));
        list.add(device.get("Mac"));
        list.add(device.get("Linux"));
        list.add(device.get("Android"));
        list.add(device.get("IOS"));


        System.out.println(list);

        return list;
    }

    //设备类型
    @RequestMapping("/getViewFrequency")
    @ResponseBody
    public Collection<Integer> getViewFrequency() {
        Map<String, Integer> ViewFrequency = new TreeMap<>();
        ViewFrequency.put("无",0);
        ViewFrequency.put("较少",0);
        ViewFrequency.put("一般",0);
        ViewFrequency.put("经常",0);

        List<String> temp = getColumns("ViewFrequency");

        for(String x:temp){
            System.out.println(ViewFrequency.get(x));
            ViewFrequency.put(x,ViewFrequency.get(x)+1);
        }

//        System.out.println(policy);
//        System.out.println(policy.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(ViewFrequency.get("无"));
        list.add(ViewFrequency.get("较少"));
        list.add(ViewFrequency.get("一般"));
        list.add(ViewFrequency.get("经常"));

        System.out.println(list);

        return list;
    }

    //浏览时常
    @RequestMapping("/getWatchTime")
    @ResponseBody
    public Collection<Integer> getWatchTime() {
        Map<String, Integer> WatchTime = new TreeMap<>();
        WatchTime.put("1分钟内",0);
        WatchTime.put("1-5分钟",0);
        WatchTime.put("5分钟以上",0);

        List<String> temp = getColumns("WatchTime");

        for(String x:temp){
            WatchTime.put(x,WatchTime.get(x)+1);
        }

//        System.out.println(policy);
//        System.out.println(policy.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(WatchTime.get("1分钟内"));
        list.add(WatchTime.get("1-5分钟"));
        list.add(WatchTime.get("5分钟以上"));

        System.out.println(list);

        return list;
    }


}