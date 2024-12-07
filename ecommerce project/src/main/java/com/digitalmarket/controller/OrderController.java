package com.digitalmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;
import com.digitalmarket.model.*;
import com.digitalmarket.dto.*;
import com.digitalmarket.service.*;
import org.slf4j.Logger;
import com.digitalmarket.exception.*;
import com.digitalmarket.jwt.*;
import org.slf4j.LoggerFactory;
import javax.validation.*;
import java.util.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/order")
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
@Autowired
private OrderService orderService;

@Autowired
private PaymentService paymentService;

@Validated
@PostMapping("/placeOrder")
public ResponseEntity<String> placeOrder(@RequestBody OrderEntity orderEntity){
	String responsemsg = orderService.addOrder(orderEntity);
	return ResponseEntity.ok(responsemsg);
}

@DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteProductDetails(@PathVariable("id") Integer orderId) {
	try {
        orderService.cancelOrder(orderId);
		return ResponseEntity.ok("Order cancelled successfully");
	} catch (OrderNotFoundException e) {
		return ResponseEntity.status(404).body("Order is not present");
	
}
}
@GetMapping("/payment/{id}")
public ResponseEntity<String> processOrderPayment(@PathVariable("id") Integer orderId,@RequestParam(required=true) String coupon){
	String responsemsg = paymentService.processPayment(orderId,coupon);
	return ResponseEntity.ok(responsemsg);
}

@GetMapping("/viewOrders/{id}")
public ResponseEntity<List<BasketRequestDTO>> viewOrderList(@PathVariable("id") Integer userId){
	List<BasketRequestDTO> cartDTOList = orderService.viewOrderHistory(userId);
	return ResponseEntity.ok(cartDTOList);
}

@GetMapping("/viewAllOrders")
public ResponseEntity<Page<BasketEntity>> viewAllOrders(@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="0")int size){
	Pageable pageable = PageRequest.of(page, size);
return	ResponseEntity.ok(orderService.viewAllOrderHistory(pageable));
}
	




@PutMapping("/updateOrderStatus/{id}")
public ResponseEntity<String> updateOrderStatus(@PathVariable("id") Integer orderId,
		@RequestParam(required = true) Integer status) {
	String responseMsg = orderService.updateOrderStatus(orderId, status);
	return ResponseEntity.ok(responseMsg);

}

}

