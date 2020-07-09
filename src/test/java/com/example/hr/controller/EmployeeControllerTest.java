package com.example.hr.controller;

import com.example.hr.controller.responsehandling.EntityNotFoundException;
import com.example.hr.domain.*;
import com.example.hr.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService service;

    private Country country;
    private Department department;
    private Employee john;
    private Employee maria;
    private Employee suzy;
    private List<Employee> employees;
    private Location location;
    private Region region;

    @Before
    public void setup() {
        region = Region.builder().id(101).regionName("Some Region").build();
        country = Country.builder().id(101).countryCode("AA").countryName("Some Country").region(region).build();
        location = Location
                .builder()
                .id(101)
                .streetAddress("Some Street")
                .stateProvince("Some State")
                .postalCode("1234567")
                .city("Some City")
                .country(country)
                .build();
        department = Department.builder().id(101).departmentName("Some Department").location(location).build();
        Job manager = Job.builder().id(101).jobTitle("Manager").minSalary(5000.00).maxSalary(6000.00).build();
        Job programmer = Job.builder().id(102).jobTitle("Programmer").minSalary(4000.00).maxSalary(4500.00).build();
        Job dba = Job.builder().id(103).jobTitle("DBA").minSalary(4000.00).maxSalary(4500.00).build();
        john = Employee
                .builder()
                .id(501)
                .firstName("John")
                .lastName("Long")
                .email("john.l@example.org")
                .phoneNumber("0234567891")
//                .hireDate(LocalDate.now())
                .salary(6000.00)
                .job(manager)
                .manager(null)
                .department(department)
                .build();
        maria = Employee
                .builder()
                .id(502)
                .firstName("Maria")
                .lastName("Sanchez")
                .email("maria.s@example.org")
                .phoneNumber("0123456789")
                .hireDate(LocalDate.now())
                .salary(4000.00)
                .job(programmer)
                .manager(john)
                .department(department)
                .build();
        suzy = Employee
                .builder()
                .id(503)
                .firstName("Suzy")
                .lastName("Lee")
                .email("suzy.l@example.org")
                .phoneNumber("0345678912")
                .salary(4500.00)
                .job(dba)
                .manager(john)
                .department(department)
                .build();
        employees = new ArrayList<>();
        employees.add(john);
        employees.add(maria);
        employees.add(suzy);
    }

    @Test
    public void testGetAlLEmployeesShouldReturnAllEmployees() throws Exception {
        when(service.getAllEmployees()).thenReturn(employees);

        mvc.perform(
                get("/api/employees")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(employees.size()))
                .andExpect(jsonPath("$.data[0].id").value(john.getId()))
                .andExpect(jsonPath("$.data[1].id").value(maria.getId()))
                .andExpect(jsonPath("$.data[2].id").value(suzy.getId()));
    }

    @Test
    public void testGetEmployeeByIdShouldReturnEmployee() throws Exception {
        when(service.getEmployeeById(maria.getId())).thenReturn(maria);

        mvc.perform(
                get("/api/employee/{id}", maria.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(maria.getId()));
    }

    @Test
    public void testGetEmployeeByIdWithInvalidIdShouldReturnHttpStatusNotFound() throws Exception {
        int employeeId = -1;
        when(service.getEmployeeById(employeeId))
                .thenThrow(new EntityNotFoundException(Employee.class, "id", String.valueOf(employeeId)));

        mvc.perform(
                get("/api/employee/{id}", employeeId)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message")
                        .value(String.format("Employee was not found for parameters {id=%d}", employeeId)));
    }

    @Test
    public void testSaveEmployeeShouldReturnNewEmployee() throws Exception {
        Job tester = Job.builder().id(104).jobTitle("Tester").minSalary(4000.00).maxSalary(4500.00).build();
        Employee jane = Employee
                .builder()
                .id(504)
                .firstName("Jane")
                .lastName("Lou")
                .email("jane.l@example.org")
                .phoneNumber("0456789123")
                .salary(4500.00)
//                .hireDate(LocalDate.parse("2020-07-06"))
                .job(tester)
                .manager(john)
                .department(department)
                .build();
        when(service.saveEmployee(jane)).thenReturn(jane);
        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(
                post("/api/employee")
                        .content(objectMapper.writeValueAsBytes(jane))
                        .characterEncoding("UTF-8")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(jane.getId()));
    }

    @Test
    public void testDeleteEmployeeShouldReturnSuccess() throws Exception {
        when(service.getEmployeeById(maria.getId())).thenReturn(maria);
        doNothing().when(service).deleteEmployeeById(maria.getId());

        mvc.perform(
                delete("/api/employee/{id}", maria.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEmployeeWithInvalidEmployeeIdShouldReturnError() throws Exception {
        int employeeId = -1;
        doThrow(new EntityNotFoundException(Employee.class, "id", String.valueOf(employeeId)))
                .when(service).deleteEmployeeById(employeeId);

        mvc.perform(
                delete("/api/employee/{id}", employeeId)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
        )
                .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value("error"))
        .andExpect(jsonPath("$.message")
                .value(String.format("Employee was not found for parameters {id=%d}", employeeId)));
    }
}