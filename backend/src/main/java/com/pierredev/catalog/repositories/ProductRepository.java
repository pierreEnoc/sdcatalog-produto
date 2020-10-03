package com.pierredev.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pierredev.catalog.entities.Product;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {

}
