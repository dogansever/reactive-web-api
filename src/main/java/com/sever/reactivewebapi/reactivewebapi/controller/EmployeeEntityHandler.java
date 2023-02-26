package com.sever.reactivewebapi.reactivewebapi.controller;

import com.sever.reactivewebapi.reactivewebapi.dao.EmployeeRepository;
import com.sever.reactivewebapi.reactivewebapi.dao.entity.EmployeeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class EmployeeEntityHandler {

    private final EmployeeRepository employees;

    public Mono<ServerResponse> all(ServerRequest req) {
        return ServerResponse.ok().body(this.employees.findAll(), EmployeeEntity.class);
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(EmployeeEntity.class)
                .flatMap(this.employees::save)
                .flatMap(p -> ServerResponse.created(URI.create("/employee/" + p.getId())).build());
    }

    public Mono<ServerResponse> get(ServerRequest req) {
        return this.employees.findById(req.pathVariable("id"))
                .flatMap(post -> ServerResponse.ok().body(Mono.just(post), EmployeeEntity.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        return ServerResponse.noContent().build(this.employees.deleteById(req.pathVariable("id")));
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        return Mono
                .zip(
                        data -> {
                            EmployeeEntity p = (EmployeeEntity) data[0];
                            EmployeeEntity p2 = (EmployeeEntity) data[1];
                            p.setName(p2.getName());
                            return p;
                        },
                        this.employees.findById(req.pathVariable("id")),
                        req.bodyToMono(EmployeeEntity.class)
                )
                .cast(EmployeeEntity.class)
                .flatMap(this.employees::save)
                .flatMap(post -> ServerResponse.noContent().build());
    }
}
