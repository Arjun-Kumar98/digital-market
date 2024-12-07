package com.digitalmarket.dto;
import java.util.*;
import javax.validation.constraints.*;
import javax.validation.Valid;
public class BasketRequestDTO {
@NotNull(message = "Cart ID cannot be null")
private Integer cartId;

@NotEmpty(message="product list cannot be empty")
@Valid
private List<ProductRequest> products;
public BasketRequestDTO(Integer cartId, List<ProductRequest> products) {
    this.cartId = cartId;
    this.products = products;
}
public static class ProductRequest{
	@NotNull(message = "Product ID cannot be null")
	private Integer productId;
	@Min( value=1,message = "Quantity must be greater than zero")
	@Valid
	private Integer quantity;
	
	public ProductRequest(Integer productId,Integer quantity) {
		this.quantity = quantity;
	}
	
	public Integer getProductId() {
		return productId;
	}
	
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
public Integer getCartId() {
	return cartId;
}
public List<ProductRequest> getProducts(){
	return products;
}
public void setProducts(List<ProductRequest> products) {
	this.products = products;
}
public void setCartId(Integer cartId) {
	this.cartId = cartId;
}



}
