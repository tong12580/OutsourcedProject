/**
 * @Title: HttpPostUploadUtil.java
 * @Package com.surfing.imodel.common.utils
 * @Description: TODO(用一句话描述该文件做什么)
 * @author Administrator
 * @projectName:learn
 * @date 2013年12月3日 下午2:50:15
 * @version V1.1
 */
package com.business.common.http;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @author Administrator
 * @ClassName: HttpPostUploadUtil
 * @Description: http请求工具类
 * @date 2013年12月3日 下午2:50:15
 */
@Slf4j
public class HttpPostUtil {

    //方法运行正常
    public static final String NORMAL = "normal";

    //方法异常
    public static final String EXCEPTION = "exception";

    /**
     * 上传数据流
     *
     * @param urlStr  访问上传地址
     * @param textMap 文本键值对MAP  键：类名 ，值：对象JSON包
     * @return
     */
    public static Map<String, Object> doRequest(String urlStr, Map<String, String> textMap) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("ex", NORMAL);
        String res;
        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------123821742118716"; // boundary就是request头和上传文件内容的分隔符
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(500000);
            conn.setReadTimeout(300000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "text/html;charset=UTF-8");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator<?> iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    @SuppressWarnings("rawtypes")
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            resultMap.put("responseText", res);
        } catch (Exception e) {
            log.error("发送POST请求出错。" + urlStr);
            resultMap.put("ex", EXCEPTION);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return resultMap;
    }


}
