package kuit.springbasic.config;

import kuit.springbasic.auth.JwtTokenProvider;
import kuit.springbasic.filter.AuthFilter;
import kuit.springbasic.filter.JwtAuthFilter;
import kuit.springbasic.filter.JwtExceptionFilter;
import kuit.springbasic.interceptor.JwtSameAuthInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    public WebConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /*
     - 비밀번호 암호화를 위한 빈 등록
     - 회원가입 및 로그인 시 사용
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     - JWT 관련 예외를 처리하는 필터
     - ExpiredJwtException 처리
     - 다른 필터보다 먼저 실행되어야 함
     */
    @Bean
    public FilterRegistrationBean<JwtExceptionFilter> jwtExceptionFilter() {
        FilterRegistrationBean<JwtExceptionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtExceptionFilter());
        registrationBean.addUrlPatterns(
                "/user/list", "/user/updateForm/*", "/user/update/*",
                "/qna/form", "/qna/updateForm/*", "/qna/update", "/qna/create",
                "/api/qna/addAnswer", "/auth/*"
        );
        registrationBean.setOrder(1);
        return registrationBean;
    }

    /*
     - JWT 인증을 처리하는 필터 등록
     - Authorization 헤더에서 JWT 추출 및 검증
     - loginUserId를 request attribute에 추가
     - 예외 필터 다음에 실행
     */
    @Bean
    public FilterRegistrationBean<AuthFilter> jwtAuthFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthFilter(jwtTokenProvider));
        registrationBean.addUrlPatterns(
                "/user/list", "/user/updateForm/*", "/user/update/*",
                "/qna/form", "/qna/updateForm/*", "/qna/update", "/qna/create",
                "/api/qna/addAnswer", "/auth/*"
        );
        registrationBean.setOrder(2);
        return registrationBean;
    }

    /*
     - JwtSameAuthInterceptor 등록
     - 요청의 userId와 인증된 loginUserId 비교
     - 권한 확인이 필요한 경로에 적용
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtSameAuthInterceptor())
                .addPathPatterns(
                        "/user/updateForm/**", "/user/update/**",
                        "/auth/userId"
                );
    }
}

