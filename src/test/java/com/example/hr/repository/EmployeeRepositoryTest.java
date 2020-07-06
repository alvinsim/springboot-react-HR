package com.example.hr.repository;

import com.example.hr.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private EmployeeRepository employeeRepository;

    private Department department;
    private Employee employee;
    private Job job;

    @Before
    public void setup() {
        job = Job.builder().jobTitle("Some Job").maxSalary(2000.00).minSalary(1000.00).build();
        job = entityManager.persist(job);
        Region region = Region.builder().regionName("Some Region").build();
        region = entityManager.persist(region);
        Country country = Country.builder().countryCode("AA").countryName("Some Country").region(region).build();
        country = entityManager.persist(country);
        Location location = Location
                .builder()
                .streetAddress("Some Street")
                .stateProvince("Some State")
                .postalCode("1234567")
                .city("Some City")
                .country(country)
                .build();
        location = entityManager.persist(location);
        department = Department.builder().departmentName("Some Department").location(location).build();
        department = entityManager.persist(department);
        employee = Employee
                .builder()
                .firstName("Some First Name")
                .lastName("Some Last Name")
                .email("Some Email")
                .hireDate(LocalDate.now())
                .job(job)
                .department(department)
                .build();
        employee = entityManager.persist(employee);
    }

    @Test
    public void testInjectedComponentsAreNotNull() {
        assertNotNull(entityManager);
        assertNotNull(employeeRepository);
    }

    @Test
    public void testFindByIdShouldReturnEmployee() {
        assertEquals(employeeRepository.findById(employee.getId()).get(), employee);
    }

    @Test
    public void testFindByIdShouldReturnEmptyWhenEmployeeIsNotFound() {
        assertTrue(employeeRepository.findById(-1).isEmpty());
    }

    @Test
    public void testSaveShouldReturnEmployee() {
        Employee anotherEmployee = Employee
                .builder()
                .firstName("Another First Name")
                .lastName("Another Last Name")
                .email("Another Email")
                .hireDate(LocalDate.now())
                .job(job)
                .department(department)
                .build();
        anotherEmployee = employeeRepository.save(anotherEmployee);

        assertNotNull(anotherEmployee);
        assertEquals(employeeRepository.findById(anotherEmployee.getId()).get(), anotherEmployee);
    }

    @Test
    public void testSaveWithNewDataShouldReturnUpdatedEmployee() {
        employee.setEmail("Some Other Email");
        employee = employeeRepository.save(employee);

        assertNotNull(employee);
        assertEquals("Some Other Email", employeeRepository.findById(employee.getId()).get().getEmail());
    }

    @Test
    public void testDeleteShouldReturnNoEmployee() {
        employeeRepository.delete(employee);

        assertTrue(employeeRepository.findById(employee.getId()).isEmpty());
    }
}