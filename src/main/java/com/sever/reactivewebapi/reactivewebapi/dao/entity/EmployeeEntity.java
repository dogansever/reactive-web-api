package com.sever.reactivewebapi.reactivewebapi.dao.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("employees")
public class EmployeeEntity {

    @Id
    private String id;

    private String name;
    @CreatedDate
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    void prePersist() {
        createTime = LocalDateTime.now();
    }

    void preUpdate() {
        updateTime = LocalDateTime.now();
    }
}
