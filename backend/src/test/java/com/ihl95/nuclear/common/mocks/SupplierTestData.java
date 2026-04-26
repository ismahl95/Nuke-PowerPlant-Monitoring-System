package com.ihl95.nuclear.common.mocks;

import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import com.ihl95.nuclear.supplier.domain.Supplier;

/**
 * Datos de prueba compartidos para Supplier.
 * Reutilizable en tests unitarios, integración y E2E.
 */
public class SupplierTestData {

    // ── ENTIDADES ────────────────────────────────────

    public static Supplier createSupplierEntity(Long id, String name, String contact, String phone) {
        return Supplier.builder()
                .id(id)
                .name(name)
                .contact(contact)
                .phone(phone)
                .createdBy("system")
                .build();
    }

    public static Supplier createSupplierEntity() {
        return createSupplierEntity(1L, "Supplier A", "supplier@example.com", "+34912345678");
    }

    // ── DTOs ─────────────────────────────────────────

    public static SupplierDTO createSupplierDTO(Long id, String name, String contact, String phone) {
        return SupplierDTO.builder()
                .id(id)
                .name(name)
                .contact(contact)
                .phone(phone)
                .build();
    }

    public static SupplierDTO createSupplierDTO() {
        return createSupplierDTO(1L, "Supplier A", "supplier@example.com", "+34912345678");
    }

    // ── CONSTANTES ───────────────────────────────────

    public static final String VALID_SUPPLIER_NAME = "Proveedor de Combustible";
    public static final String VALID_SUPPLIER_CONTACT = "contact@supplier.com";
    public static final String VALID_SUPPLIER_PHONE = "+34912345678";

    public static final String INVALID_SUPPLIER_NAME = "";
    public static final String INVALID_SUPPLIER_EMAIL = "invalid-email";
}

