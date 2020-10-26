package com.qa.cne.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qa.cne.persistence.domain.Badger;
import com.qa.cne.service.BadgerService;

@RestController
@CrossOrigin // disables CORS shenanigans
public class BadgerController {

	private BadgerService service;

	public BadgerController(BadgerService service) {
		super();
		this.service = service;
	}

	@GetMapping("/greeting")
	public String greeting() {
		return "Hello, World!";
	}

	@PostMapping("/create")
	public ResponseEntity<Badger> createBadger(@RequestBody Badger badger) {
		return new ResponseEntity<Badger>(this.service.createBadger(badger), HttpStatus.CREATED);
	}

	@GetMapping("/get")
	public ResponseEntity<List<Badger>> getBadger() {
		return ResponseEntity.ok(this.service.getBadger());
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Badger> updateBadger(@RequestBody Badger badger, @PathVariable Long id) {
		return new ResponseEntity<Badger>(this.service.updateBadger(badger, id), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<Object> deleteBadger(@PathVariable Long id) {
		if (this.service.deleteBadger(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
