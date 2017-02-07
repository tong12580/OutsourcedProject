package com.business.common.http.token;

import com.business.common.CommonTools;
import com.business.common.context.BasePathFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil extends CommonTools {

    private final static String domain = "Bang.vc";//网站域名
    private final static int cookieMaxAge = 60 * 60 * 6;// 设置cookie有效期6小时

    private final static String HISTORY_PATH = "/";// Cookie中，历史浏览的Cookie的路径

    // 清除所有历史浏览记录
    public static void removeAllHistoryCookie(String cookieName, HttpServletRequest request,
                                              HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies || cookies.length == 0) {
            return;
        }
        for (Cookie thisCookie : cookies) {
            if (thisCookie.getName().startsWith(cookieName)) {
                thisCookie.setMaxAge(0); // 删除这个cookie
                thisCookie.setPath(HISTORY_PATH);
                if (checkDomain(request)) {
                    thisCookie.setDomain(domain);
                }
                response.addCookie(thisCookie);
            }
        }
    }

    // 清除单个的浏览记录
    public static void removeCookieByName(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        if (cookieName == null) {
            return;
        }
        Cookie[] cookies = request.getCookies();
        if (null == cookies || cookies.length == 0) {
            return;
        }
        for (Cookie thisCookie : cookies) {
            if (thisCookie.getName().equals(cookieName)) {
                thisCookie.setMaxAge(0); // 删除这个cookie
                thisCookie.setPath(HISTORY_PATH);
                if (checkDomain(request)) {
                    thisCookie.setDomain(domain);
                }
                response.addCookie(thisCookie);
            }
        }
    }

    /**
     * @param  request
     * @param  response
     * @param  cookieName
     * @param  cookieValue
     * @return void
     * @Title: saveCookie
     * @Description: 添加cookie, 默认过期时间是6小时
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        if (checkDomain(request)) {
            cookie.setDomain(domain);
        }
        cookie.setMaxAge(cookieMaxAge);
        cookie.setPath(HISTORY_PATH);
        response.addCookie(cookie);
    }

    /**
     * @param  request
     * @param  response
     * @param  cookieName
     * @param  cookieValue
     * @return void
     * @Title: setCookie
     * @Description: 添加cookie, 没有设置过期时间
     */
    public static void setCookieNoAge(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                      String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        if (checkDomain(request)) {
            cookie.setDomain(domain);
        }
        cookie.setPath(HISTORY_PATH);
        response.addCookie(cookie);
    }


    /**
     * @param   request
     * @param   cookieName
     * @return String
     * @Title: getCookieByName
     * @Description: 根据cookieName来获取cookieValue
     */
    public static String getCookieByName(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (isEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private static boolean checkDomain(HttpServletRequest request) {
        boolean flag = false;
        String basePath = BasePathFactory.getBasePath(request);
        if (!isEmpty(basePath) && basePath.contains(domain)) {
            flag = true;
        }
        return flag;
    }

}
