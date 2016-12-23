//package ru.mail.park.config;
//
//import javax.inject.Inject;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.social.connect.UsersConnectionRepository;
//import org.springframework.social.connect.web.thymeleaf.SpringSocialDialect;
//import org.springframework.web.servlet.ViewResolver;
//import org.springframework.web.servlet.config.annotation.*;
//import org.thymeleaf.spring4.SpringTemplateEngine;
//import org.thymeleaf.spring4.view.ThymeleafViewResolver;
//import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
//import org.thymeleaf.templateresolver.TemplateResolver;
//import ru.mail.park.user.UserInterceptor;
//
//@Configuration
//@EnableWebMvc
//public class WebMvcConfig extends WebMvcConfigurerAdapter {
//
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new UserInterceptor(usersConnectionRepository));
//    }
//
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/signin");
//        registry.addViewController("/signout");
//    }
//
//    private @Inject UsersConnectionRepository usersConnectionRepository;
//
//}
