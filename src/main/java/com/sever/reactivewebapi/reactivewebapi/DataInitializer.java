package com.sever.reactivewebapi.reactivewebapi;

import com.sever.reactivewebapi.reactivewebapi.dao.EmployeeRepository;
import com.sever.reactivewebapi.reactivewebapi.dao.entity.EmployeeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
class DataInitializer implements CommandLineRunner {

    private final EmployeeRepository posts;


    @Override
    public void run(String[] args) {
        log.info("start data initialization  ...");
        this.posts
                .deleteAll()
                .thenMany(
                        Flux
                                .just("Walter", "Jesse")
                                .flatMap(
                                        name -> {
                                            String id = UUID.randomUUID().toString();
                                            return this.posts.save(EmployeeEntity.builder().id(id).name(name).build());
                                        }
                                )
                )
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("done initialization...")
                );
    }
}
