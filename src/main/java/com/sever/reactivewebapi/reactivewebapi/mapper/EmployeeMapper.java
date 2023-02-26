package com.sever.reactivewebapi.reactivewebapi.mapper;

import com.sever.reactivewebapi.reactivewebapi.dao.entity.EmployeeEntity;
import com.sever.reactivewebapi.reactivewebapi.dto.EmployeeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeEntity toEntity(EmployeeDto dto);
}
