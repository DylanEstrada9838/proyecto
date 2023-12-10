package org.bedu.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.bedu.proyecto.dto.SupplierDTO;
import org.bedu.proyecto.dto.CreateSupplierDTO;
import org.bedu.proyecto.dto.UpdateSupplierDTO;
import org.bedu.proyecto.exception.ServiceNotAssignedException;
import org.bedu.proyecto.exception.ServiceNotFoundException;
import org.bedu.proyecto.exception.SupplierNotFoundException;
import org.bedu.proyecto.exception.UserNotFoundException;
import org.bedu.proyecto.mapper.SupplierMapper;
import org.bedu.proyecto.model.AppService;
import org.bedu.proyecto.model.Supplier;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.repository.ServiceRepository;
import org.bedu.proyecto.repository.SupplierRepository;
import org.bedu.proyecto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class SupplierService {
    @Autowired
    SupplierRepository repository;
    @Autowired
    SupplierMapper mapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ServiceRepository serviceRepository;

    public List<SupplierDTO> findAll() {
        return mapper.toDTOs(repository.findAll());
    }

    public SupplierDTO findById(Long supplierId) throws SupplierNotFoundException {
        Optional<Supplier> supplierOptional = repository.findById(supplierId);
        if (!supplierOptional.isPresent()) {
            throw new SupplierNotFoundException(supplierId);
        }
        return mapper.toDTO(supplierOptional.get());
    }

    public SupplierDTO save(CreateSupplierDTO data) throws UserNotFoundException{
        Optional<User> userOptional = userRepository.findById(data.getUserId());
        if(!userOptional.isPresent()){
            throw new UserNotFoundException(data.getUserId());
        }
        Supplier entity = mapper.toModel(data);
        entity.setUser(userOptional.get());
        repository.save(entity);

        return mapper.toDTO(entity);
    }

    public void update(long supplierId, UpdateSupplierDTO data) throws SupplierNotFoundException {
        Optional<Supplier> supplierOptional = repository.findById(supplierId);
        if (!supplierOptional.isPresent()) {
            throw new SupplierNotFoundException(supplierId);
        }
        Supplier supplier = supplierOptional.get();
        mapper.update(supplier, data);
        repository.save(supplier);
    }

    public void delete(long supplierId) throws SupplierNotFoundException {
        Optional<Supplier> supplierOptional = repository.findById(supplierId);
        if (!supplierOptional.isPresent()) {
            throw new SupplierNotFoundException(supplierId);
        }
        repository.delete(supplierOptional.get());
    }

    public void addService(long supplierId,long serviceId) throws SupplierNotFoundException,ServiceNotFoundException{
         Optional<Supplier> supplierOptional = repository.findById(supplierId);
        if (!supplierOptional.isPresent()) {
            throw new SupplierNotFoundException(supplierId);
        }
        Optional<AppService> serviceOptional = serviceRepository.findById(serviceId);
        if(!serviceOptional.isPresent()){
            throw new ServiceNotFoundException(serviceId);
        }
        Supplier supplier = supplierOptional.get();
        List <AppService> services = supplier.getServices();
        services.add(serviceOptional.get());

        repository.save(supplier);

    }
    public void removeService(long supplierId,long serviceId) throws SupplierNotFoundException,ServiceNotFoundException,ServiceNotAssignedException{
         Optional<Supplier> supplierOptional = repository.findById(supplierId);
        if (!supplierOptional.isPresent()) {
            throw new SupplierNotFoundException(supplierId);
        }
        Optional<AppService> serviceOptional = serviceRepository.findById(serviceId);
        if(!serviceOptional.isPresent()){
            throw new ServiceNotFoundException(serviceId);
        }
        Supplier supplier = supplierOptional.get();
        List <AppService> services = supplier.getServices();
        
        if (!services.contains(serviceOptional.get())){
            throw new ServiceNotAssignedException(serviceId);
        }
        services.remove(serviceOptional.get());
        repository.save(supplier);

    }
    
}
