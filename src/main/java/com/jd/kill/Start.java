package com.jd.kill;

import com.alibaba.fastjson.JSONObject;
import com.sun.webkit.network.CookieManager;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Start {
    final static String headerAgent = "User-Agent";
    final static String headerAgentArg = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36";
    final static String Referer = "Referer";
    final static String RefererArg = "https://passport.jd.com/new/login.aspx";
    //商品id 茅台 100012043978 ps5 100021367452
    static String pid = "100021367452";
    //eid
    static String eid = "FSLJAML4GT6DHFUTRTYPG4C3Q4ZT733I3UTZXVZUTWOJAGGM5E7TXVDHLUQJHVEC5L7RHQ2WD4KYARC2SHNZGEU5BI";
    //fp
    static String fp = "0d956d9119e16bb4baed0509b236554a";
    //抢购数量
    volatile static Integer ok = 1;

    static CookieManager manager = new CookieManager();

    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 20, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());


    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException, ParseException {
        CookieHandler.setDefault(manager);
        HttpUrlConnectionUtil.ips();
        //获取venderId
//        String shopDetail = util.get(null, "https://item.jd.com/" + RushToPurchase.pid + ".html");
//        String venderID = shopDetail.split("isClosePCShow: false,\n" +
//                "                venderId:")[1].split(",")[0];
//        RushToPurchase.venderId = venderID;
        //登录
        Login.Login();
        //判断是否开始抢购
        judgePruchase();
        //开始抢购
        for (int i = 0; i < 20; i++) {
            threadPoolExecutor.execute(new RushToPurchase());
        }
//        new RushToPurchase().run();
    }

    public static void judgePruchase() throws IOException, ParseException, InterruptedException {
        //获取开始时间
        JSONObject headers = new JSONObject();
        headers.put(Start.headerAgent, Start.headerAgentArg);
        headers.put(Start.Referer, Start.RefererArg);
        String str = HttpUrlConnectionUtil.get(headers, "https://item-soa.jd.com/getWareBusiness?skuId=" + pid);
        JSONObject shopDetail = JSONObject.parseObject(str);
        if (shopDetail.get("yuyueInfo") != null) {
            String buyDate = JSONObject.parseObject(shopDetail.get("yuyueInfo").toString()).get("buyTime").toString();
            String startDate = buyDate.split("-202")[0] + ":00";
            System.out.println("抢购时间为：" + startDate);
            Long startTime = HttpUrlConnectionUtil.dateToTime(startDate);

            //获取京东时间
            JSONObject jdTime = JSONObject.parseObject(HttpUrlConnectionUtil.get(headers, "https://api.m.jd.com/client.action?functionId=queryMaterialProducts&client=wh5"));
            Long serverTime = Long.valueOf(jdTime.get("currentTime2").toString());
            if (startTime >= serverTime) {
                System.out.println("正在等待抢购时间");
                System.out.println("等待时间为(min)：" + (startTime - serverTime - 1000) / 1000 / 60);
                // 阻塞直到开售前1s
                Thread.sleep(startTime - serverTime - 1000);
            }
        }
    }
}
