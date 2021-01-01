package com.pierredev.catalog.test.factory;

import java.time.Instant;

import com.pierredev.catalog.dto.ProductDTO;
import com.pierredev.catalog.entities.Category;
import com.pierredev.catalog.entities.Product;

public class ProductFactory {
	
	public static Product createProduct() {
		Product product = new Product(1L, "phone", "Good phone", 800.0, "https://img.com/img.pmg", Instant.parse("2020-11-20T03:00:00z"));
		product.getCategories().add(new Category(1L, null));
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
	
	public static ProductDTO createProductDTO(Long id) {
		ProductDTO dto = createProductDTO();
		dto.setId(id);
		return dto;
	}

}
