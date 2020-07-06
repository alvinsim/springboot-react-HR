package com.example.hr.service;

import com.example.hr.controller.responsehandling.EntityNotFoundException;
import com.example.hr.domain.Employee;
import com.example.hr.repository.EmployeeRepository;
import com.google.common.collect.Iterables;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

    @MockBean
    private EmployeeRepository repository;

    @Autowired
    private EmployeeService service;

    @Test public void testInjectedComponentsAreNotNull() {
        assertNotNull(repository);
        assertNotNull(service);
    }

    @Test
    public void testGetEmployeeByIdShouldReturnEmployeeWhenFound() {
        int employeeId = 10;
        Optional<Employee> employee = Optional.of(
                Employee.builder().id(employeeId).firstName("Dummy").lastName("Test").build());
        when(repository.findById(employeeId)).thenReturn(employee);

        assertEquals(service.getEmployeeById(employeeId).getId(), employeeId);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetEmployeeByIdShouldThrowExceptionWhenEmployeeIdIsNotFound() {
        service.getEmployeeById(-1);
    }

    @Test
    public void testGetAllEmployeesShouldReturnAllEmployeesWhenFound() {
        Employee maria = Employee.builder().id(1).firstName("Maria").build();
        Employee suzy = Employee.builder().id(2).firstName("Suzy").build();
        List<Employee> employees = new ArrayList<>();
        employees.add(maria);
        employees.add(suzy);
        when(repository.findAll()).thenReturn(employees);

        assertEquals(Iterables.size(service.getAllEmployees()), employees.size());
    }
}