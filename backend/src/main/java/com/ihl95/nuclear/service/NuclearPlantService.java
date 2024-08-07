package com.ihl95.nuclear.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihl95.nuclear.model.NuclearPlant;
import com.ihl95.nuclear.repository.NuclearPlantRepository;

import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional
public class NuclearPlantService {

  @Autowired
  private NuclearPlantRepository nuclearPlantRepository;

  public List<NuclearPlant> getAllNuclearPlants(){
    return nuclearPlantRepository.findAll();
  }
    
}
