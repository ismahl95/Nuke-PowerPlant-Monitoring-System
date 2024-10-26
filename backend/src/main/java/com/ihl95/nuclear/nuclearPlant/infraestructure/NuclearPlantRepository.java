package com.ihl95.nuclear.nuclearplant.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;

@Repository
public interface NuclearPlantRepository extends JpaRepository<NuclearPlant, Long> {

}
