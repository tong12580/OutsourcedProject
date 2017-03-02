package com.business.filter.xssFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by yuton on 2016/9/3.
 */
@WebFilter(urlPatterns = "/*",filterName = "xssFilter")
public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
    }

    @Override
    public void destroy() {
    }
}
