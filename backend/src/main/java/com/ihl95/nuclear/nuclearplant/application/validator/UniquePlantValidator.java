package com.ihl95.nuclear.nuclearplant.application.validator;

import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.infraestructure.NuclearPlantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Validador: Unicidad de nombre de planta nuclear.
 * 
 * Verifica que:
 * - No exista otra planta nuclear con el mismo nombre
 * - El nombre es único en el sistema
 * 
 * Nota: Este validador es stateful ya que requiere acceso al repositorio.
 * Se usa en contexto de creación de nuevas plantas.
 * 
 * @author GitHub Copilot
 */
@Component
public class UniquePlantValidator extends NuclearPlantValidator {
    
    private static final Logger logger = LoggerFactory.getLogger(UniquePlantValidator.class);
    
    private final NuclearPlantRepository nucleaPlantRepository;
    
    public UniquePlantValidator(NuclearPlantRepository nucleaPlantRepository) {
        this.nucleaPlantRepository = nucleaPlantRepository;
    }
    
    @Override
    protected ValidationResult doValidate(NuclearPlantDTO dto) {
        logger.debug("Validating plant name uniqueness: {}", dto.name());
        
        // Buscar por nombre (case-insensitive)
        boolean existsByName = nucleaPlantRepository.findAll()
            .stream()
            .anyMatch(plant -> plant.getName()
                .equalsIgnoreCase(dto.name().trim()));
        
        if (existsByName) {
            return ValidationResult.invalid(
                String.format("Plant with name '%s' already exists", dto.name())
            );
        }
        
        logger.debug("Plant name uniqueness validation passed");
        return ValidationResult.valid();
    }
}
