package com.digitalmarket.service;

import java.util.Optional;
import com.digitalmarket.exception.*;
import com.digitalmarket.controller.UserController;
import com.digitalmarket.dto.*;
import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.digitalmarket.repository.*;
import com.digitalmarket.model.*;

@Service
public class BasketService {
	private static final Logger logger = LoggerFactory.getLogger(BasketService.class);
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ShoppingCartRepository shoppingcartRepository;

	@Autowired
	private BasketRepository basketRepository;

	private <T> T fetchEntityById(Optional<T> optionalEntity, String entityName) {
		return optionalEntity.orElseThrow(() -> new EntityNotFoundException(entityName + "not found"));
	}

	public String addCart(ShoppingcartEntity shoppingCart) {
		shoppingcartRepository.save(shoppingCart);
		return "cart is added successfully";
	}

	public String saveBasketDetails(BasketRequestDTO basketRequestDTO) {
		for (BasketRequestDTO.ProductRequest productRequest : basketRequestDTO.getProducts()) {

			BasketEntity basketEntity = new BasketEntity();
			basketEntity.setCartId(basketRequestDTO.getCartId());
			basketEntity.setProductId(productRequest.getProductId());
			basketEntity.setQuantity(productRequest.getQuantity());
			basketRepository.save(basketEntity);

		}
		return "Shopping cart details are saved";
	}

	public String updateBasketDetails(BasketRequestDTO basketRequest) {
		for (BasketRequestDTO.ProductRequest productRequest : basketRequest.getProducts()) {
			if (!productRepository.existsById(productRequest.getProductId())) {
				throw new ProductNotFoundException(
						"Product with an Id " + productRequest.getProductId() + " is not found");
			}
			ShoppingcartEntity shoppingCart = fetchEntityById(
					shoppingcartRepository.findById(basketRequest.getCartId()), "cartId");
			ProductEntity product = fetchEntityById(productRepository.findById(productRequest.getProductId()),
					"products");
			BasketEntity newProduct = basketRepository.findCartProductMapIdByCartIdAndProductId(shoppingCart, product);
			newProduct.setQuantity(productRequest.getQuantity());
			basketRepository.save(newProduct);
		}

		return "The quantity of the items has changed";
	}

	public String deleteProduct(BasketRequestDTO basketRequest) {
		for (BasketRequestDTO.ProductRequest productRequest : basketRequest.getProducts()) {
			if (!productRepository.existsById(productRequest.getProductId())) {
				throw new ProductNotFoundException(
						"Product with an Id " + productRequest.getProductId() + " is not found");
			}
			ShoppingcartEntity shoppingCart = fetchEntityById(
					shoppingcartRepository.findById(basketRequest.getCartId()), "cartId");
			ProductEntity product = fetchEntityById(productRepository.findById(productRequest.getProductId()),
					"products");
			BasketEntity newProduct = basketRepository.findCartProductMapIdByCartIdAndProductId(shoppingCart, product);
			basketRepository.deleteById(newProduct.getcartProductMapId());
		}
		return "The items have been removed from the cart";
	}

	public List<BasketEntity> viewCartItems(Integer cartId) {
		List<BasketEntity> cartList = basketRepository.findByCartIdCartId(cartId);
		return cartList;
	}

}
