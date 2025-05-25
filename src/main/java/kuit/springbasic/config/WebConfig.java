package kuit.springbasic.config;

import kuit.springbasic.auth.JwtTokenProvider;
import kuit.springbasic.controller.interceptor.JwtSameAuthInterceptor;
import kuit.springbasic.filter.JwtAuthFilter;
import kuit.springbasic.filter.JwtExceptionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public FilterRegistrationBean<JwtExceptionFilter> jwtExceptionFilterFilter(){
        FilterRegistrationBean<JwtExceptionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtExceptionFilter());
        registrationBean.setOrder(1);
        return registrationBean;
    }
    @Bean
    public FilterRegistrationBean<JwtAuthFilter> authFilter(JwtTokenProvider provider){
        FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthFilter(provider));
        registrationBean.addUrlPatterns(
                "/user/list", "/user/updateForm/**", "/user/update/**",
                "/qna/form", "/qna/updateForm/**", "/qna/create",
                "/api/qna/addAnswer"
        );  // 필터를 적용할 URL 패턴
        // 필터 순서 (낮을수록 먼저 실행)
        registrationBean.setOrder(2);
        return registrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtSameAuthInterceptor())
                .addPathPatterns(
                        "/user/updateForm/**", "/user/update/**",
                        "/qna/updateForm/**", "/qna/update"
                );
    }
}
