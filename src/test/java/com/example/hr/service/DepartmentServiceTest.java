package com.example.hr.service;

import com.example.hr.domain.Department;
import com.example.hr.domain.Location;
import com.example.hr.repository.DepartmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class DepartmentServiceTest {

    @MockBean
    private DepartmentRepository repository;

    @Autowired
    private DepartmentService service;

    @Test
    public void testInjectedComponentsAreNotNull() {
        assertNotNull(repository);
        assertNotNull(service);
    }

    @Test
    public void testGetDepartmentByIdShouldReturnTheDepartmentWhenFound() {
//        Location location = Location.builder().id(1).city("Some City").country()
//        int departmentId = 1;
//        Optional<Department> department = Optional.of(
//                Department.builder().id(departmentId).departmentName("Test Department").location().build()
//        );
    }
}