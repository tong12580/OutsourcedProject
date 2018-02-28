package com.business.config;

import com.google.common.collect.ImmutableMap;

import com.business.common.http.token.JwtTokenUtil;
import com.business.common.message.CopyWriteUI;
import com.business.common.message.ResultMessage;
import com.business.common.response.IResultUtil;
import com.business.dao.users.UserDTORepository;
import com.business.pojo.dto.user.UserDTO;

import org.apache.commons.lang3.CharEncoding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yuton
 * @version 1.0
 * @description com.example.demo.config
 * @since 上午10:34 2017/12/25
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDTORepository userDTORepository;
    @Resource
    private CopyWriteUI copyWriteUI;

    @Bean
    UserDetailsService customUserService() {

        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserDTO userDTO = userDTORepository.findByUsername(username);
                if (null == userDTO) {
                    throw new UsernameNotFoundException("用户名不存在");
                }
                return userDTO;
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationSuccessHandler loginSuccessful() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                UserDTO userDTO = userDTORepository.findByUsername(authentication.getName());
                httpServletResponse.setStatus(HttpStatus.OK.value());
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.setCharacterEncoding(CharEncoding.UTF_8);
                httpServletResponse.getWriter().write(IResultUtil.successResult(
                        ImmutableMap.of("token",
                                Objects.requireNonNull(JwtTokenUtil.createToken(
                                        userDTO, copyWriteUI.getSecret(), copyWriteUI.getIssuer()))))
                        .toJson());
            }
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
            httpServletResponse.addHeader("WWW-Authenticate", "Basic realm=" + httpServletRequest.getContextPath() + "");
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.setCharacterEncoding(CharEncoding.UTF_8);
            httpServletResponse.getWriter().write(IResultUtil.errorResult(ResultMessage.ERROR_PROMPT, e.getMessage()).toJson());
        };
    }

//    @Bean
//    OncePerRequestFilter jwtAuthenticationTokenFilter() {
//        return new OncePerRequestFilter() {
//
//            @Override
//            protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//                String token = httpServletRequest.getHeader(copyWriteUI.getTokenHeader());
//                if (null == token || !token.startsWith(copyWriteUI.getTokenHead())) {
//                    return;
//                }
//                token = token.substring(copyWriteUI.getTokenHead().length());
//                if (JwtTokenUtil.validateToken(token, copyWriteUI.getSecret())) {
//                    UserDTO user = JwtTokenUtil.getAuthentication(token);
//                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
//                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(
//                            httpServletRequest));
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//                }
//                filterChain.doFilter(httpServletRequest, httpServletResponse);
//            }
//        };
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
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

//                .and()
//                .addFilterBefore()

                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .passwordParameter("password")
                .usernameParameter("username")
                .successHandler(loginSuccessful())
                .failureHandler(failureHandler())
                .permitAll()

                .and()
                .rememberMe()
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                .useSecureCookie(true)
                .key("remember-me")
                .rememberMeCookieName("remember-me")

                .and()
                .logout()
                .deleteCookies("remember-me", "JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", RequestMethod.GET.name()))
                .logoutSuccessHandler(logoutSuccessHandler())
                .invalidateHttpSession(true)
                .permitAll()

                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())

                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint())

                .and()
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**");
    }
}
