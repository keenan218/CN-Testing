package com.qa.cne.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qa.cne.exception.BadgerNotFoundException;
import com.qa.cne.persistence.domain.Badger;
import com.qa.cne.persistence.repo.BadgerRepo;

@Service
public class BadgerService {

	private BadgerRepo repo;

	public BadgerService(BadgerRepo repo) {
		super();
		this.repo = repo;
	}

	public Badger createBadger(Badger badger) {
		return this.repo.save(badger);
	}

	public List<Badger> getBadger() {
		return this.repo.findAll();
	}

	public Badger updateBadger(com.qa.cne.persistence.domain.Badger badger, Long id) {
		// If doesn't find a matching Badger, throw exception
		Optional<Badger> optBadger = this.repo.findById(id);
		com.qa.cne.persistence.domain.Badger oldBadger = optBadger.orElseThrow(BadgerNotFoundException::new);

		oldBadger.setAge(badger.getAge());
		oldBadger.setHabitat(badger.getHabitat());
		oldBadger.setName(badger.getName());

		Badger saved = this.repo.save(oldBadger);
		return saved;
	}

	public boolean deleteBadger(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

}
