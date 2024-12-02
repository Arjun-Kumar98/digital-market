package com.digitalmarket.service;
import java.util.Optional;
import java.util.stream.Collectors;

import com.digitalmarket.exception.*;
import com.digitalmarket.dto.*;
import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import com.digitalmarket.repository.*;
import com.digitalmarket.model.*;
@Service

public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private BasketRepository basketRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	private <T> T fetchEntityById(Optional<T> optionalEntity, String entityName) {
		return optionalEntity.orElseThrow(() -> new EntityNotFoundException(entityName + "not found"));
	}
	

		
		
		public String addOrder(OrderEntity orderEntity) {
		    Integer cartId = orderEntity.getCartId();
		    List<BasketEntity> cartItems = basketRepository.findByCartIdCartId(cartId);

		    for (BasketEntity basketItem : cartItems) {
		        Integer productId = basketItem.getProductId();
		        ProductEntity product = fetchEntityById(productRepository.findById(productId), "Product"); // Checking if there is sufficient stock
		        
		        if (product.getStock() < basketItem.getQuantity()) {
		          
		            return "There are only " + product.getStock() + " items available for " + product.getProductName();
		        } else {
		        
		            product.setStock(product.getStock() - basketItem.getQuantity());
		            productRepository.save(product);
		        }
		    }

		    orderRepository.save(orderEntity);
		    return "The order has been placed";
		}

	
	
	public String cancelOrder(Integer orderId) {
		
		if(!orderRepository.existsById(orderId)) {
			throw new OrderNotFoundException("Order with "+ orderId +" is not found");
		}
		OrderEntity order = fetchEntityById(orderRepository.findById(orderId),"Order");
		Integer cartId = order.getCartId();
		
		List<BasketEntity> cartItems = basketRepository.findByCartIdCartId(cartId);
		
		for(BasketEntity basketItem: cartItems) {
			Integer productId = basketItem.getProductId();
			ProductEntity product = fetchEntityById(productRepository.findById(productId),"Product");
			
				product.setStock(product.getStock() + basketItem.getQuantity());               //updating the stock values
				productRepository.save(product);
			
		}
        orderRepository.deleteById(orderId);
		return "The order has been cancelled";
	}
	
	public List <BasketEntity> viewOrderHistory(Integer userId) {
		OrderEntity orderList = orderRepository.getByUserIdUserId(userId);
		Integer cartId = orderList.getCartId();
		return basketRepository.findByCartIdCartId(cartId);
		
	}
}
