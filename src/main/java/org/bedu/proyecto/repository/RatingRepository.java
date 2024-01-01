package org.bedu.proyecto.repository;
import java.math.BigDecimal;
import java.util.List;

import org.bedu.proyecto.keys.SupplierServiceKey;
import org.bedu.proyecto.model.Rating;
import org.bedu.proyecto.model.SupplierServiceJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long>{
    List<Rating> findAllBySupplierServiceJoin(SupplierServiceJoin supplierServiceJoin);
    List<Rating> findByIdSupplierServiceKey(SupplierServiceKey supplierServiceKey);
    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.id.supplierServiceKey = :supplierServiceKey")
    BigDecimal calculateAverageRatingBySupplierServiceKey(@Param("supplierServiceKey") SupplierServiceKey supplierServiceKey);
}
