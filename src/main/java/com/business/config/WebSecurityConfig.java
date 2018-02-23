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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions()
                .sameOrigin()
                .disable()

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
                .successForwardUrl("/loginSuccessful")
                .failureUrl("/loginFail")
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
                .invalidateHttpSession(true)
                .permitAll()

                .and()
                .httpBasic()

                .and()
                .csrf()
                .disable()
                .sessionManagement().maximumSessions(1);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**");
    }
}
