package com.digitalmarket.service;
import java.util.Optional;
import com.digitalmarket.exception.*;
import java.util.*;

import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// For dependency injection
import org.springframework.stereotype.Service; 
import com.digitalmarket.repository.*;
import com.digitalmarket.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Service
public class ProductService {
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
@Autowired
private ProductRepository productRepository;

private <T> T fetchEntityById(Optional<T> optionalEntity, String entityName) {
	return optionalEntity.orElseThrow(() -> new EntityNotFoundException(entityName + "not found"));
}

public String saveProduct(ProductEntity productEntity) {
	 productRepository.save(productEntity);
	 return "The product has been created successfully";
}

public String updateProduct(ProductEntity productEntity) {
	ProductEntity updateProduct = fetchEntityById(productRepository.findById(productEntity.getProductId()),"product");
	updateProduct.setProductName(productEntity.getProductName());
	updateProduct.setDescription(productEntity.getDesription());
	updateProduct.setStock(productEntity.getStock());
	updateProduct.setPrice(productEntity.getPrice());
	updateProduct.setCategory(productEntity.getCategory());
	productRepository.save(updateProduct);
	return "The product has been updated successfully";
}

public void deleteProduct(Integer productId) {
	if(productRepository.existsById(productId)) {
	productRepository.deleteById(productId);
	}else {
		throw new ProductNotFoundException("Product with Id "+productId+" is not found");
	}
}

public List<ProductEntity> findProductbyNameorCategory(String productName,String Category){
	List<ProductEntity> productList = productRepository.findByProductNameIgnoreCaseOrCategoryIgnoreCase(productName, Category);
	return productList;
}

public Page<ProductEntity> findbyPrice(Double startPrice,Double endPrice,Pageable pageable){
 return productRepository.findByPriceBetween(startPrice, endPrice,pageable);

}

public Page<ProductEntity> listProducts(Pageable pageable){
	return productRepository.findAll(pageable);
	
}

}
