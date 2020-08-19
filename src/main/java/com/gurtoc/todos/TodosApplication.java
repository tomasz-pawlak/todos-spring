package com.gurtoc.todos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;
//import org.springframework.validation.Validator;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@EnableAsync//włączenie asynch
@SpringBootApplication
public class TodosApplication implements RepositoryRestConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(TodosApplication.class, args);
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
//        validatingListener.addValidator("beforeCreate", validator());
//        validatingListener.addValidator("afterCreate", validator());
    }

    @Bean
    Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
