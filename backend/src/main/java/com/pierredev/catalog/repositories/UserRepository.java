package com.pierredev.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pierredev.catalog.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
