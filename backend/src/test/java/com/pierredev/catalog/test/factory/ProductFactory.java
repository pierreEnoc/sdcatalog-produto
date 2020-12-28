package com.pierredev.catalog.test.factory;

import java.time.Instant;

import com.pierredev.catalog.dto.ProductDTO;
import com.pierredev.catalog.entities.Product;

public class ProductFactory {
	
	public static Product createProduct() {
		return  new Product(1L, "phone", "Good phone", 800.0, "https://img.com/img.pmg", Instant.parse("2020-11-20T03:00:00z"));
	}
	
	public static ProductDTO createProductDTO() {
		return new ProductDTO(createProduct());
	}

}
