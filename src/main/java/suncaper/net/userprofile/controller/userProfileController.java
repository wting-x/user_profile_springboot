package suncaper.net.userprofile.controller;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Table;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import suncaper.net.userprofile.HBaseUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("")

public class userProfileController {

    private List<String> toList(Map<String,String> map){
        List<String> list = new ArrayList();

        list.add(map.get("gender"));//0
        list.add(map.get("ageRange"));//1
        list.add(map.get("nationality"));//2
        list.add(map.get("jiguan"));//3
        list.add(map.get("politicalFace"));//4
        list.add(map.get("constellation"));
        list.add(map.get("job"));
        list.add(map.get("marriage"));
        list.add(map.get("paymentCode"));
        list.add(map.get("consumptionCycle"));//0-9

        list.add(map.get("purchaseFrequency"));
        list.add(map.get("ComsumptionAbility"));
        list.add(map.get("discount"));
        list.add(map.get("AdvisoryServiceFrequency"));
        list.add(map.get("replacementRate"));
        list.add(map.get("returnRate"));
        list.add(map.get("PerCustomerTransation"));
        list.add(map.get("MaxSingleComsumption"));
        list.add(map.get("LatestLogin"));
        list.add(map.get("LoginFrequency"));//10-19

        list.add(map.get("browser_loc_url"));
        list.add(map.get("WatchTime"));
        list.add(map.get("ReadedTime_Browser"));
        list.add(map.get("ViewFrequency"));
        list.add(map.get("favorProducts"));
        list.add(map.get("favorProductsType"));
        list.add(map.get("favorBrandName"));
        list.add(map.get("ViewGoods"));
        list.add(map.get("purchaseGoods"));
        list.add(map.get("PriceSensitivity"));//20-29

        list.add(map.get("userValue"));//30
        list.add(map.get("ID"));//31
        list.add(map.get("activity"));//32
        list.add(map.get("coupon"));//33
        list.add(map.get("promotion"));//34
        list.add(map.get("Coupon"));//35

        list.add(map.get("productName0"));//36
        list.add(map.get("productName1"));//37
        list.add(map.get("productName2"));//38
        list.add(map.get("productName3"));//39
        list.add(map.get("productName4"));//40
        list.add(map.get("productName5"));//41
        list.add(map.get("productName6"));//42
        list.add(map.get("productName7"));//43
        list.add(map.get("productName8"));//44
        list.add(map.get("device"));//45
        list.add(map.get("favorProductsId"));//46




//        System.out.println("&&&&&&&&&&&&&&&&&&&"+list);
        return list;
    }

