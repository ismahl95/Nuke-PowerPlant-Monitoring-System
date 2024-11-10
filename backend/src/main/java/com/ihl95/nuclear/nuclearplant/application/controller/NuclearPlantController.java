package com.ihl95.nuclear.nuclearplant.application.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.application.service.NuclearPlantService;
import com.ihl95.nuclear.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/nuclear-plants")
@Tag(name = "Nuclear Plants", description = "Gestión de plantas nucleares")
public class NuclearPlantController {

        private NuclearPlantService nuclearPlantService;

        public NuclearPlantController(NuclearPlantService nuclearPlantService) {
                this.nuclearPlantService = nuclearPlantService;
        }

        /**
         * This Java function retrieves all registered nuclear plants and returns them
         * as a list of
         * NuclearPlantDTO objects.
         * 
         * @return A ResponseEntity containing a list of NuclearPlantDTO objects
         *         representing all nuclear
         *         plants.
         */
        @Operation(summary = "Obtener todas las plantas nucleares", description = "Devuelve una lista de todas las plantas nucleares registradas")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Listado de plantas nucleares obtenido con éxito", content = @Content(array = @ArraySchema(schema = @Schema(implementation = NuclearPlantDTO.class)))),
                        @ApiResponse(responseCode = "403", description = "No tiene permisos para acceder a este recurso")
        })
        @GetMapping
        public ResponseEntity<List<NuclearPlantDTO>> getAllNuclearPlants() {
                List<NuclearPlantDTO> nuclearPlants = nuclearPlantService.getAllNuclearPlants();
                return ResponseEntity.ok(nuclearPlants);
        }

        /**
         * This Java function retrieves a nuclear plant by its ID and returns it as a
         * ResponseEntity.
         * 
         * @param id The `id` parameter in the code snippet represents the unique
         *           identifier of the nuclear
         *           plant that is being requested. This ID is used to retrieve the
         *           specific nuclear plant from the
         *           database or storage. The `@PathVariable` annotation in Spring
         *           indicates that the value for this
         *           parameter is extracted from the URI path of
         * @return A ResponseEntity containing a NuclearPlantDTO object is being
         *         returned. If the nuclearPlant
         *         object is null, a response with status code 404 (NOT_FOUND) is
         *         returned. Otherwise, a response with
         *         status code 200 (OK) is returned with the NuclearPlantDTO object.
         */
        @Operation(summary = "Obtener planta nuclear por ID", description = "Devuelve una planta nuclear según su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Planta nuclear obtenida con éxito", content = @Content(schema = @Schema(implementation = NuclearPlantDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Planta nuclear no encontrada"),
                        @ApiResponse(responseCode = "403", description = "No tiene permisos para acceder a este recurso")
        })
        @GetMapping("/{id}")
        public ResponseEntity<NuclearPlantDTO> getNuclearPlantById(@PathVariable Long id) {
                NuclearPlantDTO nuclearPlant = nuclearPlantService.getNuclearPlantById(id);
                return ResponseEntity.ok(nuclearPlant);
        }

        /**
         * This Java function creates a new nuclear plant and returns the created plant
         * as a response.
         * 
         * @param nuclearPlantDTO The `nuclearPlantDTO` parameter in the
         *                        `createNuclearPlant` method is of type
         *                        `NuclearPlantDTO`. It is a data transfer object that
         *                        contains the information needed to create a new
         *                        nuclear plant. This parameter is received in the
         *                        request body when a POST request is made to create
         * @return The method `createNuclearPlant` is returning a `ResponseEntity`
         *         object containing a
         *         `NuclearPlantDTO` object with the HTTP status code 201 (Created) and
         *         the newly created nuclear plant
         *         data.
         */
        @Operation(summary = "Crear nueva planta nuclear", description = "Crea una nueva planta nuclear")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Planta nuclear creada con éxito", content = @Content(schema = @Schema(implementation = NuclearPlantDTO.class))),
                        @ApiResponse(responseCode = "403", description = "No tiene permisos para acceder a este recurso")
        })
        @PostMapping
        public ResponseEntity<Object> createNuclearPlant(@Valid @RequestBody NuclearPlantDTO nuclearPlantDTO,
                        BindingResult result) {
                ResponseEntity<Object> errorResponse = Utils.validateFields(result);
                return errorResponse != null
                                ? errorResponse
                                : ResponseEntity.status(HttpStatus.CREATED)
                                                .body(nuclearPlantService.createNuclearPlant(nuclearPlantDTO));
        }

        /**
         * This function updates an existing nuclear plant based on its ID.
         * 
         * @param id              The `id` parameter in the `updateNuclearPlant` method
         *                        is a path variable of type `Long`.
         *                        It represents the ID of the nuclear plant that you
         *                        want to update. This ID is used to identify the
         *                        specific plant that needs to be updated in the system.
         * @param nuclearPlantDTO The `nuclearPlantDTO` parameter in the
         *                        `updateNuclearPlant` method is of type
         *                        `NuclearPlantDTO`. It is the request body that
         *                        contains the data needed to update an existing
         *                        nuclear plant. When a PUT request is made to update a
         *                        nuclear plant, the client should provide a
         * @return The method `updateNuclearPlant` is returning a `ResponseEntity`
         *         object containing a
         *         `NuclearPlantDTO` object that represents the updated nuclear plant.
         *         The HTTP status of the response
         *         will be 200 (OK) if the update is successful.
         */
        @Operation(summary = "Actualizar planta nuclear", description = "Actualiza una planta nuclear existente según su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Planta nuclear actualizada con éxito", content = @Content(schema = @Schema(implementation = NuclearPlantDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Planta nuclear no encontrada"),
                        @ApiResponse(responseCode = "403", description = "No tiene permisos para acceder a este recurso")
        })
        @PutMapping("/{id}")
        public ResponseEntity<NuclearPlantDTO> updateNuclearPlant(
                        @PathVariable Long id,
                        @RequestBody NuclearPlantDTO nuclearPlantDTO) {
                NuclearPlantDTO updatedNuclearPlant = nuclearPlantService.updateNuclearPlant(id, nuclearPlantDTO);
                return ResponseEntity.ok(updatedNuclearPlant);
        }

        /**
         * This Java function deletes a nuclear plant based on its ID.
         * 
         * @param id The `id` parameter in the `deleteNuclearPlant` method represents
         *           the unique identifier of
         *           the nuclear plant that is to be deleted. This ID is extracted from
         *           the path of the URL when a DELETE
         *           request is made to the endpoint `/{id}`. The method then calls the
         *           `deleteN
         * @return The `deleteNuclearPlant` method is returning a `ResponseEntity<Void>`
         *         object. This method is
         *         used to delete a nuclear plant based on its ID. After deleting the
         *         plant using the
         *         `nuclearPlantService.deleteNuclearPlant(id)` method, the method
         *         returns a response entity with a
         *         status of 204 (indicating success with no content) using
         *         `ResponseEntity.noContent().build()
         */
        @Operation(summary = "Eliminar planta nuclear", description = "Elimina una planta nuclear según su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Planta nuclear eliminada con éxito"),
                        @ApiResponse(responseCode = "403", description = "No tiene permisos para acceder a este recurso")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteNuclearPlant(@PathVariable Long id) {
                nuclearPlantService.deleteNuclearPlant(id);
                return ResponseEntity.noContent().build();
        }
}
