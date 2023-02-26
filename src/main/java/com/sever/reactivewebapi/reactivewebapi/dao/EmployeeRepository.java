package com.sever.reactivewebapi.reactivewebapi.dao;

import com.sever.reactivewebapi.reactivewebapi.dao.entity.EmployeeEntity;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@Transactional
public class EmployeeRepository {

    ReactiveRedisOperations<String, EmployeeEntity> template;
    public static final String EMPLOYEES = "employees";

    public EmployeeRepository(ReactiveRedisOperations<String, EmployeeEntity> template) {
        this.template = template;
    }


    public Flux<EmployeeEntity> findAll() {
        return template.<String, EmployeeEntity>opsForHash().values(EMPLOYEES);
    }

    public Mono<EmployeeEntity> findById(String id) {
        return template.<String, EmployeeEntity>opsForHash().get(EMPLOYEES, id);
    }

    public Mono<EmployeeEntity> save(EmployeeEntity employee) {
        if (employee.getId() == null) {
            String id = UUID.randomUUID().toString();
            employee.setId(id);
        }
        return template.<String, EmployeeEntity>opsForHash().put(EMPLOYEES, employee.getId(), employee)
                .log()
                .map(p -> employee);

    }

    public Mono<Void> deleteById(String id) {
        return template.<String, EmployeeEntity>opsForHash().remove(EMPLOYEES, id)
                .flatMap(p -> Mono.<Void>empty());
    }

    public Mono<Boolean> deleteAll() {
        return template.<String, EmployeeEntity>opsForHash().delete(EMPLOYEES);
    }
}
