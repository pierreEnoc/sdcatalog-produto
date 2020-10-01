package com.pierredev.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pierredev.catalog.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>   {

}
