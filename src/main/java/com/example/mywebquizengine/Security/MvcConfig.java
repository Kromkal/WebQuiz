package com.example.mywebquizengine.Security;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("singin");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**", "/img/**", "/video/**", "/.well-known/pki-validation/**" , "yandex_135f209071de02b1.html", "googlee45a32e3d6f7edf4.html")
                .addResourceLocations("classpath:/static/", "file:" + System.getProperty("user.dir") + "/img/",
                                                            "file:" + System.getProperty("user.dir") + "/video/",
                                                            "file:" + System.getProperty("user.dir") + "/.well-known/pki-validation/" ,
                                                            "file:" + System.getProperty("user.dir") +  "/yandex_135f209071de02b1.html",
                                                            "file:" + System.getProperty("user.dir") +  "/googlee45a32e3d6f7edf4.html");
        // for Windows
        /*.addResourceLocations("classpath:/static/", "file:C:\\Users\\avlad\\IdeaProjects\\WebQuiz\\img\\",
                "file:C:\\Users\\avlad\\IdeaProjects\\WebQuiz\\video\\", "file:C:\\Users\\avlad\\IdeaProjects\\WebQuiz\\.well-known\\pki-validation\\");*/
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("502400KB");
        factory.setMaxRequestSize("502400KB");
        return factory.createMultipartConfig();
    }

    /*@Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }*/

}
