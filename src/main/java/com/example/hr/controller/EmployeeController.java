package com.example.hr.controller;

import com.example.hr.controller.responsehandling.ApiResponse;
import com.example.hr.controller.responsehandling.EntityNotFoundException;
import com.example.hr.domain.Employee;
import com.example.hr.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.hr.controller.responsehandling.ApiResponse.RESPONSE_STATUS_SUCCESS;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
public class EmployeeController extends AbstractController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/employees")
    public ResponseEntity<ApiResponse> getAllEmployees() throws EntityNotFoundException {
        Iterable<Employee> employees = service.getAllEmployees();
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status(RESPONSE_STATUS_SUCCESS)
                .data(employees)
                .build();
        return new ResponseEntity<>(apiResponse, OK);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<ApiResponse> getEmployeeById(@PathVariable("id") int id) throws EntityNotFoundException {
        Employee employee = service.getEmployeeById(id);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status(RESPONSE_STATUS_SUCCESS)
                .data(employee)
                .build();
        return new ResponseEntity<>(apiResponse, OK);
    }

    @PostMapping("/employees")
    public ResponseEntity<ApiResponse> addEmployee(@Valid @RequestBody Employee employee) {
        Employee newEmployee = service.saveEmployee(employee);
        ApiResponse apiResponse = ApiResponse.builder().status(RESPONSE_STATUS_SUCCESS).data(newEmployee).build();
        return new ResponseEntity<>(apiResponse, OK);
    }

    @PutMapping("/employees")
    public ResponseEntity<ApiResponse> updateEmployee(@Valid @RequestBody Employee employee) {
        Employee updatedEmployee = service.updateEmployee(employee);
        ApiResponse apiResponse = ApiResponse.builder().status(RESPONSE_STATUS_SUCCESS).data(updatedEmployee).build();
        return new ResponseEntity<>(apiResponse, OK);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable("id") int id) throws EntityNotFoundException {
        service.deleteEmployeeById(id);
        ApiResponse apiResponse = ApiResponse.builder().status(RESPONSE_STATUS_SUCCESS).build();
        return new ResponseEntity<>(apiResponse, OK);
    }
}
