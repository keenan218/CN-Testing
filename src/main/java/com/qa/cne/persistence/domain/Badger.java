package com.qa.cne.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Badger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private int age;

	private String habitat;

	public Badger(Long id, String name, int age, String habitat) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.habitat = habitat;
	}

	public Badger(String name, int age, String habitat) {
		super();
		this.name = name;
		this.age = age;
		this.habitat = habitat;
	}

	public Badger() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getHabitat() {
		return habitat;
	}

	public void setHabitat(String habitat) {
		this.habitat = habitat;
	}

}
