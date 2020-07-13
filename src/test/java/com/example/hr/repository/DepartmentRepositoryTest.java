package com.example.hr.repository;

import com.example.hr.domain.Country;
import com.example.hr.domain.Department;
import com.example.hr.domain.Location;
import com.example.hr.domain.Region;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DepartmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private DepartmentRepository repository;

    private Department department;
    private Location location;

    @Before
    public void setup() {
        Region region = Region.builder().regionName("Some Region").build();
        region = entityManager.persist(region);
        Country country = Country.builder().countryCode("AA").countryName("Some Country").region(region).build();
        country = entityManager.persist(country);
        location = Location
                .builder()
                .streetAddress("Some Street")
                .stateProvince("Some State")
                .postalCode("1234567")
                .city("Some City")
                .country(country)
                .build();
        location = entityManager.persist(location);
        department = Department.builder().departmentName("Some Department").location(location).build();
        department = entityManager.persist(department);
    }

    @Test
    public void testInjectedComponentsAreNotNull() {
        assertNotNull(entityManager);
        assertNotNull(repository);
    }

    @Test
    public void testFindByIdShouldReturnDepartment() {
        assertEquals(department, repository.findById(department.getId()).get());
    }

    @Test
    public void testFindByIdShouldReturnEmptyWhenDepartmentIsNotFound() {
        assertTrue(repository.findById(-1).isEmpty());
    }

    @Test
    public void testSaveShouldReturnSavedDepartment() {
        Department anotherDepartment = Department.builder().departmentName("Another Department").location(location).build();
        anotherDepartment = repository.save(anotherDepartment);

        assertNotNull(anotherDepartment);
        assertEquals(anotherDepartment, repository.findById(anotherDepartment.getId()).get());
    }

    @Test
    public void testDeleteShouldReturnNoDepartment() {
        repository.deleteById(department.getId());

        assertTrue(repository.findById(department.getId()).isEmpty());
    }
}