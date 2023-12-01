package org.bedu.proyecto.repository;

import java.util.List;

import org.bedu.proyecto.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
}
