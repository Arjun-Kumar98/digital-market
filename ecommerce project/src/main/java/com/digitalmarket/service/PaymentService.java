package com.digitalmarket.service;
import java.util.Random;
import java.util.Optional;
import java.util.stream.Collectors;

import com.digitalmarket.exception.*;
import com.digitalmarket.dto.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import com.digitalmarket.repository.*;
import com.digitalmarket.model.*;
import com.digitalmarket.service.*;
@Service
public class PaymentService {
	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
	  @Autowired
	    private OrderRepository orderRepository;
	  
	 @Autowired
	 private OrderService orderService;
	  
	public String processPayment(Integer orderId,String coupon) {
	
		OrderEntity order = orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order with "+ orderId+ " is not found"));
	    Double totalAmount = orderService.getTotalPayableAmount(orderId);
	    String substr = coupon.substring(4);
	    int discount = Integer.parseInt(substr);
	    double finalamount = totalAmount - (totalAmount *(discount/100));
		boolean payment = simulatePayment();
		if(payment) {
			order.setOrderStatus("paid");
			orderRepository.save(order);
			return "payment of "+finalamount+"is successfull";
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
