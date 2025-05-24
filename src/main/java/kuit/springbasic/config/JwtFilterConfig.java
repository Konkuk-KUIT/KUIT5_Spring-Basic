package kuit.springbasic.config;

import kuit.springbasic.filter.JwtAuthFilter;
import kuit.springbasic.filter.JwtExceptionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtFilterConfig {
    @Bean
    public FilterRegistrationBean<JwtExceptionFilter> jwtExceptionFilterRegistration(JwtExceptionFilter filter) {
        FilterRegistrationBean<JwtExceptionFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setOrder(1); // 먼저 실행됨
        return registration;
    }

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilterRegistration(JwtAuthFilter filter) {
        FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setOrder(2); // 나중에 실행됨
        return registration;
    }
}
