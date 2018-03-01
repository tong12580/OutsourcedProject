package com.business.filter;

import com.business.common.http.token.JwtTokenUtil;
import com.business.common.message.CopyWriteUI;
import com.business.pojo.dto.user.UserDTO;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yutong
 * @version 1.0
 * @description
 * @since 2018/3/1 21:59
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private CopyWriteUI copyWriteUI;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String authHeader = request.getHeader(copyWriteUI.getTokenHeader());
        if (authHeader != null && authHeader.startsWith(copyWriteUI.getTokenHead())) {
            // The part after "Bearer "
            final String authToken = authHeader.substring(copyWriteUI.getTokenHead().length());
            UserDTO userDTO = JwtTokenUtil.getAuthentication(authToken);
            String username = userDTO.getUsername();

            logger.info("checking authentication " + username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (JwtTokenUtil.validateToken(authToken, copyWriteUI.getSecret())) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDTO, null, userDTO.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    logger.info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
