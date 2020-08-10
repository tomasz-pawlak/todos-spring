package com.gurtoc.todos.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

@Configuration//dodatkowa clasa konfiguracyjna, WebMvC dodaje nowe funkcjonalnosci do controllera
public class MvcConfiguration implements WebMvcConfigurer {
    private Set<HandlerInterceptor> interceptorSet;

    //wstrzykniecie wszystkich iterceptorów, które implementuja interface
    public MvcConfiguration(Set<HandlerInterceptor> interceptorSet) {
        this.interceptorSet = interceptorSet;
    }

    //dodawanie iterceptorów
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        interceptorSet.forEach(registry::addInterceptor);
//        dodawanie "z palca"
//        registry.addInterceptor(new LoggerInterceptor());
    }
}
