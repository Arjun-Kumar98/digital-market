package com.digitalmarket.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.digitalmarket.dto.*;
import com.digitalmarket.model.*;
import com.digitalmarket.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;
import java.util.*;


@RestController
@RequestMapping("/shopping")
public class ShoppingController {
	private static final Logger logger = LoggerFactory.getLogger(ShoppingController.class);
@Autowired
private BasketService basketService;

@Validated
@PostMapping("/addCart")
public ResponseEntity<String> addCart(@RequestBody ShoppingcartEntity shoppingCart){
	String responsemsg = basketService.addCart(shoppingCart);
	return ResponseEntity.ok(responsemsg);
}

@Validated
@PostMapping("/addCartItems")
public ResponseEntity<String> updateCart(@RequestBody BasketRequestDTO basketRequest){
	String responsemsg = basketService.saveBasketDetails(basketRequest);
	return ResponseEntity.ok(responsemsg);
}

@Validated
@PostMapping("/modifyCartItems")
public ResponseEntity<String> modifyCartItems(@RequestBody BasketRequestDTO basketRequest){
	logger.info("the request is === {}",basketRequest);
	String responsemsg = basketService.updateBasketDetails(basketRequest);
	return ResponseEntity.ok(responsemsg);
}

@DeleteMapping("/products/{id}")
public ResponseEntity<String> deleteProducts(
        @PathVariable("id") Integer cartMapId) {
    String response = basketService.deleteProduct(cartMapId);
    return ResponseEntity.ok(response);
}

@GetMapping("/viewCart")
public ResponseEntity<List<BasketEntity>> viewProducts(){
	List<BasketEntity> cartList = basketService.viewCartItems();
	return ResponseEntity.ok(cartList);
}
}
