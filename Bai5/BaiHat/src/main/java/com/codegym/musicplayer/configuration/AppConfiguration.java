package com.codegym.musicplayer.configuration;

import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import jakarta.persistence.EntityManager;

@Configuration
@EnableWebMvc
@ComponentScan("com.codegym.musicplayer")
public class AppConfiguration implements WebMvcConfigurer, ApplicationContextAware {

    private ApplicationContext applicationContext;

    // Đường dẫn thư mục vật lý lưu trữ file nhạc upload trên ổ cứng
    public static final String UPLOAD_PATH = "D:/music_player_uploads/";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    // ==========================================
    // Cấu hình Thymeleaf Template Engine & ViewResolver
    // ==========================================
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    // ==========================================
    // Cấu hình Multipart Resolver (Upload File)
    // ==========================================
    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    // ==========================================
    // Cấu hình Hibernate SessionFactory & EntityManager
    // ==========================================
    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.conf.xml")
                .buildSessionFactory();
    }

    @Bean
    public EntityManager entityManager(SessionFactory sessionFactory) {
        return sessionFactory.createEntityManager();
    }

    // ==========================================
    // Cấu hình Resource Handlers (Static files & Audio mapping)
    // ==========================================
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map URL /audio/** tới thư mục vật lý lưu trữ file nhạc
        registry.addResourceHandler("/audio/**")
                .addResourceLocations("file:" + UPLOAD_PATH);

        // Map URL /static/** cho CSS, JS nếu có
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/static/");
    }
}
