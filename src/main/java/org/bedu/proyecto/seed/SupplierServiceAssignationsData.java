package org.bedu.proyecto.seed;

import java.util.List;

import org.bedu.proyecto.dto.service.AddServiceDTO;
import org.bedu.proyecto.mapper.SupplierServiceJoinMapper;
import org.bedu.proyecto.repository.SupplierServiceJoinRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

@Component
@Profile("!test")
@Order(3)
public class SupplierServiceAssignationsData implements CommandLineRunner {
    @Autowired
    SupplierServiceJoinRepository supplierServiceJoinRepository;
    @Autowired
    SupplierServiceJoinMapper supplierServiceJoinMapper;

    @Transactional
    @Override
    public void run(String... args) {
        List<Long> supplierIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
        List<Integer> yearsExperience = List.of(2, 3, 4, 4, 3, 5, 6, 3, 2, 5);
        List<Integer> yearsExperience2 = List.of(2, 3, 4, 4, 3, 5, 6, 3, 2, 5);

        List<Long> serviceIds = List.of(2L, 4L, 6L, 9L, 4L, 3L, 6L, 1L, 8L, 3L);
        List<Long> serviceIds2 = List.of(3L, 5L, 1L, 3L, 8L, 9L, 7L, 4L, 1L, 5L);

        int i = 0;
        for (Long supplierId : supplierIds) {
            AddServiceDTO data = AddServiceDTO.builder()
            .serviceId(serviceIds.get(i))
            .yearsExperience(yearsExperience.get(i)).build();
            supplierServiceJoinRepository.save(supplierServiceJoinMapper.toModel(supplierId, data));
            AddServiceDTO data2 = AddServiceDTO.builder()
            .serviceId(serviceIds2.get(i))
            .yearsExperience(yearsExperience2.get(i)).build();
            supplierServiceJoinRepository.save(supplierServiceJoinMapper.toModel(supplierId, data2));
            i++;
        }

    }

}
