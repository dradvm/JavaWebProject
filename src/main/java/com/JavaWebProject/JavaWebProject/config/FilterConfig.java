package com.JavaWebProject.JavaWebProject.config;

import com.JavaWebProject.JavaWebProject.filters.AdminFilter;
import com.JavaWebProject.JavaWebProject.filters.EmailVerificationFilter;
import com.JavaWebProject.JavaWebProject.filters.LoginedFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    
    @Bean
    public FilterRegistrationBean<LoginedFilter> loginedFilter() {
        FilterRegistrationBean<LoginedFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginedFilter());
        registrationBean.addUrlPatterns("/auth/toLogin" , "/auth/toRetrievepassword", "/auth/toSignup", "/rank/toBuyRankSignup");
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
    
    @Bean
    public FilterRegistrationBean<EmailVerificationFilter> emailVerificationFilter() {
        FilterRegistrationBean<EmailVerificationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new EmailVerificationFilter());
        registrationBean.addUrlPatterns("/auth/toEmailverification");
        registrationBean.setOrder(4);
        return registrationBean;
    }
}
