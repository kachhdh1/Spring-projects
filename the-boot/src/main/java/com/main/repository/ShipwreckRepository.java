package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.model.Shipwreck;

public interface ShipwreckRepository extends JpaRepository<Shipwreck, Long> {

}
