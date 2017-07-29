package com.business.common.http.token;

import com.business.common.context.SpringContextUtil;
import com.business.common.other.Files.MD5Util;
import com.business.common.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


/**
 * @author yuton
 * @ClassName: SessionUtil
 * @Description: session 统一管理处理
 * @date 2012-3-19 上午10:39:04
 */
@Slf4j
public class SessionUtil {

    public static final String NICE_NAME = "nickName";
    public static final String COOKIE_USER_KEY = "access-token";
    public static final Long TIMEOUT = 60 * 60 * 6L; //保存2小时
    @SuppressWarnings("unchecked")
    private static RedisUtil<String, Object> redisUtil = SpringContextUtil.getBean(RedisUtil.class);

    /**
     * @param request
     * @param response
     * @param phone
     * @param value
     * @param nickName
     * @return void
     * @throws Exception
     * @Title: setSessionAttribute
     * @Description: 保存会话变量
     */
    public static String setSessionAttribute(HttpServletRequest request, HttpServletResponse response,
                                             String phone, String nickName, Object value) {
        String sessionKey = getSessionKey(request, response);
        String key = MD5Util.getMD5Encode(sessionKey, phone);
        redisUtil.set(key, value, TIMEOUT);
        CookieUtil.setCookieNoAge(request, response, NICE_NAME, nickName);
        return key;
    }

    /**
     * @param request
     * @param response
     * @param phone
     * @param nickName
     * @param value
     * @return void
     * @Title: setSessionAttributeString
     * @Description: 保存会话变量
     */
    public static void setSessionAttributeString(HttpServletRequest request, HttpServletResponse response,
                                                 String phone, String nickName, String value) {
        setSessionAttribute(request, response, phone, nickName, value);
    }


    /**
     * @param request
     * @param phone
     * @return String
     * @Title: getSessionAttributeString
     * @Description: 获取会话变量
     */
    public static String getSessionAttributeString(HttpServletRequest request, String phone) {
        String sessionKey = getSessionKey(request);
        return (StringUtils.isNotEmpty(sessionKey) ? (String) redisUtil.get(MD5Util.getMD5Encode(sessionKey, phone)) : sessionKey);
    }


    /**
     * @param <T>
     * @param request
     * @param phone
     * @return T
     * @Title: getSessionAttribute
     * @Description: 获取会话变量值
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionAttribute(HttpServletRequest request, String phone) {
        String sessionKey = getSessionKey(request);
        return (StringUtils.isNotEmpty(sessionKey) ? (T) redisUtil.get(MD5Util.getMD5Encode(sessionKey, phone)) : null);
    }

    /**
     * @param request
     * @param phone
     * @return void
     * @Title: removeSessionAttribute
     * @Description: 移除会话变量 Redis
     */
    public static void removeSessionAttribute(HttpServletRequest request,
                                              String phone) {
        String sessionKey = getSessionKey(request);
        if (sessionKey != null) {
            redisUtil.delete(MD5Util.getMD5Encode(sessionKey, phone));
        }
    }

    /**
     * @param request
     * @param response
     * @return boolean
     * @Title: removeSessionKey
     * @Description: 移出会话Key Cookie
     */
    public static boolean removeSessionKey(HttpServletRequest request, HttpServletResponse response) {
        String sessionKey = getSessionKey(request);
        if (StringUtils.isNotEmpty(sessionKey)) {
            CookieUtil.removeCookieByName(request, response, COOKIE_USER_KEY);
            return true;
        }
        return false;
    }

    /**
     * @param request
     * @param response
     * @param phone
     * @return
     * @Description: 移出整个Session
     */
    public static boolean removeSession(HttpServletRequest request, HttpServletResponse response, String phone) {
        String sessionKey = getSessionKey(request);
        if (StringUtils.isNotEmpty(sessionKey)) {
            CookieUtil.removeCookieByName(request, response, COOKIE_USER_KEY);
            redisUtil.delete(MD5Util.getMD5Encode(sessionKey, phone));
            return true;
        }
        return false;
    }

    /**
     * @return {@link String}
     * @Title: getSessionKey
     * @Description: 获取会话Key, 不存在返回null
     */
    public static String getSessionKey(HttpServletRequest request) {
        return CookieUtil.getCookieByName(request, COOKIE_USER_KEY);
    }

    /**
     * @return {@link String}
     * @Title: getSessionKey
     * @Description: 获取会话Key, 不存在返回新Key
     */
    public static String getSessionKey(HttpServletRequest request, HttpServletResponse response) {
        String sessionKey = getSessionKey(request);
        if (sessionKey == null) {
            sessionKey = UUID.randomUUID().toString();
            CookieUtil.setCookie(request, response, COOKIE_USER_KEY, sessionKey);
        }
        return sessionKey;
    }

    /**
     * @return {@link String}
     * @Title: getNewSessionKey
     * @Description: 添加并获取新会话Key
     */
    public static String getNewSessionKey(HttpServletRequest request, HttpServletResponse response) {
        String sessionKey = UUID.randomUUID().toString();
        CookieUtil.setCookie(request, response, COOKIE_USER_KEY, sessionKey);
        return sessionKey;
    }

    /**
     * @param request
     * @return {@link String}
     * @Description: 获取会话的sessionId
     */
    public static String getSessionId(HttpServletRequest request) {
        return request.getSession().getId();
    }

}
