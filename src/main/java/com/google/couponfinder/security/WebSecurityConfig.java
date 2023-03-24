package com.google.couponfinder.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/2/14 15:46
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //TODO SpringSecurity提供的东西太多了，身份认证就简实现吧。按照自己的思路，但是鉴权如何做？鉴权只要处理web(HttpSecurity http)，
    //身份认证完全之后，Authentication对象中应该保存了authorities

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //不禁用的话，微信小程序调用/login接口就会出现问题。
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/coupon/**", "/js/**", "/images/**", "/login","/wallet/**","/merchant/**").permitAll()
                .anyRequest().authenticated();
    }



    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password("$2a$10$FnGPmEzu/182b9/9yycwHu8WL5yHJrRf0uFkjMiPOYXJk58Bpsw36")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("$2a$10$FnGPmEzu/182b9/9yycwHu8WL5yHJrRf0uFkjMiPOYXJk58Bpsw36")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
