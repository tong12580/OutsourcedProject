package com.business.filter.loginFilter;

import com.business.common.http.token.CookieUtil;
import com.business.common.http.token.SessionUtil;
import com.business.common.message.ResultMessage;
import com.business.common.other.Files.MD5Util;
import com.business.common.redis.RedisUtil;
import com.business.common.response.IResultException;
import com.business.pojo.dto.user.UserDTO;
import com.business.pojo.vo.UserVo;
import com.business.service.interfaces.users.UserService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author yuton
 * @version 1.0
 * @description
 * @since 2017/2/9 12:30
 */
@WebFilter(urlPatterns = "/api/*", filterName = "loginFilter")
public class LoginFilter implements Filter {
    @Resource
    private UserService userService;

    @Resource
    private RedisUtil<String, UserVo> redisUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String accessToken = SessionUtil.getSessionKey(request);
        if (StringUtils.isBlank(accessToken)) {
            throw new IResultException(ResultMessage.LOGIN_TIME_OUT);
        }
        String nickName = CookieUtil.getCookieByName(request, SessionUtil.NICE_NAME);
        UserDTO userDTO = userService.getUserByNickName(nickName);
        if (null == userDTO) {
            throw new IResultException(ResultMessage.LOGIN_TIME_OUT);
        }
        UserVo user = redisUtil.get(MD5Util.getMD5Encode(accessToken, userDTO.getPhone()));
        if (null == user) {
            throw new IResultException(ResultMessage.LOGIN_TIME_OUT);
        }
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
