package com.example.kojimall.config;

import com.example.kojimall.common.LogFilter;
import com.example.kojimall.common.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        AuditorAware<String> auditorAware = new AuditorAware<>() {
            @Override
            public Optional<String> getCurrentAuditor() {
                return Optional.of(UUID.randomUUID().toString());
            }
        };
        return auditorAware;
    }
}
