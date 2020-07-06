package com.example.hr.repository;

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
public class RegionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private RegionRepository repository;

    private Region region;

    @Before
    public void setup() {
        region = Region.builder().regionName("Some Region").build();
        region = entityManager.persist(region);
    }

    @Test
    public void testInjectedComponentsAreNotNull() {
        assertNotNull(entityManager);
        assertNotNull(repository);
    }

    @Test
    public void testFindByIdShouldReturnRegion() {
        assertEquals(repository.findById(region.getId()).get(), region);
    }

    @Test
    public void testFindByIdShouldReturnEmptyWhenRegionIsNotFound() {
        assertTrue(repository.findById(-1).isEmpty());
    }

    @Test
    public void testSaveShouldReturnSavedRegion() {
        Region anotherRegion = Region.builder().regionName("Another Region").build();
        anotherRegion = repository.save(anotherRegion);

        assertNotNull(anotherRegion);
        assertEquals(repository.findById(anotherRegion.getId()).get(), anotherRegion);
    }

    @Test
    public void testSaveWithNewDataShouldReturnUpdatedRegion() {
        region.setRegionName("Some Other Region");
        region = repository.save(region);

        assertNotNull(region);
        assertEquals("Some Other Region", repository.findById(region.getId()).get().getRegionName());
    }

    @Test
    public void testDeleteShouldReturnNoRegion() {
        repository.deleteById(region.getId());

        assertTrue(repository.findById(region.getId()).isEmpty());
    }
}