package org.bedu.proyecto.service;

import java.util.List;

import org.bedu.proyecto.dto.service.AddServiceDTO;
import org.bedu.proyecto.dto.supplier.CreateSupplierDTO;
import org.bedu.proyecto.dto.supplier.SupplierDTO;
import org.bedu.proyecto.dto.supplier.UpdateSupplierDTO;
import org.bedu.proyecto.dto.supplier_service.ChangeStatusDTO;
import org.bedu.proyecto.dto.supplier_service.ServicesBySupplierDTO;
import org.bedu.proyecto.dto.supplier_service.SuppliersByServicesDTO;
import org.bedu.proyecto.exception.service.ServiceNotFoundException;
import org.bedu.proyecto.exception.supplier.ServiceAlreadyAssignedException;
import org.bedu.proyecto.exception.supplier.ServiceNotAssignedException;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.exception.supplier.SupplierUserAlreadyExist;
import org.bedu.proyecto.exception.user.UserNotFoundException;
import org.bedu.proyecto.keys.SupplierServiceKey;
import org.bedu.proyecto.mapper.SupplierServiceJoinMapper;
import org.bedu.proyecto.mapper.SupplierMapper;
import org.bedu.proyecto.model.Supplier;
import org.bedu.proyecto.model.SupplierServiceJoin;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.repository.SupplierServiceJoinRepository;
import org.bedu.proyecto.repository.ServiceRepository;
import org.bedu.proyecto.repository.SupplierRepository;
import org.bedu.proyecto.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {
    
    private SupplierRepository repository;
    
    private SupplierMapper mapper;
    
    private UserRepository userRepository;
    
    private ServiceRepository serviceRepository;
    
    private SupplierServiceJoinRepository supplierServiceJoinRepository;
    
    private SupplierServiceJoinMapper supplierServiceJoinMapper;

    public SupplierService(SupplierRepository repository, SupplierMapper mapper, UserRepository userRepository,
            ServiceRepository serviceRepository, SupplierServiceJoinRepository supplierServiceJoinRepository,
            SupplierServiceJoinMapper supplierServiceJoinMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.supplierServiceJoinRepository = supplierServiceJoinRepository;
        this.supplierServiceJoinMapper = supplierServiceJoinMapper;
    }

    public List<SupplierDTO> findAll() {
        return mapper.toDTOs(repository.findAll());
    }

    public SupplierDTO findById(Long supplierId) throws SupplierNotFoundException {
        return mapper.toDTO(Validation.supplierExists(repository, supplierId));
    }

    public SupplierDTO save(CreateSupplierDTO data,long userId) throws UserNotFoundException, SupplierUserAlreadyExist {
        User user = Validation.userExists(userRepository, userId);
        if (repository.findByUser(user).isPresent()) {
            throw new SupplierUserAlreadyExist(userId);
        }
        
        Supplier entity = mapper.toModel(data);
        entity.setUser(user);
        repository.save(entity);
        return mapper.toDTO(entity);
    }

    public void update(long supplierId, UpdateSupplierDTO data) throws SupplierNotFoundException {
        Supplier supplier = Validation.supplierExists(repository, supplierId);
        mapper.update(supplier, data);
        repository.save(supplier);
    }

    public void delete(long supplierId) throws SupplierNotFoundException {
        repository.delete(Validation.supplierExists(repository, supplierId));
    }

    public void addService(long supplierId, AddServiceDTO data)
            throws SupplierNotFoundException, ServiceNotFoundException, ServiceAlreadyAssignedException {
        Validation.verifySupplierExists(repository, supplierId);
        Validation.verifyServiceExists(serviceRepository, data.getServiceId());
        if (supplierServiceJoinRepository
        .findById(new SupplierServiceKey(supplierId, data.getServiceId())).isPresent()) {
            throw new ServiceAlreadyAssignedException(data.getServiceId());
        }
        supplierServiceJoinRepository.save(supplierServiceJoinMapper.toModel(supplierId, data));
    }

    public void removeService(long supplierId, long serviceId)
            throws SupplierNotFoundException, ServiceNotFoundException, ServiceNotAssignedException {
        Validation.verifySupplierExists(repository, supplierId);
        Validation.verifyServiceExists(serviceRepository, serviceId);
        SupplierServiceJoin supplierServiceJoin = Validation.supplierServiceJoinExists(supplierServiceJoinRepository,
                new SupplierServiceKey(supplierId, serviceId));
        supplierServiceJoinRepository.delete(supplierServiceJoin);
    }

    public void changeServiceStatus(ChangeStatusDTO status, long supplierId, long serviceId)
            throws SupplierNotFoundException, ServiceNotFoundException, ServiceNotAssignedException {
        Validation.verifySupplierExists(repository, supplierId);
        Validation.verifyServiceExists(serviceRepository, serviceId);
        SupplierServiceJoin supplierServiceJoin = Validation.supplierServiceJoinExists(supplierServiceJoinRepository,
                new SupplierServiceKey(supplierId, serviceId));
        supplierServiceJoinMapper.update(supplierServiceJoin, status);
        supplierServiceJoinRepository.save(supplierServiceJoin);
    }

    public List<ServicesBySupplierDTO> findAllBySupplier(long supplierId) throws SupplierNotFoundException {
        Validation.verifySupplierExists(repository, supplierId);

        return supplierServiceJoinMapper
                .toServicesBySupplierDTO(supplierServiceJoinRepository.findServicesBySupplier(supplierId));
    }

    public List<SuppliersByServicesDTO> findAllByService(long serviceId) throws ServiceNotFoundException {
        Validation.verifyServiceExists(serviceRepository, serviceId);
        return supplierServiceJoinMapper
                .toSuppliersByServicesDTO(supplierServiceJoinRepository.findSuppliersByService(serviceId));
    }

}
