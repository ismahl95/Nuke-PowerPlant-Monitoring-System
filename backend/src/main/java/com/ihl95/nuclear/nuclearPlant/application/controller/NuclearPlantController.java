package com.ihl95.nuclear.nuclearPlant.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ihl95.nuclear.nuclearPlant.application.dto.NuclearPlantCompleteDTO;
import com.ihl95.nuclear.nuclearPlant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearPlant.application.service.NuclearPlantService;

@RestController
@RequestMapping("/api/nuclear-plants")
public class NuclearPlantController {

    @Autowired
    private NuclearPlantService nuclearPlantService;

    @GetMapping
    public ResponseEntity<List<NuclearPlantDTO>> getAllNuclearPlants() {
        List<NuclearPlantDTO> nuclearPlants = nuclearPlantService.getAllNuclearPlants();
        return ResponseEntity.ok(nuclearPlants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NuclearPlantDTO> getNuclearPlantById(@PathVariable Long id) {
        NuclearPlantDTO nuclearPlant = nuclearPlantService.getNuclearPlantById(id);
        if (nuclearPlant == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(nuclearPlant);
    }

    @GetMapping("/complete/{id}")
    public ResponseEntity<NuclearPlantCompleteDTO> getNuclearPlantCompleteById(@PathVariable Long id) {
        NuclearPlantCompleteDTO nuclearPlantComplete = nuclearPlantService.getNuclearPlantCompleteById(id);
        return ResponseEntity.ok(nuclearPlantComplete);
    }

    @PostMapping
    public ResponseEntity<NuclearPlantDTO> createNuclearPlant(@RequestBody NuclearPlantDTO nuclearPlantDTO) {
        NuclearPlantDTO createdNuclearPlant = nuclearPlantService.createNuclearPlant(nuclearPlantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNuclearPlant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NuclearPlantDTO> updateNuclearPlant(
            @PathVariable Long id,
            @RequestBody NuclearPlantDTO nuclearPlantDTO) {
        NuclearPlantDTO updatedNuclearPlant = nuclearPlantService.updateNuclearPlant(id, nuclearPlantDTO);
        return ResponseEntity.ok(updatedNuclearPlant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNuclearPlant(@PathVariable Long id) {
        nuclearPlantService.deleteNuclearPlant(id);
        return ResponseEntity.noContent().build();
    }
}
