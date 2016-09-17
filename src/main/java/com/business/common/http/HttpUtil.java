package com.business.common.http;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/***
 * HTTP请求公共类
 *
 * @author yuTong
 * @version 1.0
 * @since 2016/05/19 01:04:35
 */
@Slf4j
public class HttpUtil {

    private static final String APPLICATION_JSON = "application/json";

    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    private static final String CONTENT_TYPE_APPLICATION = "application/x-www-form-urlencoded;charset=utf-8";

    /**
     * 发起请求并获取json结果
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取JSON对象的属性值)
     */
    public static JSONObject requestJSON(String requestUrl, String requestMethod, String outputStr) {

        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();

        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str ;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (Exception e) {
            log.error("https request error:{}" + e.getMessage());
        }
        return jsonObject;
    }

    /**
     * post请求附带Map参数
     * @param requestUrl
     * @param params
     * @return
     */
    public static String postJSON(String requestUrl, Map<String, String> params) {

        String requestMethod = "POST";
        String jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            String outputStr = "";
            if (params != null && params.size() > 0) {

                for (String key : params.keySet()) {
                    String keyValue = params.get(key);
                    if (keyValue != null && !keyValue.equals("")) {
                        outputStr = outputStr + key + "=" + keyValue + "&";
                    }
                }
            }
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            log.error("requestUrl:" + requestUrl);
            log.error("outputStr:" + outputStr);

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str ;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            httpUrlConn.disconnect();
            jsonObject = buffer.toString();
            log.error("jsonObject:" + jsonObject);
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out." + ce.getMessage());
        } catch (Exception e) {
            log.error("https request error:{}" + e.getMessage());
        }
        return jsonObject;
    }

    /**
     * JSON参数的Post请求
     * 支持SSL
     * @param url
     * @param json
     * @author yuTong
     */
    public static String postJSON(String url, String json) {

        String encoderJson ;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        String result = null;
        try {
            encoderJson = URLEncoder.encode(json, "UTF-8");// 将JSON进行UTF-8编码,以便传输中文

            httpClient = HttpUtil.createSSLClientDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

            StringEntity se = new StringEntity(encoderJson, Consts.UTF_8);
            se.setContentType(CONTENT_TYPE_TEXT_JSON);
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
            httpPost.setEntity(se);

            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            if (null != entity) {
                result = EntityUtils.toString(entity, "UTF-8");
            }

        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        } catch (ClientProtocolException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {

            try {
                response.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }

            try {
                httpClient.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return result;
    }

    public static String httpJSON(String url, String json,List<Header> headers){
        CloseableHttpClient httpClient;
        CloseableHttpResponse response = null;
        String result = null;
        httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        headers.forEach(header -> httpPost.addHeader(header));
        StringEntity se = new StringEntity(json, Consts.UTF_8);
        httpPost.setEntity(se);
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }

            try {
                httpClient.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

        return result;

    }

    /**
     * Map参数的Post简单请求请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String httpRequestJSON(String url, Map<String, Object> params) {

        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient;
        String result = null;
        httpClient = HttpUtil.createSSLClientDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE_APPLICATION);

        List<BasicNameValuePair> pairs = new ArrayList<>();

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            BasicNameValuePair valuePair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
            pairs.add(valuePair);
        }

        try {

            httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            if (null != entity) {
                result = EntityUtils.toString(entity, "UTF-8");
            }

        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        } catch (ClientProtocolException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }

            try {
                httpClient.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

        return result;
    }

    /**
     * HTTP的GET简单调用 .未支持SSL
     *
     * @param url
     * @return String
     * @author yuTong
     */
    public static String httpGET(String url) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;

        httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        String result = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (null != entity) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (ClientProtocolException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {

            try {
                response.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

        return result;
    }

    /**
     * 创建Client工具类使其支持SSL
     *
     * @return
     */
    private static CloseableHttpClient createSSLClientDefault() {

        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,
                    (chain, authType) -> true).build();
            SSLConnectionSocketFactory sslsF = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsF).build();
        } catch (KeyManagementException e) {
            log.error(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        } catch (KeyStoreException e) {
            log.error(e.getMessage());
        }

        return HttpClients.createDefault();
    }

    /**
     * 忽视证书HostName
     */
    private static HostnameVerifier ignoreHostnameVerifier = (s, sslsession) -> {
        log.warn("WARNING: Hostname is not matched for cert.");
        return true;
    };


    /**
     * Ignore Certification
     */
    private static TrustManager ignoreCertificationTrustManger = new X509TrustManager() {


        private X509Certificate[] certificates;


        @Override
        public void checkClientTrusted(X509Certificate certificates[],
                                       String authType) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = certificates;
                log.info("init at checkClientTrusted");
            }


        }


        @Override
        public void checkServerTrusted(X509Certificate[] ax509certificate,
                                       String s) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = ax509certificate;
                log.info("init at checkServerTrusted");
            }
        }


        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }


    };

    /**
     * Get 请求
     *
     * @param pathUrl
     * @param queryString
     * @return
     */
    public static String doGet(String pathUrl, String queryString) {

        StringBuffer repString = new StringBuffer();

        try {
            String path = pathUrl;
            if (queryString != null && !queryString.equals("")) {
                path = path + "?" + queryString;
            }
            HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
            HttpsURLConnection connection = (HttpsURLConnection) (new URL(path)).openConnection();

            TrustManager[] tm = {ignoreCertificationTrustManger};
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tm, new java.security.SecureRandom());


            // 从上述SSLContext对象中得到SSLSocketFactory对象 
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            connection.setSSLSocketFactory(ssf);

            InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while (null != (s = br.readLine())) {
                repString.append(s);
            }
            isr.close();
            connection.disconnect();

        } catch (Exception ex) {
            log.error("调用链接失败：pathUrl:" + pathUrl + "queryString:" + queryString);
            log.error(ex.getMessage());
        } finally {
            log.info(repString.toString());
        }
        return repString.toString();
    }

    /**
     * GET Map请求
     *
     * @param pathUrl
     * @param params
     * @return
     */
    public static String doGet(String pathUrl, Map<String, String> params) {

        String queryString = "";
        StringBuffer repString = new StringBuffer();

        try {
            String path = pathUrl;
            String keyValue = "";
            if (params != null && params.size() > 0) {

                for (String key : params.keySet()) {
                    keyValue = params.get(key);
                    if (keyValue != null && !keyValue.equals("")) {
                        queryString = queryString + key + "=" + keyValue + "&";
                    }
                }
            }
            if (queryString != null && !queryString.equals("")) {
                path = path + "?" + queryString;
            }
            HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
            HttpsURLConnection connection = (HttpsURLConnection) (new URL(path)).openConnection();

            // Prepare SSL Context 
            TrustManager[] tm = {ignoreCertificationTrustManger};
//            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE"); 
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tm, new java.security.SecureRandom());


            // 从上述SSLContext对象中得到SSLSocketFactory对象 
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            connection.setSSLSocketFactory(ssf);

            InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while (null != (s = br.readLine())) {
                repString.append(s);
            }
            isr.close();
            connection.disconnect();

        } catch (Exception ex) {
            log.error("调用链接失败：pathUrl:" + pathUrl + "queryString:" + queryString);
            ex.printStackTrace();
        } finally {
            log.info(repString.toString());
        }
        return repString.toString();
    }

    /**
     * GET List<NameValuePair>请求
     *
     * @param pathUrl
     * @param params
     * @return
     */
    public static String URLGet(String pathUrl, List<NameValuePair> params) {

        String queryString = "";

        StringBuffer repString = new StringBuffer();

        try {
            String path = pathUrl;
            String keyValue = "";
            if (params != null && params.size() > 0) {

                for (NameValuePair nvp : params) {
                    String key = nvp.getName();
                    keyValue = nvp.getValue();
                    if (keyValue != null && !keyValue.equals("")) {
                        queryString = queryString + key + "=" + keyValue + "&";
                    }
                }
            }
            if (queryString != null && !queryString.equals("")) {
                path = path + "?" + queryString;
            }
            HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
            HttpsURLConnection connection = (HttpsURLConnection) (new URL(path)).openConnection();

            // Prepare SSL Context 
            TrustManager[] tm = {ignoreCertificationTrustManger};
//            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE"); 
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tm, new java.security.SecureRandom());


            // 从上述SSLContext对象中得到SSLSocketFactory对象 
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            connection.setSSLSocketFactory(ssf);

            InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while (null != (s = br.readLine())) {
                repString.append(s);
            }
            isr.close();
            connection.disconnect();

        } catch (Exception ex) {
            log.error("调用链接失败：pathUrl:" + pathUrl + "queryString:" + queryString);
            ex.printStackTrace();
        } finally {
            log.info(repString.toString());
        }
        return repString.toString();
    }

}
