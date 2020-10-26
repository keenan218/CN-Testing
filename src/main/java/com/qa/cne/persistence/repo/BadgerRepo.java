package com.qa.cne.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.cne.persistence.domain.Badger;

@Repository
public interface BadgerRepo extends JpaRepository<Badger, Long> {

}
