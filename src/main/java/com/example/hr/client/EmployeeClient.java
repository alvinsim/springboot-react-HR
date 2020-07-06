package com.example.hr.client;

import com.example.hr.domain.Employee;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeClient {

    private final RestTemplate restTemplate;

    public EmployeeClient(RestTemplateBuilder builder) {
        restTemplate = builder.build();
    }

    public Employee getEmployee(int id) {
        return restTemplate.getForObject("/api/employees/{id}", Employee.class, id);
    }
}
