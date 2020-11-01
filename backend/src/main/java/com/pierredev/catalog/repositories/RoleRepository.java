package com.pierredev.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pierredev.catalog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
