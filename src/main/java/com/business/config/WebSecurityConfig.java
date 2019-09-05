package com.business.config;

import com.business.common.message.CopyWriteUI;
import com.business.dao.users.UserDTORepository;
import com.business.filter.JwtAuthenticationTokenFilter;
import com.business.pojo.dto.user.UserDTO;
import com.google.common.collect.ImmutableMap;
import com.jokers.common.http.token.JwtTokenUtil;
import com.jokers.common.message.ResultMessage;
import com.jokers.common.response.IResultUtil;
import com.jokers.pojo.bo.JwtBo;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author yuton
 * @version 1.0
 * com.example.demo.config
 * @since 上午10:34 2017/12/25
 */
@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 2)
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
            if (!JwtTokenUtil.validateToken(token, copyWriteUI.getSecret())) {
                Date date = new Date();
                JwtBo jwtBo = new JwtBo();
                jwtBo.setIssuer(copyWriteUI.getIssuer());
                jwtBo.setSecret(copyWriteUI.getSecret());
                jwtBo.setUsername(userDTO.getUsername());
                jwtBo.setUserId(userDTO.getId());
                jwtBo.setIssuedAt(date);
                jwtBo.setExpiresAt(DateUtils.addWeeks(date, 1));
                token = JwtTokenUtil.createToken(jwtBo);
                userDTO = userDTORepository.findByUsername(authentication.getName());
                userDTO.setToken(token);
                userDTORepository.saveAndFlush(userDTO);
            }
            httpServletResponse.setStatus(HttpStatus.OK.value());
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
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
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            httpServletResponse.getWriter().write(IResultUtil.errorResult(ResultMessage.ERROR_PROMPT, e.getMessage()).toJson());
        };
    }

    @Bean
    LogoutSuccessHandler logoutSuccessHandler() {
        return (httpServletRequest, httpServletResponse, authentication) -> {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            httpServletResponse.setCharacterEncoding(CharEncoding.UTF_8);
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            httpServletResponse.getWriter().write(IResultUtil.successResult().toJson());
        };
    }

    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return (httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
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
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
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
