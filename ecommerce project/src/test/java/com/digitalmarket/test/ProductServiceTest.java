package com.digitalmarket.test;


import com.digitalmarket.model.ProductEntity;
import com.digitalmarket.repository.ProductRepository;
import com.digitalmarket.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.digitalmarket.exception.*;
import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    ProductServiceTest() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testSaveProduct() {
        // Arrange
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(1);
        productEntity.setProductName("Test Product");

        when(productRepository.save(productEntity)).thenReturn(productEntity);

        // Act
        String response = productService.saveProduct(productEntity);

        // Assert
        assertEquals("The product has been created successfully", response);
        verify(productRepository, times(1)).save(productEntity);
    }

    @Test
    void testUpdateProduct() {
        ProductEntity existingProduct = new ProductEntity();
        existingProduct.setProductId(1);
        existingProduct.setProductName("Old Name");

        ProductEntity updatedProduct = new ProductEntity();
        updatedProduct.setProductId(1);
        updatedProduct.setProductName("New Name");

        when(productRepository.findById(1)).thenReturn(Optional.of(existingProduct));

        // Act
        String response = productService.updateProduct(updatedProduct);

        // Assert
        assertEquals("The product has been updated successfully", response);
        verify(productRepository, times(1)).save(existingProduct);
        assertEquals("New Name", existingProduct.getProductName());
    }

    @Test
    void testUpdateProductThrowsException() {
        // Arrange
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(99);

        when(productRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                productService.updateProduct(productEntity));
        assertEquals("productnot found", exception.getMessage());
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        Integer productId = 1;
        when(productRepository.existsById(productId)).thenReturn(true);

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testDeleteProductThrowsException() {
        // Arrange
        Integer productId = 99;
        when(productRepository.existsById(productId)).thenReturn(false);

        // Act & Assert
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () ->
                productService.deleteProduct(productId));
        assertEquals("Product with Id 99 is not found", exception.getMessage());
    }

    @Test
    void testFindProductByNameOrCategory() {
        // Arrange
        String productName = "Test Product";
        String category = "Electronics";

        ProductEntity product1 = new ProductEntity();
        product1.setProductName(productName);
        ProductEntity product2 = new ProductEntity();
        product2.setCategory(category);

        when(productRepository.findByProductNameOrCategory(productName, category))
                .thenReturn(List.of(product1, product2));

        // Act
        List<ProductEntity> products = productService.findProductbyNameorCategory(productName, category);

        // Assert
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findByProductNameOrCategory(productName, category);
    }

    @Test
    void testFindByPrice() {
        // Arrange
        Double startPrice = 100.0;
        Double endPrice = 500.0;

        ProductEntity product1 = new ProductEntity();
        product1.setPrice(150.0);
        ProductEntity product2 = new ProductEntity();
        product2.setPrice(300.0);

        when(productRepository.findByPriceBetween(startPrice, endPrice))
                .thenReturn(List.of(product1, product2));

        // Act
        List<ProductEntity> products = productService.findbyPrice(startPrice, endPrice);

        // Assert
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findByPriceBetween(startPrice, endPrice);
    }
}
