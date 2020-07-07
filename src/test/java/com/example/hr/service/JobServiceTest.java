package com.example.hr.service;

import com.example.hr.controller.responsehandling.EntityNotFoundException;
import com.example.hr.domain.Job;
import com.example.hr.repository.JobRepository;
import com.google.common.collect.Iterables;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobServiceTest {

    @MockBean
    private JobRepository repository;
    @MockBean
    private JobService service;

    @Test
    public void testInjectedComponetsAreNotNull() {
        assertNotNull(repository);
        assertNotNull(service);
    }

    @Test
    public void testGetJobByIdShouldReturnTheJobWhenFound() {
        int jobId = 100;
        Job job = Job.builder().id(jobId).jobTitle("Test Job").maxSalary(2000.00).minSalary(1000.00).build();
        when(service.getJobById(jobId)).thenReturn(job);

        assertEquals(job, service.getJobById(jobId));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetJobByIdShouldThrowExceptionWhenJobIdIsNotFound() {
        int jobId = 101;
        when(service.getJobById(jobId)).thenThrow(new EntityNotFoundException(Job.class, "id", String.valueOf(jobId)));

        service.getJobById(jobId);
    }

    @Test
    public void testGetAllJobsShouldReturnAllJobsWhenFound() {
        Job secretary = Job.builder().id(102).jobTitle("Secretary").maxSalary(3000.00).minSalary(2000.00).build();
        Job officeManager = Job.builder().id(103).jobTitle("Office Manager").maxSalary(4000.00).minSalary(3000.00).build();
        List<Job> jobs = new ArrayList<>();
        jobs.add(secretary);
        jobs.add(officeManager);
        when(service.getAllJobs()).thenReturn(jobs);

        assertEquals(jobs.size(), Iterables.size(service.getAllJobs()));
    }

    @Test
    public void testSaveJobShouldReturnNewJob() {
        Job newJob = Job.builder().id(104).jobTitle("Some Job").maxSalary(1000.00).minSalary(1000.00).build();
        when(service.saveJob(newJob)).thenReturn(newJob);

        Job actualJob = service.saveJob(newJob);

        assertNotNull(actualJob);
        assertEquals(104, actualJob.getId());
    }

    @Test
    public void testUpdateJobShouldReturnUpdatedJob() {
        Job updatedJob = Job.builder().id(105).jobTitle("Some Job").maxSalary(1000.00).minSalary(1000.00).build();
        when(service.saveJob(updatedJob)).thenReturn(updatedJob);

        Job actualJob = service.saveJob(updatedJob);

        assertNotNull(actualJob);
        assertEquals(105, actualJob.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteJobWithInvalidIdShouldThrowException() {
        doThrow(EntityNotFoundException.class).when(service).deleteJobById(-1);

        service.deleteJobById(-1);
    }
}
