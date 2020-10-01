package com.pierredev.catalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pierredev.catalog.entities.Category;
import com.pierredev.catalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository; 
	
	public List<Category> findAll() {
		return categoryRepository.findAll();
		
		
	}

}
