package com.ihl95.nuclear.supplier.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ihl95.nuclear.supplier.domain.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>{
  
}
