package kuit.springbasic.config;

import kuit.springbasic.filter.AuthFilter;
import kuit.springbasic.filter.SessionAuthFilter;
import kuit.springbasic.interceptor.JwtSameAuthInterceptor;
import kuit.springbasic.interceptor.SameUserInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final JwtSameAuthInterceptor jwtSameAuthInterceptor;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SessionAuthFilter());
        registrationBean.addUrlPatterns(
                "/user/list", "/user/updateForm/*", "/user/update/*",
                "/qna/form", "/qna/updateForm/*", "/qna/update", "/qna/create",
                "/api/qna/addAnswer"
        );
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtSameAuthInterceptor)
                .addPathPatterns("/auth/userId")
                .excludePathPatterns("/user/loginForm", "/css/**", "/js/**", "/images/**");
    }
}
