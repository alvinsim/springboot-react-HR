package com.example.hr.repository;

import com.example.hr.domain.Country;
import com.example.hr.domain.Location;
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
public class LocationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private LocationRepository repository;

    private Country country;
    private Location location;

    @Before
    public void setup() {
        Region region = Region.builder().regionName("Some Region").build();
        region = entityManager.persist(region);
        country = Country.builder().countryCode("AA").countryName("Some Country").region(region).build();
        country = entityManager.persist(country);
        location = Location
                .builder()
                .streetAddress("Some address")
                .postalCode("1234567")
                .city("Some City")
                .stateProvince("Some State")
                .country(country)
                .build();
        location = entityManager.persist(location);
    }

    @Test
    public void testInjectedComponentsAreNotNull() {
        assertNotNull(entityManager);
        assertNotNull(repository);
    }

    @Test
    public void testFindByIdShouldReturnLocation() {
        assertEquals(repository.findById(location.getId()).get(), location);
    }

    @Test
    public void testFindByIdShouldReturnEmptyWhenLocationIsNotFound() {
        assertTrue(repository.findById(-1).isEmpty());
    }

    @Test
    public void testSaveShouldReturnSavedLocation() {
        Location anotherLocation = Location
                .builder()
                .streetAddress("Another address")
                .postalCode("2345678")
                .city("Another City")
                .stateProvince("Another State")
                .country(country)
                .build();
        anotherLocation = repository.save(anotherLocation);

        assertNotNull(anotherLocation);
        assertEquals(repository.findById(anotherLocation.getId()).get(), anotherLocation);
    }

    @Test
    public void testSaveWithNewDataShouldReturnUpdatedLocation() {
        location.setPostalCode("3456789");
        location = repository.save(location);

        assertNotNull(location);
        assertEquals("3456789", repository.findById(location.getId()).get().getPostalCode());
    }

    @Test
    public void testDeleteShouldReturnNoLocation() {
        repository.deleteById(location.getId());

        assertTrue(repository.findById(location.getId()).isEmpty());
    }
}