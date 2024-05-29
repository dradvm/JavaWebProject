package com.JavaWebProject.JavaWebProject.config;

import com.JavaWebProject.JavaWebProject.filters.AdminFilter;
import com.JavaWebProject.JavaWebProject.filters.CatererFilter;
import com.JavaWebProject.JavaWebProject.filters.EmailVerificationFilter;
import com.JavaWebProject.JavaWebProject.filters.LoginedFilter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class Config {

    @Bean
    public FirebaseApp firebaseApp() {
        try {
            Resource resource = new ClassPathResource("firebase.json");
            FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(resource.getInputStream())).setStorageBucket("java-web-project-2d8bc.appspot.com").build();
            return FirebaseApp.initializeApp(options);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public FilterRegistrationBean<LoginedFilter> loginedFilter() {
        FilterRegistrationBean<LoginedFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginedFilter());
        registrationBean.addUrlPatterns("/auth/toLogin", "/auth/toRetrievepassword", "/auth/toSignup", "/rank/toBuyRankSignup");
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
    
    @Bean
    public FilterRegistrationBean<CatererFilter> catererVerificationFilter() {
        FilterRegistrationBean<CatererFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CatererFilter());
        registrationBean.addUrlPatterns("/caterer/*");
        registrationBean.setOrder(5);
        return registrationBean;
    }
}
