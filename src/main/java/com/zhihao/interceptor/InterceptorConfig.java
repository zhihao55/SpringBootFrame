package com.zhihao.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }

    @Bean
    public HandlerInterceptor jWTInterceptor(){
        return new JWTInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jWTInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/v1/user/login")
                .excludePathPatterns("/v1/user/register")
                .excludePathPatterns("/v1/user/changetoken")
                .excludePathPatterns("/v1/user/email");
    }

}
