package com.sever.reactivewebapi.reactivewebapi.config;

import com.sever.reactivewebapi.reactivewebapi.controller.EmployeeEntityHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    public static final String EMPLOYEES_V1 = "employees/v1";
    public static final String EMPLOYEES_V1_ID = EMPLOYEES_V1 + "/{id}";
    @Bean
    public RouterFunction<ServerResponse> routes(EmployeeEntityHandler employeeController) {
        return route(GET(EMPLOYEES_V1), employeeController::all)
                .andRoute(POST(EMPLOYEES_V1), employeeController::create)
                .andRoute(GET(EMPLOYEES_V1_ID), employeeController::get)
                .andRoute(PUT(EMPLOYEES_V1_ID), employeeController::update)
                .andRoute(DELETE(EMPLOYEES_V1_ID), employeeController::delete);
    }
}
