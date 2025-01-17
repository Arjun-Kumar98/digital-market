package com.digitalmarket.service;
import java.util.stream.Collectors;

import com.digitalmarket.exception.*;
import com.digitalmarket.dto.*;
import org.springframework.data.domain.*;
import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import com.digitalmarket.repository.*;
import com.digitalmarket.model.*;
@Service

public class OrderService {
	public enum OrderStatus {
	    ACTIVE,
	    PAID,
	    SHIPPED,
	    DELIVERED;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private BasketRepository basketRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
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
               orderEntity.setOrderStatus(OrderEntity.OrderStatus.ACTIVE);
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
	
	public List <BasketRequestDTO> viewOrderHistory(Integer userId) {
        UserEntity user = fetchEntityById(userRepository.findById(userId),"user");
        
		OrderEntity orderList = orderRepository.getByUserIdUserId(userId);
		Integer cartId = orderList.getCartId();
		List<BasketEntity> cartList = basketRepository.findByCartIdCartId(cartId);
		   List<BasketRequestDTO> cartDTOList = cartList.stream().collect(Collectors.groupingBy(BasketEntity::getCartId,
				   Collectors.mapping(entity->new BasketRequestDTO.ProductRequest(entity.getProductId(),entity.getQuantity()),
						   Collectors.toList())
		   ))
				   .entrySet().stream().map(entry->new BasketRequestDTO(entry.getKey(),entry.getValue())).collect(Collectors.toList());
		   return cartDTOList;
        }
	
	
	public Page<BasketEntity> viewAllOrderHistory(Pageable pageable) {
        return basketRepository.findAll(pageable);
        }
	
	public String updateOrderStatus(Integer orderId,Integer status) {
		OrderEntity order = fetchEntityById(orderRepository.findById(orderId),"Order");
		if(status==1) {
		order.setOrderStatus(OrderEntity.OrderStatus.SHIPPED);
		}else{
			order.setOrderStatus(OrderEntity.OrderStatus.DELIVERED);
		}
		return "The status of order with orderId "+orderId+" has been updated";
		
	}
	
	public Double getTotalPayableAmount(Integer orderId) {
		if(!orderRepository.existsById(orderId)) {
			throw new OrderNotFoundException("Order with "+ orderId +" is not found");
		}
		OrderEntity order = fetchEntityById(orderRepository.findById(orderId),"Order");
		Integer cartId =order.getCartId();
		List<BasketEntity> cartItems = basketRepository.findByCartIdCartId(cartId);
		Double totalprice = 0.0;
		for(BasketEntity basketItem:cartItems) {
			Integer productId = basketItem.getProductId();
		  ProductEntity product = fetchEntityById(productRepository.findById(cartId),"Product");
		  totalprice+= product.getPrice()*basketItem.getQuantity();
		  
		}
		return totalprice;
	}
}
