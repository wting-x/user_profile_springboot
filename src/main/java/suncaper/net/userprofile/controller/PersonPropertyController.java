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
@RequestMapping("/personProperty")
public class PersonPropertyController {
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

    @RequestMapping("/getAgeRange")
    @ResponseBody
    public Collection<Integer> getAgeRange() {
        Map<String, Integer> ageRange = new TreeMap<>();
        ageRange.put("50后",0);
        ageRange.put("60后",0);
        ageRange.put("70后",0);
        ageRange.put("80后",0);
        ageRange.put("90后",0);
        ageRange.put("00后",0);
        ageRange.put("10后",0);
        ageRange.put("20后",0);

        List<String> temp = getColumns("ageRange");

        for(String x:temp){
            ageRange.put(x,ageRange.get(x)+1);
        }

//        System.out.println(ageRange);
//        System.out.println(ageRange.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(ageRange.get("50后"));
        list.add(ageRange.get("60后"));
        list.add(ageRange.get("70后"));
        list.add(ageRange.get("80后"));
        list.add(ageRange.get("90后"));
        list.add(ageRange.get("00后"));
        list.add(ageRange.get("10后"));
        list.add(ageRange.get("20后"));

        System.out.println(list);

        return list;
    }

    @RequestMapping("/getGender")
    @ResponseBody
    public Collection<Integer> getGender() {
        Map<String, Integer> gender = new TreeMap<>();
        gender.put("男",0);
        gender.put("女",0);

        List<String> temp = getColumns("gender");

        for(String x:temp){
            gender.put(x,gender.get(x)+1);
        }


//        System.out.println(gender);
//        System.out.println(gender.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(gender.get("男"));
        list.add(gender.get("女"));

        System.out.println(list);

        return list;
    }

    @RequestMapping("/getPolicy")
    @ResponseBody
    public Collection<Integer> getPolicy() {
        Map<String, Integer> policy = new TreeMap<>();
        policy.put("群众",0);
        policy.put("党员",0);
        policy.put("无党派人士",0);

        List<String> temp = getColumns("politicalFace");

        for(String x:temp){
            policy.put(x,policy.get(x)+1);
        }

//        System.out.println(policy);
//        System.out.println(policy.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(policy.get("群众"));
        list.add(policy.get("党员"));
        list.add(policy.get("无党派人士"));

        System.out.println(list);

        return list;
    }

    @RequestMapping("/getJob")
    @ResponseBody
    public Collection<Integer> getJob() {
        Map<String, Integer> job = new TreeMap<>();
        job.put("学生",0);
        job.put("公务员",0);
        job.put("军人",0);
        job.put("警察",0);
        job.put("教师",0);
        job.put("白领",0);

        List<String> temp = getColumns("job");

        for(String x:temp){
            job.put(x,job.get(x)+1);
        }

//        System.out.println(job);
//        System.out.println(job.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(job.get("学生"));
        list.add(job.get("公务员"));
        list.add(job.get("军人"));
        list.add(job.get("警察"));
        list.add(job.get("教师"));
        list.add(job.get("白领"));

        System.out.println(list);

        return list;
    }

    @RequestMapping("/getMarriage")
    @ResponseBody
    public Collection<Integer> getMarriage() {
        Map<String, Integer> marriage = new TreeMap<>();
        marriage.put("已婚",0);
        marriage.put("未婚",0);
        marriage.put("离异",0);

        List<String> temp = getColumns("marriage");

        for(String x:temp){
            marriage.put(x,marriage.get(x)+1);
        }

//        System.out.println(marriage);
//        System.out.println(marriage.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(marriage.get("已婚"));
        list.add(marriage.get("未婚"));
        list.add(marriage.get("离异"));

        System.out.println(list);

        return list;
    }

    @RequestMapping("/getConstellation")
    @ResponseBody
    public Collection<Integer> getConstellation() {
        Map<String, Integer> constellation = new TreeMap<>();
        constellation.put("白羊座",0);
        constellation.put("金牛座",0);
        constellation.put("双子座",0);
        constellation.put("巨蟹座",0);
        constellation.put("狮子座",0);
        constellation.put("处女座",0);
        constellation.put("天秤座",0);
        constellation.put("天蝎座",0);
        constellation.put("人马座",0);
        constellation.put("摩羯座",0);
        constellation.put("水瓶座",0);
        constellation.put("双鱼座",0);

        List<String> temp = getColumns("constellation");

        System.out.println(temp);

        for(String x:temp){
            constellation.put(x,constellation.get(x)+1);
        }

//        System.out.println(constellation);
//        System.out.println(constellation.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(constellation.get("白羊座"));
        list.add(constellation.get("金牛座"));
        list.add(constellation.get("双子座"));
        list.add(constellation.get("巨蟹座"));
        list.add(constellation.get("狮子座"));
        list.add(constellation.get("处女座"));
        list.add(constellation.get("天秤座"));
        list.add(constellation.get("天蝎座"));
        list.add(constellation.get("人马座"));
        list.add(constellation.get("摩羯座"));
        list.add(constellation.get("水瓶座"));
        list.add(constellation.get("双鱼座"));

        System.out.println(list);

        return list;
    }

