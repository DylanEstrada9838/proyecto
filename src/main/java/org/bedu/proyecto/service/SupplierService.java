package org.bedu.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.bedu.proyecto.dto.supplier.CreateSupplierDTO;
import org.bedu.proyecto.dto.supplier.SupplierDTO;
import org.bedu.proyecto.dto.supplier.UpdateSupplierDTO;
import org.bedu.proyecto.exception.service.ServiceNotFoundException;
import org.bedu.proyecto.exception.supplier.ServiceAlreadyAssignedException;
import org.bedu.proyecto.exception.supplier.ServiceNotAssignedException;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.exception.supplier.SupplierUserAlreadyExist;
import org.bedu.proyecto.exception.user.UserNotFoundException;
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
        Supplier supplier = supplierOptional.orElseThrow(() -> new SupplierNotFoundException(supplierId));
        return mapper.toDTO(supplier);
    }

    public SupplierDTO save(CreateSupplierDTO data) throws UserNotFoundException, SupplierUserAlreadyExist {
        Optional<User> userOptional = userRepository.findById(data.getUserId());
        User user = userOptional.orElseThrow(() -> new UserNotFoundException(data.getUserId()));

        Optional<Supplier> supplierOptional = repository.findByUser(user);
        if (supplierOptional.isPresent()) {
            throw new SupplierUserAlreadyExist(data.getUserId());
        }
        Supplier entity = mapper.toModel(data);
        entity.setUser(user);
        repository.save(entity);

        return mapper.toDTO(entity);
    }

    public void update(long supplierId, UpdateSupplierDTO data) throws SupplierNotFoundException {
        Optional<Supplier> supplierOptional = repository.findById(supplierId);
        Supplier supplier = supplierOptional.orElseThrow(() -> new SupplierNotFoundException(supplierId));
        mapper.update(supplier, data);
        repository.save(supplier);
    }

    public void delete(long supplierId) throws SupplierNotFoundException {
        Optional<Supplier> supplierOptional = repository.findById(supplierId);
        Supplier supplier = supplierOptional.orElseThrow(() -> new SupplierNotFoundException(supplierId));
        repository.delete(supplier);
    }

    public void addService(long supplierId, long serviceId)
            throws SupplierNotFoundException, ServiceNotFoundException, ServiceAlreadyAssignedException {
        Optional<Supplier> supplierOptional = repository.findById(supplierId);
        Supplier supplier = supplierOptional.orElseThrow(() -> new SupplierNotFoundException(supplierId));

        Optional<AppService> serviceOptional = serviceRepository.findById(serviceId);
        AppService service = serviceOptional.orElseThrow(() -> new ServiceNotFoundException(serviceId));

        List<AppService> services = supplier.getServices();
        if (services.contains(service)) {
            throw new ServiceAlreadyAssignedException(serviceId);
        }
        services.add(service);
        repository.save(supplier);
    }

    public void removeService(long supplierId, long serviceId)
            throws SupplierNotFoundException, ServiceNotFoundException, ServiceNotAssignedException {
        Optional<Supplier> supplierOptional = repository.findById(supplierId);
        Supplier supplier = supplierOptional.orElseThrow(() -> new SupplierNotFoundException(supplierId));

        Optional<AppService> serviceOptional = serviceRepository.findById(serviceId);
        AppService service = serviceOptional.orElseThrow(() -> new ServiceNotFoundException(serviceId));

        List<AppService> services = supplier.getServices();
        if (!services.contains(service)) {
            throw new ServiceNotAssignedException(serviceId);
        }
        services.remove(service);
        repository.save(supplier);
    }

    public List<AppService> findAllBySupplier(long supplierId) throws SupplierNotFoundException {
        Optional<Supplier> supplierOptional = repository.findById(supplierId);
        Supplier supplier = supplierOptional.orElseThrow(() -> new SupplierNotFoundException(supplierId));

        return serviceRepository.findAllBySuppliers(supplier);
    }

    public List<SupplierDTO> findAllByService(long serviceId) throws ServiceNotFoundException {
        Optional<AppService> serviceOptional = serviceRepository.findById(serviceId);
        AppService service = serviceOptional.orElseThrow(() -> new ServiceNotFoundException(serviceId));

        return mapper.toDTOs(repository.findByServices(service));
    }

}
