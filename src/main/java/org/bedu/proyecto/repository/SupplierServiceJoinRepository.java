package org.bedu.proyecto.repository;

import java.util.List;

import org.bedu.proyecto.keys.SupplierServiceKey;
import org.bedu.proyecto.model.AppService;
import org.bedu.proyecto.model.SupplierServiceJoin;
import org.bedu.proyecto.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierServiceJoinRepository extends JpaRepository <SupplierServiceJoin,SupplierServiceKey>{
    @Query("SELECT i.service FROM SupplierServiceJoin i WHERE i.supplier.id = :supplierId")
    List<AppService> findServicesBySupplier(Long supplierId);
    @Query("SELECT i.supplier FROM SupplierServiceJoin i WHERE i.service.id = :serviceId")
    List<Supplier> findSuppliersByService(Long serviceId);
}
