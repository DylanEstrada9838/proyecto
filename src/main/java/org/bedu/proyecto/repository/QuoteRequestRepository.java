package org.bedu.proyecto.repository;
import java.util.List;
import org.bedu.proyecto.model.QuoteRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRequestRepository extends JpaRepository<QuoteRequest,Long>{
    List<QuoteRequest> findAll();
}
