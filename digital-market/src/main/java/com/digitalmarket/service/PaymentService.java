package com.digitalmarket.service;
import java.util.Random;
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
public class PaymentService {

	  @Autowired
	    private OrderRepository orderRepository;
	  
	public String processPayment(Integer orderId) {
	
		OrderEntity order = orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order with "+ orderId+ " is not found"));
	
	
		boolean payment = simulatePayment();
		if(payment) {
			order.setOrderStatus("paid");
			orderRepository.save(order);
			return "payment is successfull";
		}else {
			order.setOrderStatus("payment pending");
			orderRepository.save(order);
			return "payment failure please try again";
		}
	}
	 private boolean simulatePayment() {

	        Random random = new Random();
	        return random.nextBoolean();
	    }

}
