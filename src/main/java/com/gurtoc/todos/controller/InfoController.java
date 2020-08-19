package com.gurtoc.todos.controller;

import com.gurtoc.todos.TaskConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/info")
public class InfoController {


    private DataSourceProperties dataSourceProperties;
    private TaskConfigurationProperties myProp;

    public InfoController(DataSourceProperties dataSourceProperties, TaskConfigurationProperties myProp) {
        this.dataSourceProperties = dataSourceProperties;
        this.myProp = myProp;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/url")
    String url() {
        return dataSourceProperties.getUrl();
    }

    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/prop")
    boolean myProp() {
        return myProp.getTemplate().isAllowMultipleTasks();
    }
}
