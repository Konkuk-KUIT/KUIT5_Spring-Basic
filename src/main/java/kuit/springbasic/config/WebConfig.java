package kuit.springbasic.config;

import kuit.springbasic.auth.JwtTokenProvider;
import kuit.springbasic.filter.AuthFilter;
import kuit.springbasic.filter.JwtAuthFilter;
import kuit.springbasic.filter.JwtExceptionFilter;
import kuit.springbasic.filter.SessionAuthFilter;
import kuit.springbasic.interceptor.JwtSameAuthInterceptor;
import kuit.springbasic.interceptor.SameUserInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // spring security 에 포함된 객체
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<JwtExceptionFilter> jwtExceptionFilter() {
        FilterRegistrationBean<JwtExceptionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtExceptionFilter()); // 사용할 필터 객체
        registrationBean.addUrlPatterns("/api/*", "/auth/*");        // 필터를 적용할 URL 패턴
        registrationBean.setOrder(0);                 // 필터 순서 (낮을수록 먼저 실행)
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> jwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthFilter(jwtTokenProvider)); // 사용할 필터 객체
        registrationBean.addUrlPatterns("/api/*", "/auth/*");        // 필터를 적용할 URL 패턴
        registrationBean.setOrder(1);                 // 필터 순서 (낮을수록 먼저 실행)
        return registrationBean;
    }

    // 만들어둔 필터 등록 -  서블릿 url 패턴
    @Bean
    public FilterRegistrationBean<AuthFilter> sessionAuthFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SessionAuthFilter()); // 사용할 필터 객체
        registrationBean.addUrlPatterns(
                "/user/list", "/user/updateForm/*", "/user/update/*",
                "/qna/form", "/qna/updateForm/*", "/qna/update", "/qna/create",
                "/api/qna/addAnswer"
//                "/auth/*"
                );        // 필터를 적용할 URL 패턴
        registrationBean.setOrder(2);                 // 필터 순서 (낮을수록 먼저 실행)
        return registrationBean;
    }

    // 스프링 패스 패턴
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SameUserInterceptor())
                .addPathPatterns(
                        "/user/updateForm/**", "/user/update/**"
//                        "/auth/userId"
                );
        registry.addInterceptor(new JwtSameAuthInterceptor())
                .addPathPatterns(
//                        "/user/updateForm/**", "/user/update/**",
                        "/auth/userId"
                );
    }


}
