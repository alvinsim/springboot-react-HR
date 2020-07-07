package com.example.hr.service;

import com.example.hr.controller.responsehandling.EntityNotFoundException;
import com.example.hr.domain.*;
import com.example.hr.repository.*;
import com.google.common.collect.Iterables;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

    @MockBean
    private CountryRepository countryRepository;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private JobRepository jobRepository;
    @MockBean
    private LocationRepository locationRepository;
    @MockBean
    private RegionRepository regionRepository;
    @MockBean
    private EmployeeService service;

    private Optional<Country> country;
    private Optional<Department> department;
    private Optional<Job> job;
    private Optional<Region> region;

    @Before
    public void setup() {
        region = Optional.ofNullable(Region.builder().regionName("Some Region").build());
        when(regionRepository.findById(region.get().getId())).thenReturn(region);
        country = Optional.ofNullable(
                Country.builder().countryCode("AA").countryName("Some Country").region(region.get()).build());
        when(countryRepository.findById(country.get().getId())).thenReturn(country);
        Optional<Location> location = Optional.ofNullable(
                Location.builder()
                        .streetAddress("Some Street Address")
                        .stateProvince("Some State")
                        .postalCode("1234567")
                        .city("Some City")
                        .country(country.get())
                        .build());
        when(locationRepository.findById(location.get().getId())).thenReturn(location);
        department = Optional.ofNullable(
                Department.builder().departmentName("Some Department").location(location.get()).build());
        when(departmentRepository.findById(department.get().getId())).thenReturn(department);
        job = Optional.ofNullable(Job.builder().jobTitle("Some Job").minSalary(1000.00).maxSalary(2000.00).build());
    }

    @Test
    public void testInjectedComponentsAreNotNull() {
        assertNotNull(countryRepository);
        assertNotNull(departmentRepository);
        assertNotNull(jobRepository);
        assertNotNull(locationRepository);
        assertNotNull(regionRepository);
        assertNotNull(service);
    }

    @Test
    public void testGetEmployeeByIdShouldReturnEmployeeWhenFound() {
        Employee employee = Employee
                .builder()
                .id(101)
                .firstName("Some First Name")
                .lastName("Some Last Name")
                .salary(2000.00)
                .phoneNumber("012345678")
                .email("some@example.org")
                .department(department.get())
                .job(job.get())
                .build();
        when(service.getEmployeeById(employee.getId())).thenReturn(employee);

        assertEquals(employee, service.getEmployeeById(employee.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetEmployeeByIdShouldThrowExceptionWhenEmployeeIdIsNotFound() {
        when(service.getEmployeeById(-1))
                .thenThrow(new EntityNotFoundException(Employee.class, "id", String.valueOf(-1)));

        service.getEmployeeById(-1);
    }

    @Test
    public void testGetAllEmployeesShouldReturnAllEmployeesWhenFound() {
        Employee maria = Employee
                .builder()
                .id(102)
                .firstName("Maria")
                .lastName("Lang")
                .salary(2000.00)
                .phoneNumber("0234567891")
                .email("maria@example.org")
                .department(department.get())
                .job(job.get())
                .build();
        Employee suzy = Employee.builder()
                .id(103)
                .firstName("Suzy")
                .lastName("Lee")
                .salary(3000.00)
                .phoneNumber("0345678912")
                .email("suzy@example.org")
                .department(department.get())
                .job(job.get())
                .build();
        List<Employee> employees = new ArrayList<>();
        employees.add(maria);
        employees.add(suzy);
        when(service.getAllEmployees()).thenReturn(employees);

        assertEquals(2, Iterables.size(service.getAllEmployees()));
    }

    @Test
    public void testSaveEmployeeShouldReturnNewEmployee() {
        Employee newEmployee = Employee
                .builder()
                .id(103)
                .firstName("Some First Name")
                .lastName("Some Last Name")
                .salary(2500.00)
                .phoneNumber("0123456789")
                .email("some@example.org")
                .department(department.get())
                .job(job.get())
                .build();
        when(service.saveEmployee(newEmployee)).thenReturn(newEmployee);

        Employee actualEmployee = service.saveEmployee(newEmployee);

        assertNotNull(actualEmployee);
        assertEquals(103, actualEmployee.getId());
    }

    @Test
    public void testUpdateEmployeeShouldReturnUpdatedEmployee() {
        Employee updatedEmployee = Employee
                .builder()
                .id(104)
                .firstName("Some First Name")
                .lastName("Some Last Name")
                .salary(2500.00)
                .phoneNumber("0123456789")
                .email("some@example.org")
                .department(department.get())
                .job(job.get())
                .build();
        when(service.updateEmployee(updatedEmployee)).thenReturn(updatedEmployee);

        assertNotNull(updatedEmployee);
        assertEquals(104, updatedEmployee.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteEmployeeWithInvalidIdShouldThrowException() {
        doThrow(EntityNotFoundException.class).when(service).deleteEmployeeById(-1);

        service.deleteEmployeeById(-1);
    }
}