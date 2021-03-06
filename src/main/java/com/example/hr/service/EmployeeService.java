package com.example.hr.service;

import com.example.hr.controller.responsehandling.EntityNotFoundException;
import com.example.hr.domain.Employee;
import com.example.hr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public Employee getEmployeeById(int id) {
        Optional<Employee> employee = repository.findById(id);
        employee.orElseThrow(() -> new EntityNotFoundException(Employee.class, "id", String.valueOf(id)));
        return employee.get();
    }

    public Iterable<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee saveEmployee(Employee employee) {
        return repository.save(employee);
    }

    public Employee updateEmployee(Employee employee) {
        return repository.findById(employee.getId())
                .map(emp -> {
                    emp.setFirstName(employee.getFirstName());
                    emp.setLastName(employee.getLastName());
                    emp.setEmail(employee.getEmail());
                    emp.setDepartment(employee.getDepartment());
                    emp.setHireDate(employee.getHireDate());
                    emp.setJob(employee.getJob());
                    emp.setManager(employee.getManager());
                    emp.setPhoneNumber(employee.getPhoneNumber());
                    emp.setSalary(employee.getSalary());
                    return repository.save(emp);
                }).orElseThrow(() -> new EntityNotFoundException(Employee.class, "id", String.valueOf(employee.getId())));
    }

    public void deleteEmployeeById(int id) {
        Employee employee = getEmployeeById(id);
        repository.deleteById(employee.getId());
    }
}
