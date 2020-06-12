package com.ddai.spring5.webflux.controller;

import com.ddai.spring5.webflux.dao.EmployeeDAO;
import com.ddai.spring5.webflux.model.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping(value = "employee")
public class EmployeeController {

    private EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeController(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @GetMapping(value = "/{id}")
    public Mono<EmployeeDTO> getEmployeeById(@PathVariable("id") String id) {
        return employeeDAO.findById(id);
    }

    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<EmployeeDTO> getAllEmployees() {
        return employeeDAO.findAll()
                .delayElements(Duration.ofMillis(100));
    }

    @GetMapping(value = "/all/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<EmployeeDTO> getAllEmployeesByStream() {
        return employeeDAO.findAll()
                .delayElements(Duration.ofMillis(100))
                .log();
    }

    @GetMapping(value = "/all/buffered/stream/", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<List<EmployeeDTO>> getAllEmployeesByStreamedList() {
        return employeeDAO.findAll()
                .delayElements(Duration.ofMillis(100))
                .buffer(10)
                .log();
    }

}
