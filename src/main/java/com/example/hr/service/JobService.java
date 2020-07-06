package com.example.hr.service;

import com.example.hr.controller.responsehandling.EntityNotFoundException;
import com.example.hr.domain.Job;
import com.example.hr.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository repository;

    public Job getJobById(int id) {
        Optional<Job> job = repository.findById(id);
        job.orElseThrow(() -> new EntityNotFoundException(Job.class, "id", String.valueOf(id)));
        return job.get();
    }

    public Iterable<Job> getAllJobs() {
        return repository.findAll();
    }

    public Job saveJob(Job job) {
        return repository.save(job);
    }

    public void deleteJobById(int id) {
        Job job = getJobById(id);
        repository.deleteById(job.getId());
    }
}
