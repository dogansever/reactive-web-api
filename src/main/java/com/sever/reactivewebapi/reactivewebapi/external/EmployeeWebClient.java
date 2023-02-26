package com.sever.reactivewebapi.reactivewebapi.external;

import com.sever.reactivewebapi.reactivewebapi.dao.entity.EmployeeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class EmployeeWebClient {

    private final WebClient client;

    public EmployeeWebClient(WebClient.Builder builder, @Value("${external.employee-ws.url:http://localhost:8080}") String url) {
        this.client = builder.baseUrl(url).build();
    }

    Flux<EmployeeEntity> getEmployees() {
        Flux<EmployeeEntity> employeeEntityFlux = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/employee")
                        .build())
                .retrieve()
                .bodyToFlux(EmployeeEntity.class);

        employeeEntityFlux.subscribe(m -> log.info("EmployeeEntity retrieved: {}", m));
        return employeeEntityFlux;
    }

    Mono<EmployeeEntity> getEmployeeByName(String name) {
        Mono<EmployeeEntity> employeeMono = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/employee/v1")
                        .queryParam("name", name)
                        .build())
                .retrieve()
                .bodyToMono(EmployeeEntity.class);

        employeeMono.subscribe(m -> log.info("EmployeeEntity retrieved: {}", m));
        return employeeMono;
    }
}
