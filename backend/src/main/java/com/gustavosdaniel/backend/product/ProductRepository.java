package com.gustavosdaniel.backend.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    boolean existsByName(String name);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Product> findByName(@Param("searchTerm") String searchTerm);

}
