package com.gurtoc.todos.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//configurowanie innych adnotacji, na jakich elementach?
@Retention(RetentionPolicy.RUNTIME)//jak dlugo ta dmotacia ma zostawac, dostepna w runtime itp
@interface IllegalExceptionProcessing{

}
