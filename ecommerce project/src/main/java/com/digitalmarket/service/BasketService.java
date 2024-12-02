package com.digitalmarket.service;
import java.util.Optional;
import com.digitalmarket.exception.*;
import com.digitalmarket.dto.*;
import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import com.digitalmarket.repository.*;
import com.digitalmarket.model.*;
@Service
public class BasketService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ShoppingCartRepository shoppingcartRepository;
	
	
	@Autowired
	private BasketRepository basketRepository;
	
	public String addCart(ShoppingcartEntity shoppingCart) {
		shoppingcartRepository.save(shoppingCart);
		return "cart is added successfully";
	}
	
	
	
	public String saveBasketDetails(BasketRequestDTO basketRequestDTO)
	{
		for(BasketRequestDTO.ProductRequest productRequest: basketRequestDTO.getProducts()) {
			if(!productRepository.existsById(productRequest.getProductId())) {
				throw new ProductNotFoundException("Product with an Id "+ productRequest.getProductId()+" is not found");	
			}
			
			BasketEntity basketEntity = new BasketEntity();
			basketEntity.setCartId(basketRequestDTO.getCartId());
			basketEntity.setProductId(productRequest.getProductId());
			basketEntity.setQuantity(productRequest.getQuantity());
			basketRepository.save(basketEntity);
			
		}
		return "Shopping cart details are saved";
	}

	public String updateBasketDetails(BasketEntity basketRequest) {
	   BasketEntity newCart = basketRepository.getById(basketRequest.getcartProductMapId());
	   if(!productRepository.existsById(basketRequest.getProductId())) {
		   throw new ProductNotFoundException("Product with an Id "+ basketRequest.getProductId()+" is not found");	
	   }else {
	   newCart.setQuantity(basketRequest.getQuantity());
	   basketRepository.save(newCart);
	   }
	    return "The quantity for Product Id: "+ basketRequest.getProductId() + " has changed";
	}

	
	public String deleteProduct(Integer cartMapId)
	{ basketRepository.deleteById(cartMapId);
		return "The item has been deleted successfully";
	}
	
	public List<BasketEntity> viewCartItems(){
		List<BasketEntity> cartList = basketRepository.findAll();
		return cartList;
	}

}
