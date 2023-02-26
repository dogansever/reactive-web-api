package com.sever.reactivewebapi.reactivewebapi.external;

import com.sever.reactivewebapi.reactivewebapi.dao.entity.EmployeeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class EmployeeWebClientTest {

    @Autowired
    private EmployeeWebClient employeeWebClient;

    @Test
    void getEmployeeByName() {
        Mono<EmployeeEntity> employeeEntityMono = employeeWebClient.getEmployeeByName("Jack");
        StepVerifier.create(employeeEntityMono)
                .expectNextMatches(employeeEntity -> employeeEntity.getName().equals("Jack"))
                .verifyComplete();
    }

    @Test
    void getEmployee() {
        Flux<EmployeeEntity> employeeEntityFlux = employeeWebClient.getEmployees();
        StepVerifier.create(employeeEntityFlux)
                .expectNextCount(3)
                .verifyComplete();
    }
}