package com.ihl95.nuclear.incident.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ihl95.nuclear.incident.domain.Incident;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

}

