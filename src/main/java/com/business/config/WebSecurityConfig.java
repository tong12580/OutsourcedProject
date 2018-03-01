package com.business.config;

import com.google.common.collect.ImmutableMap;

import com.business.common.http.token.JwtTokenUtil;
import com.business.common.message.CopyWriteUI;
import com.business.common.message.ResultMessage;
import com.business.common.response.IResultUtil;
import com.business.dao.users.UserDTORepository;
import com.business.filter.JwtAuthenticationTokenFilter;
import com.business.pojo.dto.user.UserDTO;

import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.Objects;

import javax.annotation.Resource;

/**
 * @author yuton
 * @version 1.0
 * @description com.example.demo.config
 * @since 上午10:34 2017/12/25
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDTORepository userDTORepository;
    @Resource
    private CopyWriteUI copyWriteUI;
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    UserDetailsService customUserService() {
        return username -> {
            UserDTO userDTO = userDTORepository.findByUsername(username);
            if (null == userDTO) {
                throw new UsernameNotFoundException("用户名不存在");
            }
            return userDTO;
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationSuccessHandler loginSuccessful() {
        return (httpServletRequest, httpServletResponse, authentication) -> {
            UserDTO userDTO = (UserDTO) authentication.getPrincipal();
            String token = userDTO.getToken();
            if (StringUtils.isEmpty(token)) {
                token = JwtTokenUtil.createToken(
                        userDTO, copyWriteUI.getSecret(), copyWriteUI.getIssuer());
                userDTO = userDTORepository.findByUsername(authentication.getName());
                userDTO.setToken(token);
                userDTORepository.saveAndFlush(userDTO);
            }
            httpServletResponse.setStatus(HttpStatus.OK.value());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.setCharacterEncoding(CharEncoding.UTF_8);
            httpServletResponse.getWriter().write(IResultUtil.successResult(
                    ImmutableMap.of("token", Objects.requireNonNull(token)))
                    .toJson());
        };
    }

    @Bean
    AuthenticationFailureHandler failureHandler() {
        return (httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            httpServletResponse.setCharacterEncoding(CharEncoding.UTF_8);
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(IResultUtil.errorResult(ResultMessage.ERROR_PROMPT, e.getMessage()).toJson());
        };
    }

    @Bean
    LogoutSuccessHandler logoutSuccessHandler() {
        return (httpServletRequest, httpServletResponse, authentication) -> {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            httpServletResponse.setCharacterEncoding(CharEncoding.UTF_8);
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(IResultUtil.successResult().toJson());
        };
    }

    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return (httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.setCharacterEncoding(CharEncoding.UTF_8);
            httpServletResponse.getWriter().write(IResultUtil.errorResult(ResultMessage.ERROR_PROMPT, e.getMessage()).toJson());
        };
    }

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return (httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.addHeader("WWW-Authenticate", "Basic ?");
            httpServletResponse.addHeader("WWW-Authenticate", "Bearer ?");
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.setCharacterEncoding(CharEncoding.UTF_8);
            httpServletResponse.getWriter().write(IResultUtil.errorResult(ResultMessage.ERROR_PROMPT, e.getMessage()).toJson());
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .headers()
                .cacheControl()

                .and()
                .frameOptions()
                .sameOrigin()
                .disable()

                .authorizeRequests()
                .antMatchers("/api/**")
                .access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")

                .and()
                .authorizeRequests()
                .antMatchers("/admin/**")
                .access("hasAnyRole('ROLE_ADMIN','ROLE_ROOT')")

                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .passwordParameter("password")
                .usernameParameter("username")
                .successHandler(loginSuccessful())
                .failureHandler(failureHandler())
                .permitAll()

//                .and()
//                .rememberMe()
//                .tokenValiditySeconds(60 * 60 * 24 * 7)
//                .useSecureCookie(true)
//                .key("remember-me")
//                .rememberMeCookieName("remember-me")

//                .and()
//                .logout()
//                .deleteCookies("remember-me", "JSESSIONID")
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", RequestMethod.GET.name()))
//                .logoutSuccessHandler(logoutSuccessHandler())
//                .invalidateHttpSession(true)
//                .permitAll()

                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())

                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**");
    }
}
