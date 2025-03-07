package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;
	
	@BeforeEach
	void setup() {
		existingId = 1L;
		nonExistingId = 100L;
		countTotalProducts = 25;
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		repository.deleteById(existingId);
		
		Optional<Product> result = repository.findById(existingId);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void saveShouldSaveNewObjectWithAutoIncrementWhenIdIsNull() {
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts+1, product.getId());
	}
	
	@Test
	public void getByIdShouldReturnAnOptionalPresentWhenIdExists() {
		Optional<Product> product = repository.findById(existingId);
		Assertions.assertTrue(product.isPresent());
	}
	
	@Test
	public void getByIdShouldReturnAnOptionalNotPresentWhenIdDoesNotExist() {
		Optional<Product> product = repository.findById(nonExistingId);
		Assertions.assertFalse(product.isPresent());
	}	
}
