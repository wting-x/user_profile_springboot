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
@RequestMapping("/marketProperty")
public class MarketPropertyController {

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

    @RequestMapping("/getConsumptionCycle")
    @ResponseBody
    public Collection<Integer> getConsumptionCycle() {
        Map<String, Integer> consumptionCycle = new HashMap<>();
        consumptionCycle.put("7日",0);
        consumptionCycle.put("2周",0);
        consumptionCycle.put("1月",0);
        consumptionCycle.put("2月",0);
        consumptionCycle.put("3月",0);
        consumptionCycle.put("4月",0);
        consumptionCycle.put("5月",0);
        consumptionCycle.put("6月",0);

        List<String> temp = getColumns("consumptionCycle");

        for(String x:temp){
            consumptionCycle.put(x,consumptionCycle.get(x)+1);
        }

        System.out.println(consumptionCycle);
        System.out.println(consumptionCycle.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(consumptionCycle.get("7日"));
        list.add(consumptionCycle.get("2周"));
        list.add(consumptionCycle.get("1月"));
        list.add(consumptionCycle.get("2月"));
        list.add(consumptionCycle.get("3月"));
        list.add(consumptionCycle.get("4月"));
        list.add(consumptionCycle.get("5月"));
        list.add(consumptionCycle.get("6月"));


        System.out.println(list);

        return list;
    }

    @RequestMapping("/getAdvisoryServiceFrequency")
    @ResponseBody
    public Collection<Integer> getAdvisoryServiceFrequency() {
        Map<String, Integer> AdvisoryServiceFrequency = new HashMap<>();
        AdvisoryServiceFrequency.put("高",0);
        AdvisoryServiceFrequency.put("中",0);
        AdvisoryServiceFrequency.put("低",0);

        List<String> temp = getColumns("AdvisoryServiceFrequency");

        for(String x:temp){
            AdvisoryServiceFrequency.put(x,AdvisoryServiceFrequency.get(x)+1);
        }

        System.out.println(AdvisoryServiceFrequency);
        System.out.println(AdvisoryServiceFrequency.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(AdvisoryServiceFrequency.get("高"));
        list.add(AdvisoryServiceFrequency.get("中"));
        list.add(AdvisoryServiceFrequency.get("低"));

        System.out.println(list);

        return list;
    }

    @RequestMapping("/getComsumptionAbility")
    @ResponseBody
    public Collection<Integer> getComsumptionAbility() {
        Map<String, Integer> ComsumptionAbility = new HashMap<>();
        ComsumptionAbility.put("超高",0);
        ComsumptionAbility.put("高",0);
        ComsumptionAbility.put("中上",0);
        ComsumptionAbility.put("中",0);
        ComsumptionAbility.put("中下",0);
        ComsumptionAbility.put("低",0);
        ComsumptionAbility.put("很低",0);

        List<String> temp = getColumns("ComsumptionAbility");

        for(String x:temp){
            ComsumptionAbility.put(x,ComsumptionAbility.get(x)+1);
        }

        System.out.println(ComsumptionAbility);
        System.out.println(ComsumptionAbility.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(ComsumptionAbility.get("超高"));
        list.add(ComsumptionAbility.get("高"));
        list.add(ComsumptionAbility.get("中上"));
        list.add(ComsumptionAbility.get("中"));
        list.add(ComsumptionAbility.get("中下"));
        list.add(ComsumptionAbility.get("低"));
        list.add(ComsumptionAbility.get("很低"));

        System.out.println(list);

        return list;
    }

    @RequestMapping("/getMaxSingleComsumption")
    @ResponseBody
    public Collection<Integer> getMaxSingleComsumption () {
        Map<String, Integer> MaxSingleComsumption = new HashMap<>();
        MaxSingleComsumption.put(">50000",0);
        MaxSingleComsumption.put("30000-49999",0);
        MaxSingleComsumption.put("10000-29999",0);
        MaxSingleComsumption.put("1-9999",0);

        System.out.println(MaxSingleComsumption);
        System.out.println(MaxSingleComsumption.values());

        List<String> temp = getColumns("MaxSingleComsumption");

        for(String x:temp){
            MaxSingleComsumption.put(x,MaxSingleComsumption.get(x)+1);
        }

//        System.out.println(ComsumptionAbility);
//        System.out.println(ComsumptionAbility.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(MaxSingleComsumption.get("1-9999"));
        list.add(MaxSingleComsumption.get("10000-29999"));
        list.add(MaxSingleComsumption.get("30000-49999"));
        list.add(MaxSingleComsumption.get(">50000"));

        System.out.println(list);

        return list;
    }



    @RequestMapping("/getPaymentCode")
    @ResponseBody
    public Collection<Integer> getPaymentCode () {
        Map<String, Integer> paymentCode  = new HashMap<>();
        paymentCode .put("alipay",0);
        paymentCode .put("cod",0);
        paymentCode .put("kjtpay",0);
        paymentCode .put("wspay",0);
        paymentCode .put("ccb",0);
        paymentCode .put("chinapay",0);
        paymentCode .put("insidestatement",0);
        paymentCode .put("prepaid",0);
        paymentCode .put("giftCard",0);
        paymentCode .put("balance",0);
        paymentCode .put("wxpay",0);
        paymentCode .put("chinaecpay",0);
        paymentCode .put("offline",0);

        List<String> temp = getColumns("paymentCode");

        for(String x:temp){
            paymentCode.put(x,paymentCode.get(x)+1);
        }

//        System.out.println(paymentCode);
//        System.out.println(paymentCode.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(paymentCode .get("alipay"));
        list.add(paymentCode .get("wxpay"));
        list.add(paymentCode .get("giftCard"));
        list.add(paymentCode .get("kjtpay"));

        System.out.println(list);

        return list;
    }

    @RequestMapping("/getPerCustomerTransation")
    @ResponseBody
    public Collection<Integer> getPerCustomerTransation () {
        Map<String, Integer> PerCustomerTransation  = new HashMap<>();
        PerCustomerTransation .put(">5000",0);
        PerCustomerTransation .put("3000-4999",0);
        PerCustomerTransation .put("1000-2999",0);
        PerCustomerTransation .put("1-999",0);

        List<String> temp = getColumns("PerCustomerTransation");

        for(String x:temp){
            PerCustomerTransation.put(x,PerCustomerTransation.get(x)+1);
        }

//        System.out.println(paymentCode);
//        System.out.println(paymentCode.values());

        Collection<Integer> list = new ArrayList<>();

        list.add(PerCustomerTransation .get("1-999"));
        list.add(PerCustomerTransation .get("1000-2999"));
        list.add(PerCustomerTransation .get("3000-4999"));
        list.add(PerCustomerTransation .get(">5000"));

        System.out.println(list);

        return list;
    }

    @RequestMapping("/getPurchaseFrequency")
    @ResponseBody
    public Collection<Integer> getPurchaseFrequency () {
        Map<String, Integer> PerCustomerTransation  = new HashMap<>();
        PerCustomerTransation .put("高",0);
        PerCustomerTransation .put("中",0);
        PerCustomerTransation .put("低",0);

        List<String> temp = getColumns("purchaseFrequency");

        for(String x:temp){
            PerCustomerTransation.put(x,PerCustomerTransation.get(x)+1);
        }

        Collection<Integer> list = new ArrayList<>();

        list.add(PerCustomerTransation .get("高"));
        list.add(PerCustomerTransation .get("中"));
        list.add(PerCustomerTransation .get("低"));

        System.out.println(list);

        return list;
    }

    @RequestMapping("/getReplacementRate")
    @ResponseBody
    public Collection<Integer> getReplacementRate () {
        Map<String, Integer> replacementRate  = new HashMap<>();
        replacementRate .put("高",0);
        replacementRate .put("中",0);
        replacementRate .put("低",0);

        List<String> temp = getColumns("replacementRate");

        for(String x:temp){
            replacementRate.put(x,replacementRate.get(x)+1);
        }

        Collection<Integer> list = new ArrayList<>();

        list.add(replacementRate .get("高"));
        list.add(replacementRate .get("中"));
        list.add(replacementRate .get("低"));

        System.out.println(list);

        return list;
    }

    @RequestMapping("/getReturnRate")
    @ResponseBody
    public Collection<Integer> getReturnRate () {
        Map<String, Integer> returnRate  = new HashMap<>();
        returnRate .put("高",0);
        returnRate .put("中",0);
        returnRate .put("低",0);

        List<String> temp = getColumns("returnRate");

        for(String x:temp){
            returnRate.put(x,returnRate.get(x)+1);
        }

        Collection<Integer> list = new ArrayList<>();

        list.add(returnRate .get("高"));
        list.add(returnRate .get("中"));
        list.add(returnRate .get("低"));

        System.out.println(list);

        return list;
    }

    //省钱能手
    @RequestMapping("/getdiscount")
    @ResponseBody
    public Collection<Integer> getdiscount () {
        Map<String, Integer> discount  = new HashMap<>();
        discount.put("8折-9折",0);
        discount.put("5折-7折",0);
        discount.put("3折-4折",0);
        discount.put("未知",0);

        List<String> temp = getColumns("discount");

        for(String x:temp){
            discount.put(x,discount.get(x)+1);
        }

        Collection<Integer> list = new ArrayList<>();

        list.add(discount.get("8折-9折"));
        list.add(discount.get("5折-7折"));
        list.add(discount.get("3折-4折"));
        list.add(discount.get("未知"));

        System.out.println(list);

        return list;
    }

    //有券必买
    @RequestMapping("/getCoupon")
    @ResponseBody
    public Collection<Integer> getCoupon () {
        Map<String, Integer> Coupon  = new HashMap<>();
        Coupon.put("有券必买",0);
        Coupon.put("null",0);


        List<String> temp = getColumns("Coupon");

        for(String x:temp){
            Coupon.put(x,Coupon.get(x)+1);
        }

        Collection<Integer> list = new ArrayList<>();

        list.add(Coupon.get("有券必买"));
        list.add(Coupon.get("null"));


        System.out.println(list);

        return list;
    }

    //RFM分类
    @RequestMapping("/getRFM")
    @ResponseBody
    public Collection<Integer> getRFM () {
        Map<String, Integer> RFM  = new HashMap<>();
        RFM.put("中频率高消费",0);
        RFM.put("中频率较高消费",0);
        RFM.put("低频率中消费",0);
        RFM.put("高频率高消费",0);
        RFM.put("低频率高消费",0);
        RFM.put("低频率低消费",0);
        RFM.put("较低频率较高消费",0);
        RFM.put("null",0);

        List<String> temp = getColumns("userValue");

        for(String x:temp){
            RFM.put(x,RFM.get(x)+1);
        }

        Collection<Integer> list = new ArrayList<>();

        list.add(RFM.get("中频率高消费"));
        list.add(RFM.get("中频率较高消费"));
        list.add(RFM.get("低频率中消费"));
        list.add(RFM.get("高频率高消费"));
        list.add(RFM.get("低频率高消费"));
        list.add(RFM.get("低频率低消费"));
        list.add(RFM.get("较低频率较高消费"));
        list.add(RFM.get("null"));

        System.out.println(list);

        return list;
    }

    //RFM分类
    @RequestMapping("/getPriceSensitivity")
    @ResponseBody
    public Collection<Integer> getPriceSensitivity () {
        Map<String, Integer> PriceSensitivity  = new HashMap<>();
        PriceSensitivity.put("极度敏感",0);
        PriceSensitivity.put("比较敏感",0);
        PriceSensitivity.put("一般敏感",0);
        PriceSensitivity.put("不太敏感",0);
        PriceSensitivity.put("极度不敏感",0);
        PriceSensitivity.put("未知",0);

        List<String> temp = getColumns("PriceSensitivity");

        for(String x:temp){
            PriceSensitivity.put(x,PriceSensitivity.get(x)+1);
        }

        Collection<Integer> list = new ArrayList<>();

        list.add(PriceSensitivity.get("极度敏感"));
        list.add(PriceSensitivity.get("比较敏感"));
        list.add(PriceSensitivity.get("一般敏感"));
        list.add(PriceSensitivity.get("不太敏感"));
        list.add(PriceSensitivity.get("极度不敏感"));
        list.add(PriceSensitivity.get("未知"));

        System.out.println(list);

        return list;
    }
}
