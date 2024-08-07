package com.ihl95.nuclear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ihl95.nuclear.model.NuclearPlant;

@Repository
public interface NuclearPlantRepository extends JpaRepository<NuclearPlant, Long> {

}
