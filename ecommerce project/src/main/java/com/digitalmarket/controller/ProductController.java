package com.digitalmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.digitalmarket.model.*;
import com.digitalmarket.service.*;
import com.digitalmarket.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;
import java.util.*;


@RestController
@RequestMapping("/product")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

@Autowired
ProductService productService;
	@Validated
	@PostMapping("/saveProduct")
	public ResponseEntity<String> saveProduct(@RequestBody ProductEntity productEntity){
		String message = productService.saveProduct(productEntity);
		return ResponseEntity.ok(message);
	}
	
	@Validated
	@PutMapping("/updateProduct")
	public ResponseEntity<String> updateProduct(@RequestBody ProductEntity productEntity){
		String message = productService.updateProduct(productEntity);
		return ResponseEntity.ok(message);
	}
	
	@Validated
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProductDetails(@PathVariable("id") Integer productId) {
		try {
	        productService.deleteProduct(productId);
			return ResponseEntity.ok("product deleted successfully");
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(404).body("product is not present");
		
}
	}
	
	@Validated
	@GetMapping("/priceRange")
	public ResponseEntity<List<ProductEntity>> findProductsBetweenPrice(@RequestParam(required = true)Double startPrice,@RequestParam(required = true)Double endPrice){
return ResponseEntity.ok(productService.findbyPrice(startPrice, endPrice));
		
	}
    
	@Validated
	@GetMapping("/findByNameOrCategory")
	public ResponseEntity<List<ProductEntity>> findByProductNameOrCategory(@RequestParam(required = false)String productName,@RequestParam(required = false) String categoryName){
		return ResponseEntity.ok(productService.findProductbyNameorCategory(productName, categoryName));
	}
}
