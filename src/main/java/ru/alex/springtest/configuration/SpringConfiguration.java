package ru.alex.springtest.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@ComponentScan("ru.alex.springtest")
@EnableWebMvc
public class SpringConfiguration implements WebMvcConfigurer {


    private final ApplicationContext applicationContext;

    @Autowired
    public SpringConfiguration(ApplicationContext applicationContext) { //Внедряем ApplicationContext
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() { //AC используем в бине tR, чтобы настроить Thymeleaf
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");//Задаём путь где путь где хранятся представления
        templateResolver.setSuffix(".html");//Задаём расширение представлений
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() { //Производим конфигурацию представлений
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) { //Метод, который задаёт шаблонизатор Thymeleaf
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }

    // = applicationContext.xml
}
