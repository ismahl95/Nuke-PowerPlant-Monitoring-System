package com.ihl95.nuclear.nuclearPlant.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ihl95.nuclear.nuclearPlant.domain.NuclearPlant;

@Repository
public interface NuclearPlantRepository extends JpaRepository<NuclearPlant, Long> {

}
