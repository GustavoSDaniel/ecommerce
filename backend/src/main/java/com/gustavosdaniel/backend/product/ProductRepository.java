package com.gustavosdaniel.backend.product;

import com.gustavosdaniel.backend.commun.ActiveOrInactive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    boolean existsByName(String name);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Product> searchByName(Pageable pageable, @Param("searchTerm") String searchTerm);

    Optional<Product> findByName(String name);

    @Query("SELECT p FROM Product p WHERE p.activeOrInactive = :status")
    Page<Product> findByActiveOrInactive(@Param("status") ActiveOrInactive status, Pageable pageable);

}
