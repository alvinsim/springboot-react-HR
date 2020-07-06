package com.example.hr.repository;

import com.example.hr.domain.Job;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JobRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private JobRepository repository;

    private Job job;

    @Before
    public void setup() {
        job = Job.builder().jobTitle("Some Job").maxSalary(1000.00).minSalary(1000.00).build();
        job = entityManager.persist(job);
    }

    @Test
    public void testInjectedComponentsAreNotNull() {
        assertNotNull(entityManager);
        assertNotNull(repository);
    }

    @Test
    public void testFindByIdShouldReturnJob() {
        assertEquals(repository.findById(job.getId()).get(), job);
    }

    @Test
    public void testFindByIdShouldReturnEmptyWhenJobIsNotFound() {
        assertTrue(repository.findById(-1).isEmpty());
    }

    @Test
    public void testSaveShouldReturnSavedJob() {
        Job anotherJob = Job.builder().jobTitle("Another Job").maxSalary(1100.00).minSalary(1100.00).build();
        anotherJob = repository.save(anotherJob);

        assertNotNull(anotherJob);
        assertEquals(repository.findById(anotherJob.getId()).get(), anotherJob);
    }

    @Test
    public void testSaveWithNewDataShouldReturnUpdatedJob() {
        job.setMaxSalary(2000.00);
        job = repository.save(job);

        assertNotNull(job);
        assertEquals(2000.00, repository.findById(job.getId()).get().getMaxSalary());
    }

    @Test
    public void testDeleteShouldReturnNoJob() {
        repository.deleteById(job.getId());

        assertTrue(repository.findById(job.getId()).isEmpty());
    }
}