package kuit.springbasic.config;

import kuit.springbasic.auth.JwtTokenProvider;
import kuit.springbasic.filter.AuthFilter;
import kuit.springbasic.filter.JwtAuthFilter;
import kuit.springbasic.filter.JwtExceptionFilter;
import kuit.springbasic.filter.SessionAuthFilter;
import kuit.springbasic.interceptor.JwtSameAuthInterceptor;
import kuit.springbasic.interceptor.SameUserInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor // 미션3 - JwtSameAuthInterceptor 주입용
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final JwtSameAuthInterceptor jwtSameAuthInterceptor; // 필드 선언

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SessionAuthFilter()); // 사용할 필터 객체
        registrationBean.addUrlPatterns(
                "/user/list", "/user/updateForm/*", "/user/update/*",
                "/qna/form", "/qna/updateForm/*", "/qna/update", "/qna/create",
                "/api/qna/addAnswer",
                "/auth/*"
        );        // 필터를 적용할 URL 패턴
        registrationBean.setOrder(1);                 // 필터 순서 (낮을수록 먼저 실행)
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthFilter(jwtTokenProvider));
        registrationBean.addUrlPatterns("/auth/*");  // JWT 인증이 필요한 URL
        registrationBean.setOrder(0);  // SessionAuthFilter보다 먼저 실행
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<JwtExceptionFilter> jwtExceptionFilter() {
        FilterRegistrationBean<JwtExceptionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtExceptionFilter());
        registrationBean.addUrlPatterns("/auth/*");
        registrationBean.setOrder(-1);  // 가장 먼저 실행되어야 함
        return registrationBean;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SameUserInterceptor())
                .addPathPatterns(
                        "/user/updateForm/**", "/user/update/**");
//                        "/auth/userId" -> 제거
        registry.addInterceptor(jwtSameAuthInterceptor) // 새 인터셉터 등록
                .addPathPatterns("/auth/userId"); // JWT 전용 검사 대상
    }


}
