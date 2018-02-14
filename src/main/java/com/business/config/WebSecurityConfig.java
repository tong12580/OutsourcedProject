package com.business.config;

import com.business.dao.users.UserDTORepository;
import com.business.pojo.dto.user.User;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

/**
 * @author yuton
 * @version 1.0
 * @description com.example.demo.config
 * @since 上午10:34 2017/12/25
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService customUserService() {
        return new UserDetailsService() {
            @Resource
            private UserDTORepository userDTORepository;

            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                User user = userDTORepository.findByUsername(s);
                if (null == user) {
                    throw new UsernameNotFoundException("用户名不存在");
                }
                return user;
            }
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**")
                .access("hasRole('ROLE_USER')")
                .anyRequest()
                .authenticated()

                .and()
                .authorizeRequests()
                .antMatchers("/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest()
                .authenticated()

                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .passwordParameter("password")
                .usernameParameter("username")
                .failureUrl("/login?error")
                .permitAll()

                .and()
                .rememberMe()
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                .useSecureCookie(true)
                .key("remember-me")
                .rememberMeCookieName("remember-me")

                .and()
                .logout()
                .deleteCookies("remember-me")
                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**");
    }
}
