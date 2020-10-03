package com.pierredev.catalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pierredev.catalog.dto.ProductDTO;
import com.pierredev.catalog.entities.Product;
import com.pierredev.catalog.repositories.ProductRepository;
import com.pierredev.catalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

@Transactional(readOnly = true)
public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
	Page <Product> list = productRepository.findAll(pageRequest);
	
	return list.map(x-> new ProductDTO(x));
}

@Transactional(readOnly = true)
public ProductDTO findById(Long id) {
	Optional<Product> obj = productRepository.findById(id);
	Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
	
	return new ProductDTO(entity, entity.getCategories());
}

}
