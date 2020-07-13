package com.example.hr.controller;

import com.example.hr.controller.responsehandling.ApiResponse;
import com.example.hr.controller.responsehandling.EntityNotFoundException;
import com.example.hr.domain.Job;
import com.example.hr.service.JobService;
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
public class JobController extends AbstractController {

    @Autowired
    private JobService service;

    @GetMapping("/jobs")
    public ResponseEntity<ApiResponse> getAllJobs() {
        Iterable<Job> jobs = service.getAllJobs();
        ApiResponse apiResponse = ApiResponse.builder().status(RESPONSE_STATUS_SUCCESS).data(jobs).build();
        return new ResponseEntity<>(apiResponse, OK);
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<ApiResponse> getJobById(@PathVariable("id") int id) throws EntityNotFoundException {
        Job job = service.getJobById(id);
        ApiResponse apiResponse = ApiResponse.builder().status(RESPONSE_STATUS_SUCCESS).data(job).build();
        return new ResponseEntity<>(apiResponse, OK);
    }

    @PostMapping("/jobs")
    public ResponseEntity<ApiResponse> addJob(@Valid @RequestBody Job job) {
        Job newJob = service.saveJob(job);
        ApiResponse apiResponse = ApiResponse.builder().status(RESPONSE_STATUS_SUCCESS).data(newJob).build();
        return new ResponseEntity<>(apiResponse, OK);
    }

    @PutMapping("/jobs")
    public ResponseEntity<ApiResponse> updateJob(@Valid @RequestBody Job job) {
        Job updatedJob = service.updateJob(job);
        ApiResponse apiResponse = ApiResponse.builder().status(RESPONSE_STATUS_SUCCESS).data(updatedJob).build();
        return new ResponseEntity<>(apiResponse, OK);
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<ApiResponse> deleteJob(@PathVariable("id") int id) throws EntityNotFoundException {
        service.deleteJobById(id);
        ApiResponse apiResponse = ApiResponse.builder().status(RESPONSE_STATUS_SUCCESS).build();
        return new ResponseEntity<>(apiResponse, OK);
    }
}
