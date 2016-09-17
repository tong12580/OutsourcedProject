/**
 * Filename:	HttpResponseUtil.java
 * Description:
 * Copyright:   Copyright (c)2011
 * Company:    easy
 * @author:     guosheng.zhu
 * @version:    1.0  
 * Create at:   2011-12-21 下午05:52:40  
 *  
 * Modification History:  
 * Date           Author       Version      Description  
 * ------------------------------------------------------------------  
 * 2011-12-21    guosheng.zhu       1.0        1.0 Version
 */
package com.business.common.http;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @ClassName: HttpResponseUtil
 * @Description: 响应通用处理类
 * @author guosheng.zhu
 * @date 2011-12-21 下午05:52:40
 */
public class HttpResponseUtil {

	// / ajax请求简单响应常量定义
	public static final String AJAX_RESP_SUCCESS = "success";
	public static final String AJAX_RESP_ERROR = "error";
	public static final String AJAX_RESP_TRUE = "true";
	public static final String AJAX_RESP_FALSE = "false";
	public static final String AJAX_RESP_EXCEPTION = "exception";

	/**
	 * @Title: send
	 * @Description: 发送简单字符串响应
	 * @param @param response
	 * @param @param data
	 * @param @param encoding
	 * @param @throws UnsupportedEncodingException
	 * @param @throws IOException
	 * @return void
	 */
	public static void send(HttpServletResponse response, String data,
                            String encoding) {
		try {
			response.getOutputStream().write(data.getBytes(encoding));
		} catch (Exception e) {
		}
	}

	/**
	 * @Title: send
	 * @Description: 发送简单字符串响应，默认UTF8编码
	 * @param @param response
	 * @param @param data
	 * @return void
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static void sendWithUTF8(HttpServletResponse response, String data) {
		send(response, data, "utf-8");
	}
}
