package com.ddai.spring5.webflux.dao;

import com.ddai.spring5.webflux.model.EmployeeDTO;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.LinkedHashMap;

@Repository
public class EmployeeDAO {
    private static Map<String, EmployeeDTO> employeeLookup;

    static
    {
        employeeLookup = new LinkedHashMap<>();
        for (int i = 0; i < 50; ++i) {
            employeeLookup.put(String.valueOf(i), new EmployeeDTO(String.valueOf(i), "Employee " + i));
        }
    }

    public Mono<EmployeeDTO> findById(String id) {
        return Mono.just(employeeLookup.get(id));
    }

    public Flux<EmployeeDTO> findAll() {
        return Flux.fromIterable(employeeLookup.values());
    }

}
