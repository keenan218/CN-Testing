package com.qa.cne.service;

import com.qa.cne.persistence.domain.Badger;
import com.qa.cne.persistence.repo.BadgerRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CnTestingUnitTests {

    @Autowired
    private BadgerService service;

    @MockBean
    private BadgerRepo repo;

    //GIVEN - WHEN - THEN
    @Test
    public void testCreate() {
        // GIVEN
        Long id = 1L;
        Badger newBadger = new Badger("fred", 21, "trees");
        Badger savedBadger = new Badger("fred", 21, "trees");
        savedBadger.setId(id);

        // WHEN
        Mockito.when(this.repo.save(newBadger)).thenReturn(savedBadger);

        // THEN
        assertThat(this.service.createBadger(newBadger)).isEqualTo(savedBadger);

        Mockito.verify(this.repo, Mockito.times(1)).save(newBadger);
    }

    @Test
    public void testUpdate(){
        // GIVEN

        Long id = 1L;

        // will be passed in
        Badger newBadger = new Badger("velma", 21, "trees");
        // will be found by findById()
        Badger oldBadger = new Badger("fred", 21, "trees");
        oldBadger.setId(id);
        // will be saved back to db and returned by method
        Badger updatedBadger = new Badger("velma", 21, "trees");
        updatedBadger.setId(id);

        // WHEN
        Mockito.when(this.repo.findById(id)).thenReturn(Optional.of(oldBadger));
        Mockito.when(this.repo.save(updatedBadger)).thenReturn(updatedBadger);

        // THEN
		assertThat(this.service.updateBadger(newBadger, id)).isEqualTo(updatedBadger);

        Mockito.verify(this.repo, Mockito.times(1)).findById(id);
        Mockito.verify(this.repo, Mockito.times(1)).save(updatedBadger);
    }
    @Test
	void testGet() {
        // GIVEN
        Badger badger = new Badger("fred", 21, "trees");
        badger.setId(1L); // object to match the one in data.sql
        List<Badger> badgers = new ArrayList<>();
        badgers.add(badger);

        // WHEN
		Mockito.when(this.repo.findAll()).thenReturn(badgers);

		// THEN
		assertThat(this.service.getBadger()).isEqualTo(badgers);

		Mockito.verify(this.repo, Mockito.times(1)).findAll();
    }
    @Test
	void testDelete() {
		// GIVEN
		Long id = 1L;
		boolean found = false;

		// WHEN
		Mockito.when(this.repo.existsById(id)).thenReturn(found);

		// THEN

		assertThat(this.service.deleteBadger(id)).isEqualTo(!found);

		Mockito.verify(this.repo, Mockito.times(1)).existsById(id);
	}
}
