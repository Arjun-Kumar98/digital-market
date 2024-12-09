package com.digitalmarket.service;

import java.util.Optional;
import java.util.stream.Collectors;

import com.digitalmarket.exception.*;
import com.digitalmarket.controller.UserController;
import com.digitalmarket.dto.*;
import java.util.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

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

	@Autowired
	private WishlistRepository wishlistRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private <T> T fetchEntityById(Optional<T> optionalEntity, String entityName) {
		return optionalEntity.orElseThrow(() -> new EntityNotFoundException(entityName + "not found"));
	}

	public String addCart(ShoppingcartEntity shoppingCart) {
		shoppingcartRepository.save(shoppingCart);
		return "cart is added successfully";
	}

	public String saveBasketDetails(BasketRequestDTO basketRequestDTO) {
		logger.info("The items added to the cart {} are {}",basketRequestDTO.getCartId(), basketRequestDTO.getProducts());
		for (BasketRequestDTO.ProductRequest productRequest : basketRequestDTO.getProducts()) {

			BasketEntity basketEntity = new BasketEntity();
			basketEntity.setCartId(basketRequestDTO.getCartId());
			basketEntity.setProductId(productRequest.getProductId());
			basketEntity.setQuantity(productRequest.getQuantity());
			basketRepository.save(basketEntity);

		}
		return "Shopping cart details are saved";
	}
	
	public String saveWishlistItems(WishlistRequestDTO wishlistRequestDTO) {
	

		for(WishlistRequestDTO.ProductName productName: wishlistRequestDTO.getProductList()) {
			logger.info("the name is === {}",wishlistRequestDTO.getUserId());
			WishlistEntity wish = new WishlistEntity();
			wish.setUserId(wishlistRequestDTO.getUserId());
			wish.setProduct(productName.getProductName());
			wishlistRepository.save(wish);
		}
		return "The wishlist details have been saved successfully";
	}
	
	public List<WishlistRequestDTO> viewWishlistItems(Integer userId) {
		List<WishlistEntity> wishList = wishlistRepository.findByUserIdUserId(userId);
		List<WishlistRequestDTO> wishDTOList = wishList.stream().collect(Collectors.groupingBy(WishlistEntity::getUserId,
				Collectors.mapping(entity->new WishlistRequestDTO.ProductName(entity.getProductName()),Collectors.toList()
				)))
				.entrySet().stream().map(entry-> new WishlistRequestDTO(entry.getKey(),entry.getValue())).collect(Collectors.toList());
		return wishDTOList;
	}
	

	@Transactional
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

	@Transactional
	public String deleteProduct(BasketRequestDTO basketRequest) {
		logger.info("The items removed from, the cart {} are {}",basketRequest.getCartId(), basketRequest.getProducts());
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

	public List<BasketRequestDTO> viewCartItems(Integer cartId) {
		List<BasketEntity> cartList = basketRepository.findByCartIdCartId(cartId);
		   List<BasketRequestDTO> cartDTOList = cartList.stream().collect(Collectors.groupingBy(BasketEntity::getCartId,
				   Collectors.mapping(entity->new BasketRequestDTO.ProductRequest(entity.getProductId(),entity.getQuantity()),
						   Collectors.toList())
		   ))
				   .entrySet().stream().map(entry->new BasketRequestDTO(entry.getKey(),entry.getValue())).collect(Collectors.toList());
		   
		return cartDTOList;
		
		
	}

}
