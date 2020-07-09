package com.example.hr.controller;

import com.example.hr.controller.responsehandling.EntityNotFoundException;
import com.example.hr.domain.Job;
import com.example.hr.service.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(JobController.class)
public class JobControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private JobService service;

    private Job qaTest;
    private Job softwareDeveloper;
    private Job dba;
    private List<Job> jobs;

    @Before
    public void setup() {
        qaTest = Job.builder().id(101).jobTitle("QA Tester").maxSalary(5000.00).minSalary(4000.00).build();
        softwareDeveloper = Job
                .builder().id(102).jobTitle("Software Developer").maxSalary(5000.00).minSalary(4000.00).build();
        dba = Job.builder().id(103).jobTitle("DBA").maxSalary(5500.00).minSalary(5000.00).build();
        jobs = new ArrayList<>(Arrays.asList(qaTest, softwareDeveloper, dba));
    }

    @Test
    public void testGetAllJobsShouldReturnAllJobs() throws Exception {
        when(service.getAllJobs()).thenReturn(jobs);

        mvc.perform(
                get("/api/jobs")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(jobs.size()))
                .andExpect(jsonPath("$.data[0].id").value(jobs.get(0).getId()))
                .andExpect(jsonPath("$.data[1].id").value(jobs.get(1).getId()))
                .andExpect(jsonPath("$.data[2].id").value(jobs.get(2).getId()));
    }

    @Test
    public void testGetJobByIdShouldReturnJob() throws Exception {
        when(service.getJobById(dba.getId())).thenReturn(dba);

        mvc.perform(
                get("/api/job/{id}", dba.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(dba.getId()));
    }

    @Test
    public void testGetJobByIdWithInvalidIdShouldReturnHttpStatusNotFound() throws Exception {
        int jobId = -1;
        when(service.getJobById(jobId)).thenThrow(new EntityNotFoundException(Job.class, "id", String.valueOf(jobId)));

        mvc.perform(
                get("/api/job/{id}", jobId)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message")
                        .value(String.format("Job was not found for parameters {id=%d}", jobId)));
    }

    @Test
    public void testSaveJobShouldReturnNewJob() throws Exception {
        Job officeManager = Job
                .builder().id(104).jobTitle("Office Manager").maxSalary(6000.00).minSalary(5000.00).build();
        when(service.saveJob(officeManager)).thenReturn(officeManager);
        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(
                post("/api/job/")
                        .content(objectMapper.writeValueAsBytes(officeManager))
                        .characterEncoding("UTF-8")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(officeManager.getId()));
    }

    @Test
    public void testDeleteJobShouldReturnSuccess() throws Exception {
        Job secretary = Job.builder().id(105).jobTitle("Secretary").maxSalary(4000.00).minSalary(2000.00).build();
        when(service.getJobById(secretary.getId())).thenReturn(secretary);
        doNothing().when(service).deleteJobById(secretary.getId());

        mvc.perform(
                delete("/api/job/{id}", secretary.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteJobWithInvalidJobIdShouldReturnError() throws Exception {
        int jobId = -1;
        doThrow(new EntityNotFoundException(Job.class, "id", String.valueOf(jobId)))
                .when(service).deleteJobById(jobId);

        mvc.perform(
                delete("/api/job/{id}", jobId)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message")
                        .value(String.format("Job was not found for parameters {id=%d}", jobId)));
    }
}