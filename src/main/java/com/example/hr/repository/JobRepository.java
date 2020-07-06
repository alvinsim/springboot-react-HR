package com.example.hr.repository;

import com.example.hr.domain.Job;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<Job, Integer> {
}
