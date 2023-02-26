package com.sever.reactivewebapi.reactivewebapi.controller;

import com.sever.reactivewebapi.reactivewebapi.dao.EmployeeRepository;
import com.sever.reactivewebapi.reactivewebapi.dao.entity.EmployeeEntity;
import com.sever.reactivewebapi.reactivewebapi.dto.EmployeeDto;
import com.sever.reactivewebapi.reactivewebapi.mapper.EmployeeMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @GetMapping("/{id}")
    private Mono<EmployeeEntity> findById(@PathVariable String id) {
        return employeeRepository.findById(id);
    }

    @GetMapping
    private Flux<EmployeeEntity> findAll() {
        return employeeRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EmployeeEntity> create(@RequestBody @Validated EmployeeDto dto) {
        log.info("EmployeeController create {}", dto);
        return employeeRepository.save(employeeMapper.toEntity(dto));
    }

    @GetMapping("/v1")
    private EmployeeEntity findByName(@RequestParam(defaultValue = "Mock", required = false) String name) {
        return EmployeeEntity.builder().name(name).build();
    }
}