    @RequestMapping("/getNationality")
    @ResponseBody
    public Collection<Integer> getNationality() {
        Map<String, Integer> nationality = new TreeMap<>();
        nationality.put("中国大陆",0);
        nationality.put("中国香港",0);
        nationality.put("中国澳门",0);
        nationality.put("中国台湾",0);
        nationality.put("其他",0);

        List<String> temp = getColumns("nationality");

        System.out.println(temp);

        for(String x:temp){
            nationality.put(x,nationality.get(x)+1);
        }

//        System.out.println(nationality);
//        System.out.println(nationality.values());

        //return nationality.values();
        Collection<Integer> list = new ArrayList<>();

        list.add(nationality.get("中国大陆"));
        list.add(nationality.get("中国香港"));
        list.add(nationality.get("中国澳门"));
        list.add(nationality.get("中国台湾"));
        list.add(nationality.get("其他"));

        System.out.println(list);

        return list;
    }

    @RequestMapping("/getjiguan")
    @ResponseBody
    public Collection<Integer> getjiguan() {
        Map<String, Integer> jiguan = new TreeMap<>();
        jiguan.put("北京市",0);
        jiguan.put("werwer",0);
        jiguan.put("宁夏中卫",0);
        jiguan.put("安徽合肥",0);
        jiguan.put("江西抚州",0);
        jiguan.put("重庆市",0);
        jiguan.put("江苏苏州",0);
        jiguan.put("江西南昌",0);
        jiguan.put("河北保定",0);
        jiguan.put("山东青岛",0);
        jiguan.put("山东济南",0);
        jiguan.put("甘肃兰州",0);
        jiguan.put("甘肃白银",0);
        jiguan.put("福建福州",0);
        jiguan.put("福建三明",0);
        jiguan.put("福建莆田",0);
        jiguan.put("广东汕头",0);
        jiguan.put("广东韶关",0);
        jiguan.put("黑龙江黑河",0);
        jiguan.put("黑龙江大兴安岭",0);
        jiguan.put("黑龙江齐齐哈尔",0);
        jiguan.put("黑龙江绥化",0);
        jiguan.put("内蒙古鄂尔多斯",0);
        jiguan.put("内蒙古呼和浩特",0);
        jiguan.put("null",0);

        List<String> temp = getColumns("jiguan");

        System.out.println(temp);

        for(String x:temp){
            jiguan.put(x,jiguan.get(x)+1);
        }

//        System.out.println(nationality);
//        System.out.println(nationality.values());

        //return nationality.values();
        Collection<Integer> list = new ArrayList<>();

        list.add(jiguan.get("北京市"));
        list.add(jiguan.get("宁夏中卫"));
        list.add(jiguan.get("安徽合肥"));
        list.add(jiguan.get("江西抚州"));
        list.add(jiguan.get("重庆市"));
        list.add(jiguan.get("江苏苏州"));
        list.add(jiguan.get("江西南昌"));
        list.add(jiguan.get("河北保定"));
        list.add(jiguan.get("山东青岛"));
        list.add(jiguan.get("山东济南"));
        list.add(jiguan.get("甘肃兰州"));
        list.add(jiguan.get("甘肃白银"));
        list.add(jiguan.get("福建福州"));
        list.add(jiguan.get("福建三明"));
        list.add(jiguan.get("福建莆田"));
        list.add(jiguan.get("广东汕头"));
        list.add(jiguan.get("广东韶关"));
        list.add(jiguan.get("黑龙江黑河"));
        list.add(jiguan.get("黑龙江大兴安岭"));
        list.add(jiguan.get("黑龙江齐齐哈尔"));
        list.add(jiguan.get("黑龙江绥化"));
        list.add(jiguan.get("内蒙古鄂尔多斯"));
        list.add(jiguan.get("内蒙古呼和浩特"));
        list.add(jiguan.get("null"));

        System.out.println(list);

        return list;
    }

}