    private Collection<String> getRowkeyRow(long t) {

        long temp = 0;

        HBaseUtils.getInstance();
        ResultScanner results = null;
        Map<String,String> map = new TreeMap<>();

        try {
            results = HBaseUtils.get("user_profile");
            for (Result result : results) {
                NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> navigableMap = result.getMap();
                for (byte[] family : navigableMap.keySet()) {
                    temp+=1;
                    if(temp==t){
                        for (byte[] column : navigableMap.get(family).keySet()) {
                            /*System.out.println("列:" + new String(column));*/
                            for (Long i: navigableMap.get(family).get(column).keySet()) {
                                map.put(new String(column),new String(navigableMap.get(family).get(column).get(i)));
                                System.out.print("");
                            }
                        }
                    }
//                    System.out.println("列族:" + new String(family));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } /*finally {
            System.out.println(map);
            results.close();
            HBaseUtils.close();
        }*/

        Collection<String> list = toList(map);

        return list;
    }

    //搜索rowkey指定数据
    @RequestMapping(path = "/rowkeySearch" , method = RequestMethod.GET)
    public ModelAndView getRowkeySearch(@RequestParam("rowkey") String t){
        ModelAndView mav=new ModelAndView("userprofile");

//        Integer has = uService.getnumByState();
//        System.out.println(has);
//        boolean hasloginuser = false;
//        users user = new users();
//        //ModelAndView mav=new ModelAndView("index");
//        if(has==1){
//            user = uService.findByUstate();
//            hasloginuser = true;
//        }
//        mav.addObject("goods",gService.findGoodsByTitle(t));
//        mav.addObject("keyValue",t);
//        mav.addObject("hasloginuser",hasloginuser);
//        mav.addObject("loginuser",user);

//        System.out.println(t);
        System.out.println("###############"+getRowkeyRow(Long.valueOf(t).longValue()));

        mav.addObject("user",getRowkeyRow(Long.valueOf(t).longValue()));
        mav.addObject("rowkey",t);

        return mav;
    }

    private ArrayList<List<String>> getLabelRow(String label, String value) {

//        map.put(new String(column),new String(navigableMap.get(family).get(column).get(i)));

        HBaseUtils.getInstance();
        ResultScanner results = null;
        ArrayList<List<String>> collection = new ArrayList<>();

//        long temp = 0;
        try {
            results = HBaseUtils.get("user_profile");
            for (Result result : results) {
                NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> navigableMap = result.getMap();
                for (byte[] family : navigableMap.keySet()) {
                    int state = 0;
                    Map<byte[], NavigableMap<Long, byte[]>> temp = navigableMap.get(family);
//                    temp+=1;
                    for (byte[] column : navigableMap.get(family).keySet()) {
//                        /*System.out.println("列:" + new String(column));*/
                        if(new String(column).equals(label)){
                            for (Long i: navigableMap.get(family).get(column).keySet()) {

                                if(new String(temp.get(column).get(i)).equals(value)){
                                    state = 1;
                                    break;
                                }

                            }
                            if(state==1)
                                break;
                        }
                    }

                    if(state ==1){
                        Map<String,String> map = new TreeMap<>();
                        for (byte[] column : temp.keySet()) {
                            /*System.out.println("列:" + new String(column));*/
                            for (Long i: temp.get(column).keySet()) {
                                map.put(new String(column),new String(temp.get(column).get(i)));
                            }
                        }
                        collection.add(toList(map));
                    }

//                    System.out.println("列族:" + new String(family));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } /*finally {
            System.out.println(map);
            results.close();
            HBaseUtils.close();
        }*/

        return collection;
    }

    //搜索label指定数据
    @RequestMapping(path = "/labelSearch" , method = RequestMethod.GET)
    public ModelAndView getLabelSearch(@RequestParam("criteria") String criteria){

//        System.out.println(criteria);

        String[] temp0 = criteria.split("\"");
        String[] temp = temp0[1].split(":");

        System.out.println(temp[0]+"  "+temp[1]);
        ModelAndView mav=new ModelAndView("search");

        mav.addObject("state",criteria);
        mav.addObject("users",getLabelRow(temp[0],temp[1]));
//        ArrayList<List<String>> a = getLabelRow(temp[0],temp[1]);
//        for(List<String> t:a){
//            System.out.println(t.get(0));
//        }

        return mav;
    }


    private ArrayList<List<String>> getCombineRow(String[] temp1){
        Map<List<String>,Integer> map = new HashMap<>();
        int state=0;
        for(String t:temp1){
            state++;
            String[] arr = t.split("/");
            for(String x:arr){
                String[] temp = x.split(":");
                String label = temp[0];
                String value = temp[1];

//                System.out.println(label+":"+value);

                ArrayList<List<String>> test = getLabelRow(label,value);
                for(List<String> l:test){
//                    System.out.println(l);
                    if(map.containsKey(l)){
                        map.put(l,map.get(l)+1);
                    }
                    else {
                        map.put(l,1);
                    }
                }
            }
        }
        ArrayList<List<String>> collection = new ArrayList<>();

        for(List<String> l:map.keySet()){
            if(map.get(l) == state){
                collection.add(l);
            }
            else {
                continue;
            }
        }
        return collection;
    }

    //搜索组合标签指定数据
    @RequestMapping(path = "/combineSearch" , method = RequestMethod.GET)
    public ModelAndView getCombineSearch(@RequestParam("criteria") String criteria){

        String[] temp0 = criteria.split("\"");
        String[] temp1 = temp0[1].split(",");

        ModelAndView mav=new ModelAndView("search");

        mav.addObject("state",criteria);
        mav.addObject("users",getCombineRow(temp1));

        return mav;
    }

    private boolean loginSearch(long t, String password){

        boolean state = false;

        long temp = 0;

        HBaseUtils.getInstance();
        ResultScanner results = null;
        Map<String,String> user = new TreeMap<>();

        try {
            results = HBaseUtils.get("tbl_users");
            for (Result result : results) {
                NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> navigableMap = result.getMap();
                for (byte[] family : navigableMap.keySet()) {
                    temp++;
                    if(temp == t){
                        for (byte[] column : navigableMap.get(family).keySet()) {
                            /*System.out.println("列:" + new String(column));*/
                            for (Long i: navigableMap.get(family).get(column).keySet()) {
                                user.put(new String(column),new String(navigableMap.get(family).get(column).get(i)));
                            }
                        }
                    }
//                    System.out.println("列族:" + new String(family));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } /*finally {
            System.out.println(map);
            results.close();
            HBaseUtils.close();
        }*/

        if(user.containsKey("password") && user.get("password").equals(password))
            state = true;

        return state;
    }

    //登录
    @RequestMapping(path = "/loginSearch" , method = RequestMethod.GET)
    public ModelAndView login(@RequestParam("criteria") String criteria){

        String[] temp0 = criteria.split("\"");
        String[] temp1 = temp0[1].split(":");

        System.out.println(temp1[0]+" "+temp1[1]);

        ModelAndView mav=new ModelAndView("login");

        boolean s = loginSearch(Long.valueOf(temp1[0]).longValue(),temp1[1]);

        if(s){
            return getRowkeySearch(temp1[0]);
        }
        else {
            mav.addObject("state",!s);
            return mav;
        }
    }

}
