package com.example.hr.repository;

import com.example.hr.domain.Country;
import com.example.hr.domain.Region;
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
public class CountryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CountryRepository repository;

    private Country country;
    private Region region;

    @Before
    public void setup() {
        region = Region.builder().regionName("Some Region").build();
        region = entityManager.persist(region);
        country = Country.builder().countryCode("AA").countryName("Some Country").region(region).build();
        country = entityManager.persist(country);
    }

    @Test
    public void testInjectedComponentsAreNotNull() {
        assertNotNull(entityManager);
        assertNotNull(repository);
    }

    @Test
    public void testFindByIdShouldReturnCountry() {
        assertEquals(country, repository.findById(country.getId()).get());
    }

    @Test
    public void testFindByIdShouldReturnEmptyWhenCountryIsNotFound() {
        assertTrue(repository.findById(-1).isEmpty());
    }

    @Test
    public void testSaveShouldReturnSavedCountry() {
        Country anotherCountry = Country.builder().countryCode("BB").countryName("Another Country").region(region).build();
        anotherCountry = entityManager.persist(anotherCountry);

        assertEquals(anotherCountry, repository.findById(anotherCountry.getId()).get());
    }

    @Test
    public void testSaveWithNewDataShouldReturnUpdatedCountry() {
        country = Country.builder().countryCode("CC").countryName("Some Other Country").region(region).build();
        country = repository.save(country);

        assertNotNull(country);
        assertEquals("Some Other Country", repository.findById(country.getId()).get().getCountryName());
    }

    @Test
    public void testDeleteShouldReturnNoCountry() {
        repository.deleteById(country.getId());

        assertTrue(repository.findById(country.getId()).isEmpty());
    }
}