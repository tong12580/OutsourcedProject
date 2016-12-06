package com.business.common.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
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
    public static final String CHARSET_UTF_8 = "UTF-8";
    public static final String CONTENT_TYPE_APPLICATION_OCTET_STREAM = "application/octet-stream";
    public static final String LOCATION = "Location";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
    private static final String CONTENT_TYPE_APPLICATION = "application/x-www-form-urlencoded;charset=utf-8";
    private static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";
    private static final String ACCEPT = "Accept";
    private static final String AUTHORIZATION = "Authorization";
    private static final String ACCEPT_LANGUAGE = "Accept-Language";
    private static final String ACCEPT_CHARSET = "Accept-Charset";
    private static final String USER_AGENT_MSG = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)";
    private static final String ACCEPT_LANGUAGE_MSG = "zh-cn,zh;q=0.5";
    private static final String ACCEPT_CHARSET_MSG = "GB2312,utf-8;q=0.7,*;q=0.7";
    private static final String HTTP_CONNECTION = "http";
    private static final String HTTPS_CONNECTION = "https";
    private static final int MAX_TOTAL = 200;
    private static final int MAX_CON_PER_ROUTE = 20;
    private static final int TIME_OUT = 10 * 1000;

    /**
     * JSON参数的Post请求
     * 支持SSL
     * @param url
     * @param json
     * @author yuTong
     */
    public static String postJSON(String url, String json) throws IOException {

        String encoderJson ;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient;
        String result = null;
        try {
            encoderJson = URLEncoder.encode(json, CHARSET_UTF_8);// 将JSON进行UTF-8编码,以便传输中文

            httpClient = createSSLClientDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
            httpPost.addHeader(ACCEPT, APPLICATION_JSON);
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
        } finally {
            try {
                assert response != null;
                response.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return result;
    }

    /**
     * 云之讯短信请求
     *
     * @param url
     * @param json
     * @author yuTong
     */
    public static String httpPostWithJSONToSMS(String url, String json, String authorization) {

        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient;
        String result = null;
        try {
            httpClient = createSSLClientDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE_JSON);
            httpPost.addHeader(ACCEPT, APPLICATION_JSON);
            httpPost.addHeader(AUTHORIZATION, authorization);
            httpPost.setHeader(HTTP.USER_AGENT, USER_AGENT_MSG);
            httpPost.setHeader(ACCEPT_LANGUAGE, ACCEPT_LANGUAGE_MSG);
            httpPost.setHeader(ACCEPT_CHARSET, ACCEPT_CHARSET_MSG);
            StringEntity se = new StringEntity(json, Charset.forName(CHARSET_UTF_8));
            se.setContentType(CONTENT_TYPE_JSON);
            se.setContentEncoding(CHARSET_UTF_8);
            httpPost.setEntity(se);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            if (null != entity) {
                result = EntityUtils.toString(entity, CHARSET_UTF_8);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                assert null != response;
                response.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return result;
    }

    /**
     *
     * @param url
     * @param json
     * @param headers
     * @return
     */
    public static String httpJSON(String url, String json,List<Header> headers){
        CloseableHttpClient httpClient;
        CloseableHttpResponse response = null;
        String result = null;
        httpClient = createSSLClientDefault();
        HttpPost httpPost = new HttpPost(url);
        headers.forEach(httpPost::addHeader);
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
        }

        return result;

    }

    /**
     * Map参数的Post请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String httpRequestJSON(String url, Map<String, Object> params) {

        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient;
        String result = null;
        httpClient = createSSLClientDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE_APPLICATION);

        List<BasicNameValuePair> pairs = new ArrayList<>();

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            BasicNameValuePair valuePair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
            pairs.add(valuePair);
        }

        try {

            httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET_UTF_8));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            if (null != entity) {
                result = EntityUtils.toString(entity, CHARSET_UTF_8);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                assert response != null;
                response.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

        return result;
    }

    /**
     * HTTP的GET调用
     *
     * @param url
     * @return String
     * @author yuTong
     */
    public static String httpGET(String url) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;

        httpClient = createSSLClientDefault();
        HttpGet httpGet = new HttpGet(url);
        String result = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (null != entity) {
                result = EntityUtils.toString(entity, CHARSET_UTF_8);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {

            try {
                assert response != null;
                response.close();
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
            SSLConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory>create()
                    .register(HTTP_CONNECTION, PlainConnectionSocketFactory.getSocketFactory())
                    .register(HTTPS_CONNECTION, sslSF)
                    .build();
            SocketConfig socketConfig = SocketConfig
                    .custom()
                    .setSoTimeout(TIME_OUT)
                    .build();
            PoolingHttpClientConnectionManager poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            poolConnManager.setMaxTotal(MAX_TOTAL);
            poolConnManager.setDefaultMaxPerRoute(MAX_CON_PER_ROUTE);
            poolConnManager.setDefaultSocketConfig(socketConfig);
            HttpRequestRetryHandler httpRequestRetryHandler = (e, i, httpContext) -> {
                if (i >= 5) {
                    return false;
                }
                if (e instanceof NoHttpResponseException) {
                    return false;
                }
                if (e instanceof SSLHandshakeException) {
                    return false;
                }
                if (e instanceof InterruptedIOException) {
                    return false;
                }
                if (e instanceof UnknownHostException) {
                    return false;
                }
                if (e instanceof ConnectTimeoutException) {
                    return false;
                }
                if (e instanceof SSLException) {
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(httpContext);
                HttpRequest request = clientContext.getRequest();
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            };
            RequestConfig requestConfig = RequestConfig
                    .custom()
                    .setConnectionRequestTimeout(TIME_OUT)
                    .setConnectTimeout(TIME_OUT)
                    .setSocketTimeout(TIME_OUT)
                    .build();
            return HttpClients
                    .custom()
                    .setConnectionManager(poolConnManager)
                    .setDefaultRequestConfig(requestConfig)
                    .setRetryHandler(httpRequestRetryHandler)
                    .build();
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
