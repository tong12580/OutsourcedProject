package com.business.common.http.token;

import com.business.common.CommonTools;
import com.business.common.message.ExceptionMessage;
import com.business.common.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
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
public class SessionUtil extends CommonTools {

    public static final String COOKIE_USER_KEY = "UserKey";
    public static final String USERNAME = "userName";
    public static final Long TIMEOUT = 30 * 24 * 3600L; //保存30天
    public static final String SESSION_TIMEOUT = "SESSION_TIMEOUT";

    @Resource
    private static RedisUtil<String, Object> redisUtil;

    /**
     * @param request
     * @param response
     * @param name
     * @param value
     * @param @throws  Exception
     * @return void
     * @Title: setSessionAttribute
     * @Description: 保存会话变量
     */
    public static void setSessionAttribute(HttpServletRequest request,
                                           HttpServletResponse response, String name, Object value) {

        String sessionKey = getSessionKey(request, response);
        String timeout = redisUtil.get(sessionKey + SESSION_TIMEOUT);
        Long iTimeout = TIMEOUT;
        try {
            iTimeout = Long.parseLong(timeout);
        } catch (Exception e) {
            log.error(ExceptionMessage.NUMBER_FORMAT_EXCEPTION.getExceptionMsg());
        }
        if (!isEmpty(sessionKey)) {
            redisUtil.set(sessionKey + name, value, iTimeout);
        }
    }

    /**
     * @param request
     * @param response
     * @param name
     * @param value
     * @return void
     * @Title: setSessionAttributeString
     * @Description: 保存会话变量
     */
    public static void setSessionAttributeString(HttpServletRequest request,
                                                 HttpServletResponse response, String name, String value) {
        setSessionAttribute(request, response, name, value);
    }


    /**
     * @param request
     * @param name
     * @return String
     * @Title: getSessionAttributeString
     * @Description: 保存会话变量
     */
    public static String getSessionAttributeString(HttpServletRequest request, String name) {

        String sessionKey = getSessionKey(request);
        return (!isEmpty(sessionKey) ? redisUtil.get(sessionKey + name) : null);
    }


    /**
     * @param <T>
     * @param request
     * @param name
     * @return T
     * @throws Exception
     * @Title: getSessionAttribute
     * @Description: 获取会话变量值
     */
    public static <T> T getSessionAttribute(HttpServletRequest request, String name) throws Exception {
        String sessionKey = getSessionKey(request);
        return (!isEmpty(sessionKey) ? redisUtil.get(sessionKey + name) : null);
    }

    /**
     * @param request
     * @param name
     * @return void
     * @Title: removeSessionAttribute
     * @Description: 移除会话变量
     */
    public static void removeSessionAttribute(HttpServletRequest request,
                                              String name) {
        String sessionKey = getSessionKey(request);
        if (sessionKey != null) {
            redisUtil.delete(sessionKey + name);
        }
    }

    /**
     * @param request
     * @param response
     * @return boolean
     * @Title: removeSessionKey
     * @Description: 移出会话Key
     */
    public static boolean removeSessionKey(HttpServletRequest request, HttpServletResponse response) {
        String sessionKey = getSessionKey(request);
        if (!isEmpty(sessionKey)) {
            CookieUtil.removeCookieByName(request, response, COOKIE_USER_KEY);
            return true;
        }
        return false;
    }

    /**
     * @Description: 移出Session
     * @param request
     * @param response
     * @param name
     * @return
     */
    public static boolean removeSession(HttpServletRequest request, HttpServletResponse response,String name) {
        String sessionKey = getSessionKey(request);
        if (!isEmpty(sessionKey)) {
            CookieUtil.removeCookieByName(request, response, COOKIE_USER_KEY);
            redisUtil.delete(sessionKey + name);
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
     * @Description: 获取会话的sessionId
     * @param request
     * @return {@link String}
     */
    public static String getSessionId(HttpServletRequest request){
        return request.getSession().getId();
    }

}
