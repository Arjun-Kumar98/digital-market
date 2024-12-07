package com.digitalmarket.test;
import org.junit.jupiter.api.Test;                  // For JUnit test methods
import org.springframework.beans.factory.annotation.Autowired; // For dependency injection in tests
import org.springframework.boot.test.context.SpringBootTest;    // To bootstrap the entire application context
import org.springframework.boot.test.web.client.TestRestTemplate; // For testing REST APIs
import org.springframework.http.ResponseEntity;                 // For handling API responses

// Additional imports for assertions and utilities
import static org.junit.jupiter.api.Assertions.*;  // For assertions
import org.springframework.test.context.ActiveProfiles; // To set test-specific profiles
import org.springframework.test.web.servlet.MockMvc;    // For mocking HTTP requests
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; 
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;


// For MockMvc setup
import com.digitalmarket.repository.*;
import com.digitalmarket.service.*;
import com.digitalmarket.model.*;
import java.util.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderServiceIntegrationTest {

	public enum OrderStatus {
	    ACTIVE,
	    PAID,
	    SHIPPED,
	    DELIVERED;
	}
    @Autowired
    private OrderService orderService; // Service under test

    @MockBean
    private BasketRepository basketRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void testAddOrder_Success() {
        // Mock data setup
        Integer cartId = 1;
        BasketEntity basketItem = new BasketEntity();
        basketItem.setProductId(1);
        basketItem.setQuantity(2);

        ProductEntity product = new ProductEntity();
        product.setProductId(1);
        product.setProductName("Sample Product");
        product.setStock(5);

        when(basketRepository.findByCartIdCartId(cartId))
                .thenReturn(Collections.singletonList(basketItem));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCartId(cartId);
       orderEntity.setOrderStatus(OrderEntity.OrderStatus.ACTIVE);

        // Test method execution
        String result = orderService.addOrder(orderEntity);

        // Assertions
        assertEquals("The order has been placed", result);
        verify(productRepository).save(any(ProductEntity.class));
        verify(orderRepository).save(any(OrderEntity.class));
    }

    @Test
    public void testAddOrder_InsufficientStock() {
        // Mock data setup
        Integer cartId = 1;
        BasketEntity basketItem = new BasketEntity();
        basketItem.setProductId(1);
        basketItem.setQuantity(10);

        ProductEntity product = new ProductEntity();
        product.setProductId(1);
        product.setProductName("Sample Product");
        product.setStock(5);

        when(basketRepository.findByCartIdCartId(cartId))
                .thenReturn(Collections.singletonList(basketItem));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCartId(cartId);
        orderEntity.setOrderStatus(OrderEntity.OrderStatus.ACTIVE);

        // Test method execution
        String result = orderService.addOrder(orderEntity);

        // Assertions
        assertEquals("There are only 5 items available for Sample Product", result);
        verify(orderRepository, never()).save(any(OrderEntity.class));
    }
}
