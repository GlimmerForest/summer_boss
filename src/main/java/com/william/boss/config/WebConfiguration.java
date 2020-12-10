package com.william.boss.config;

import com.william.boss.filter.ResponseFilter;
import com.william.boss.aop.CommonHandlerInterceptor;
import com.william.boss.properties.SelfDefineProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * @author john
 */
@Configuration
@EnableWebMvc
@EnableConfigurationProperties(SelfDefineProperties.class)
public class WebConfiguration implements WebMvcConfigurer {

    @Resource
    private SelfDefineProperties selfDefineProperties;

    /**
     * 配置静态资源存放路径
     * @param registry 资源注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceHandler是指你想在url请求的路径 addResourceLocations是资源存放的真实路径
        //springboot默认静态资源路径 "classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/", "/"
        registry.addResourceHandler("/**").addResourceLocations(selfDefineProperties.getResourceBasePaths());
    }

    /**
     * 配置拦截器
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CommonHandlerInterceptor()).addPathPatterns("/user/**", "/demo/**");
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * 配置多语言解析器,默认使用的是 AcceptHeaderLocaleResolver 不支持param传参切语言
     * @return 多语言解析器
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("es", "ES"));
        return localeResolver;
    }

    /**
     * 配置多语言资源文件绑定
     * @return 多语言资源文件绑定器
     */
    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename(selfDefineProperties.getMessageBasePath());
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }


    /**
     * 配置多语言拦截器
     * @return 多语言拦截器
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    /**
     * 注册过滤器
     * @return 过滤器注册器
     */
    @Bean
    public FilterRegistrationBean<ResponseFilter> registerResponseFilter() {
        FilterRegistrationBean<ResponseFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ResponseFilter());
        bean.setName("responseFilter");
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        return bean;
    }
}
