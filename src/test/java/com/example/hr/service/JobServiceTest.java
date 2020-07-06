package com.example.hr.service;

import com.example.hr.controller.responsehandling.EntityNotFoundException;
import com.example.hr.domain.Job;
import com.example.hr.repository.JobRepository;
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
public class JobServiceTest {

    @MockBean
    private JobRepository repository;

    @Autowired
    private JobService service;

    @Test
    public void testInjectedComponetsAreNotNull() {
        assertNotNull(repository);
        assertNotNull(service);
    }

    @Test
    public void testGetJobByIdShouldReturnTheJobWhenFound() {
        int jobId = 1;
        Optional<Job> job = Optional.of(
                Job.builder().id(jobId).jobTitle("Test Job").maxSalary(2000.00).minSalary(1000.00).build());
        when(repository.findById(jobId)).thenReturn(job);

        assertEquals(service.getJobById(jobId), job.get());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetJobByIdShouldThrowExceptionWhenJobIdIsNotFound() {
        service.getJobById(-1);
    }

    @Test
    public void testGetAllJobsShouldReturnAllJobsWhenFound() {
        Job secretary = Job.builder().id(100).jobTitle("Secretary").maxSalary(3000.00).minSalary(2000.00).build();
        Job officeManager = Job.builder().id(101).jobTitle("Office Manager").maxSalary(4000.00).minSalary(3000.00).build();
        List<Job> jobs = new ArrayList<>();
        jobs.add(secretary);
        jobs.add(officeManager);
        when(repository.findAll()).thenReturn(jobs);

        assertEquals(Iterables.size(service.getAllJobs()), jobs.size());
    }
}
