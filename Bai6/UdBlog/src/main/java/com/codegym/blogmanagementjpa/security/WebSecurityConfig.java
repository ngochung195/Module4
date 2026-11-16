package com.codegym.blogmanagementjpa.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("{noop}12345")
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{noop}12345")
                .roles("ADMIN", "USER")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // Người dùng chưa đăng nhập chỉ được phép xem danh sách và chi tiết bài viết (cùng các REST API xem)
                .requestMatchers(
                        new AntPathRequestMatcher("/"),
                        new AntPathRequestMatcher("/blogs", "GET"),
                        new AntPathRequestMatcher("/blogs/*/view", "GET"),
                        new AntPathRequestMatcher("/api/**", "GET")
                ).permitAll()
                // Người dùng đã đăng nhập mới được phép viết, sửa, xóa bài blog và quản lý danh mục
                .requestMatchers(
                        new AntPathRequestMatcher("/blogs/create"),
                        new AntPathRequestMatcher("/blogs/save"),
                        new AntPathRequestMatcher("/blogs/*/edit"),
                        new AntPathRequestMatcher("/blogs/update"),
                        new AntPathRequestMatcher("/blogs/*/delete"),
                        new AntPathRequestMatcher("/blogs/delete"),
                        new AntPathRequestMatcher("/categories/**")
                ).hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
        )
        .formLogin(form -> form
                .defaultSuccessUrl("/blogs", true)
                .permitAll()
        )
        .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/blogs")
                .permitAll()
        );

        return http.build();
    }
}

