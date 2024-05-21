package com.JavaWebProject.JavaWebProject.config;

import com.JavaWebProject.JavaWebProject.filters.AdminFilter;
import com.JavaWebProject.JavaWebProject.filters.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    
    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilter() {
        FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginFilter());
        registrationBean.addUrlPatterns("/auth/toLogin" , "/auth/login", "/auth/toSignup");
        registrationBean.setOrder(2);
        return registrationBean;
    }
    
    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilter() {
        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminFilter());
        registrationBean.addUrlPatterns("/admin/*");
        registrationBean.setOrder(3);
        return registrationBean;
    }
}
