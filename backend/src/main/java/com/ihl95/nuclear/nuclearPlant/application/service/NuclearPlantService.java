package com.ihl95.nuclear.nuclearPlant.application.service;

import java.util.List;

import com.ihl95.nuclear.nuclearPlant.application.dto.NuclearPlantDTO;

public interface NuclearPlantService {

  /**
   * This function returns a list of NuclearPlantDTO objects representing all nuclear plants.
   * 
   * @return A list of NuclearPlantDTO objects is being returned.
   */
  public List<NuclearPlantDTO> getAllNuclearPlants();

  /**
   * This function retrieves a NuclearPlantDTO object by its unique identifier.
   * 
   * @param id The parameter "id" is a unique identifier used to retrieve a specific NuclearPlantDTO
   * object from the system based on its ID.
   * @return A `NuclearPlantDTO` object corresponding to the nuclear plant with the specified `id` is
   * being returned.
   */
  public NuclearPlantDTO getNuclearPlantById(Long id);

  /**
   * This Java function creates a new NuclearPlantDTO object representing a nuclear plant.
   * 
   * @param nuclearPlantDTO The `createNuclearPlant` method takes a `NuclearPlantDTO` object as a
   * parameter. This object likely contains information about a nuclear plant, such as its name,
   * location, capacity, and other relevant details. The method is responsible for creating a new
   * nuclear plant based on the provided DTO
   * @return The method `createNuclearPlant` is returning a `NuclearPlantDTO` object.
   */
  public NuclearPlantDTO createNuclearPlant(NuclearPlantDTO nuclearPlantDTO);

  /**
   * This Java function updates a nuclear plant entity with the provided data in the NuclearPlantDTO
   * object.
   * 
   * @param id The `id` parameter is of type `Long` and represents the unique identifier of the nuclear
   * plant that you want to update.
   * @param nuclearPlantDTO The `updateNuclearPlant` method takes in two parameters:
   * @return The method signature indicates that a `NuclearPlantDTO` object will be returned after
   * updating the nuclear plant with the specified ID.
   */
  public NuclearPlantDTO updateNuclearPlant(Long id, NuclearPlantDTO nuclearPlantDTO);

  /**
   * The function `deleteNuclearPlant` in Java is used to delete a nuclear plant record based on its
   * unique identifier (id).
   * 
   * @param id The `deleteNuclearPlant` method takes a single parameter `id`, which is of type `Long`.
   * This `id` parameter is used to identify the specific nuclear plant that needs to be deleted.
   */
  public void deleteNuclearPlant(Long id);
  
}
