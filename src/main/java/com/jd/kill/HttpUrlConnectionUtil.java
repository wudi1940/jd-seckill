package com.jd.kill;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class HttpUrlConnectionUtil {

    static ArrayList<String> ipList = new ArrayList<String>();

    public static String get(JSONObject headers, String url) throws IOException {
        String response = "";
        HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(url).openConnection());
        httpURLConnection.setRequestMethod("GET");
        if (headers != null) {
            Iterator<String> iterator = headers.keySet().iterator();
            while (iterator.hasNext()) {
                String headerName = iterator.next();
                httpURLConnection.setRequestProperty(headerName, headers.get(headerName).toString());
            }
        }
        httpURLConnection.connect();
        if (httpURLConnection.getResponseCode() == 200) {
            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] buffer;
            buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) != -1) {
                response = response + new String(buffer, 0, length, "UTF-8");
            }
            httpURLConnection.disconnect();
        }
        return response;
    }

    /**
     * get请求
     *
     * @param headers 请求头，可为空
     * @param url
     * @return
     * @throws IOException
     */
    public static String get(JSONObject headers, String url, Proxy proxy) throws IOException {
        String response = "";
        HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(url).openConnection(proxy));
        httpURLConnection.setRequestMethod("GET");
        if (headers != null) {
            Iterator<String> iterator = headers.keySet().iterator();
            while (iterator.hasNext()) {
                String headerName = iterator.next();
                httpURLConnection.setRequestProperty(headerName, headers.get(headerName).toString());
            }
        }
        httpURLConnection.connect();
        if (httpURLConnection.getResponseCode() == 200) {
            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] buffer;
            buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) != -1) {
                response = response + new String(buffer, 0, length, "UTF-8");
            }
            httpURLConnection.disconnect();
        }
        return response;
    }

    /**
     * post请求
     *
     * @param headers 请求头，可为空
     * @param url
     * @param params  post请求体，可为空
     * @return
     * @throws IOException
     */
    public static String post(JSONObject headers, String url, JSONObject params) throws IOException {
        String response = "";
        HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(url).openConnection());
        httpURLConnection.setRequestMethod("POST");
        if (headers != null) {
            Iterator<String> iterator = headers.keySet().iterator();
            while (iterator.hasNext()) {
                String headerName = iterator.next();
                httpURLConnection.setRequestProperty(headerName, headers.get(headerName).toString());
            }
        }
        httpURLConnection.setDoOutput(true);
        httpURLConnection.connect();
        if (params != null) {
            httpURLConnection.getOutputStream().write(params.toJSONString().getBytes("UTF-8"));
        }
        httpURLConnection.getInputStream();
        if (httpURLConnection.getResponseCode() == 200) {
            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] buffer;
            buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) != -1) {
                response = response + new String(buffer, 0, length, "UTF-8");
            }
            httpURLConnection.disconnect();
        }
        httpURLConnection.disconnect();
        return response;
    }

    public static String post(JSONObject headers, String url, JSONObject params, Proxy proxy) throws IOException {
        String response = "";
        HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(url).openConnection(proxy));
        httpURLConnection.setRequestMethod("POST");
        if (headers != null) {
            Iterator<String> iterator = headers.keySet().iterator();
            while (iterator.hasNext()) {
                String headerName = iterator.next();
                httpURLConnection.setRequestProperty(headerName, headers.get(headerName).toString());
            }
        }
        httpURLConnection.setDoOutput(true);
        httpURLConnection.connect();
        if (params != null) {
            httpURLConnection.getOutputStream().write(params.toJSONString().getBytes("UTF-8"));
        }
        httpURLConnection.getInputStream();
        if (httpURLConnection.getResponseCode() == 200) {
            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] buffer;
            buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) != -1) {
                response = response + new String(buffer, 0, length, "UTF-8");
            }
            httpURLConnection.disconnect();
        }
        httpURLConnection.disconnect();
        return response;
    }

    /**
     * 获取并保存二维码
     *
     * @param headers
     * @param url
     * @return
     * @throws IOException
     */
    public static String getQCode(JSONObject headers, String url) throws IOException {
        String response = "";
        HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(url).openConnection());
        httpURLConnection.setRequestMethod("GET");
        if (headers != null) {
            Iterator<String> iterator = headers.keySet().iterator();
            while (iterator.hasNext()) {
                String headerName = iterator.next();
                httpURLConnection.setRequestProperty(headerName, headers.get(headerName).toString());
            }
        }
        httpURLConnection.connect();
        if (httpURLConnection.getResponseCode() == 200) {
            InputStream inputStream = httpURLConnection.getInputStream();
            OutputStream outputStream = new FileOutputStream("QCode.png");
            byte[] buffer;
            int length;
            buffer = new byte[1024];
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
                response = response + new String(buffer, 0, length, "UTF-8");
            }
            outputStream.close();
            httpURLConnection.disconnect();
        }
        return response;
    }

    public static void writeCookie(String cookie) {
        FileWriter fwriter = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter("Cookies.txt");
            fwriter.write(cookie);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String readCookie() {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("Cookies.txt"));
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result = result + s;
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ;
        }
        return result;
    }

    /**
     * date字符串转时间戳
     *
     * @param date
     * @return
     */
    public static Long dateToTime(String date) throws ParseException {
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data = sdfTime.parse(date);
        Long time = data.getTime();
        return time;
    }

    /**
     * time时间戳转Date
     *
     * @param time
     * @return
     */
    public static Date timeToDate(String time) {
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdfTime.format(Long.valueOf(time));
        try {
            Date date = sdfTime.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void ips() throws IOException {
        // 需要添加白名单
        String path = "http://api.xiequ.cn/VAD/GetIp.aspx?act=get&uid=69039&vkey=BF212FEC43AF2E039CF1EF09448DDF44&num=200&time=30&plat=1&re=0&type=0&so=1&ow=1&spl=1&addr=&db=1";// 要获得html页面内容的地址

        URL url = new URL(path);// 创建url对象

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 打开连接
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        conn.setRequestProperty("contentType", "GBK"); // 设置url中文参数编码

        conn.setConnectTimeout(5 * 1000);// 请求的时间

        conn.setRequestMethod("GET");// 请求方式

        InputStream inStream = conn.getInputStream();
//         readLesoSysXML(inStream);

        BufferedReader in = new BufferedReader(new InputStreamReader(inStream, "GBK"));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        // 读取获取到内容的最后一行,写入
        while ((line = in.readLine()) != null) {
            buffer.append(line);
            ipList.add(line);
            System.out.println(line);
        }
//        String str = buffer.toString();
//        JSONObject json1 = JSONObject.parseObject(str);
//        JSONArray jsons = JSONArray.parseArray(json1.get("data").toString());
//
//        for (Object json : jsons) {
//            JSONObject ips = JSONObject.parseObject(json.toString());
//            String ip = ips.get("IP").toString();
//            System.out.println(ip);
//            ipp.add(ip);
//        }
    }

}
