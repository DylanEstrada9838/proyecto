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
        Supplier supplier = Validation.supplierExist(repository, supplierId);
        return mapper.toDTO(supplier);
    }

    public SupplierDTO save(CreateSupplierDTO data) throws UserNotFoundException, SupplierUserAlreadyExist {
        User user = Validation.userExist(userRepository, data.getUserId());

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
        Supplier supplier = Validation.supplierExist(repository, supplierId);
        mapper.update(supplier, data);
        repository.save(supplier);
    }

    public void delete(long supplierId) throws SupplierNotFoundException {
        Supplier supplier = Validation.supplierExist(repository, supplierId);
        repository.delete(supplier);
    }

    public void addService(long supplierId, long serviceId)
            throws SupplierNotFoundException, ServiceNotFoundException, ServiceAlreadyAssignedException {
        Supplier supplier = Validation.supplierExist(repository, supplierId);
        AppService service = Validation.serviceExist(serviceRepository, serviceId);

        List<AppService> services = supplier.getServices();
        if (services.contains(service)) {
            throw new ServiceAlreadyAssignedException(serviceId);
        }
        services.add(service);
        repository.save(supplier);
    }

    public void removeService(long supplierId, long serviceId)
            throws SupplierNotFoundException, ServiceNotFoundException, ServiceNotAssignedException {
        Supplier supplier = Validation.supplierExist(repository, supplierId);
        AppService service = Validation.serviceExist(serviceRepository, serviceId);

        List<AppService> services = supplier.getServices();
        if (!services.contains(service)) {
            throw new ServiceNotAssignedException(serviceId);
        }
        services.remove(service);
        repository.save(supplier);
    }

    public List<AppService> findAllBySupplier(long supplierId) throws SupplierNotFoundException {
        Supplier supplier = Validation.supplierExist(repository, supplierId);
        return serviceRepository.findAllBySuppliers(supplier);
    }

    public List<SupplierDTO> findAllByService(long serviceId) throws ServiceNotFoundException {
        AppService service = Validation.serviceExist(serviceRepository, serviceId);
        return mapper.toDTOs(repository.findByServices(service));
    }

}
