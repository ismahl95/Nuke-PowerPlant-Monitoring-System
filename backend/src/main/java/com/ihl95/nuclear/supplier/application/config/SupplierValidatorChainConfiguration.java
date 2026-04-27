package com.ihl95.nuclear.supplier.application.config;

import com.ihl95.nuclear.supplier.application.validator.SupplierValidator;
import com.ihl95.nuclear.supplier.application.validator.NameValidator;
import com.ihl95.nuclear.supplier.application.validator.ContactValidator;
import com.ihl95.nuclear.supplier.application.validator.PhoneValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuración Spring para el Chain of Responsibility Pattern.
 *
 * Construye la cadena de validadores en el siguiente orden:
 * 1. NameValidator - Valida que el nombre sea válido
 * 2. ContactValidator - Valida que el contacto (email) sea válido
 * 3. PhoneValidator - Valida que el teléfono sea válido
 *
 * Usar @Qualifier("supplierValidatorChain") para inyectar en el Service.
 */
@Configuration
public class SupplierValidatorChainConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SupplierValidatorChainConfiguration.class);

    /**
     * Construye la cadena de validadores.
     *
     * El orden es importante:
     * - Name validation primero (más rápido, validación básica)
     * - Contact validation segundo (validación de formato)
     * - Phone validation tercero (validación de formato)
     *
     * Esto asegura fail-fast en validaciones rápidas antes de validaciones complejas.
     */
    @Bean(name = "supplierValidatorChain")
    public SupplierValidator supplierValidatorChain(
            NameValidator nameValidator,
            ContactValidator contactValidator,
            PhoneValidator phoneValidator) {

        logger.info("Constructing SupplierValidator chain of responsibility");

        // Construir cadena: Name → Contact → Phone
        nameValidator.setNext(contactValidator);
        contactValidator.setNext(phoneValidator);

        logger.info("Validator chain constructed: NameValidator → ContactValidator → PhoneValidator");

        return nameValidator;  // El primero de la cadena es el entry point
    }
}


