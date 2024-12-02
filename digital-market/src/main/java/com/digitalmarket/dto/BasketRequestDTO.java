package com.digitalmarket.dto;
import java.util.*;
public class BasketRequestDTO {
private Integer cartId;
private List<ProductRequest> products;

public static class ProductRequest{
	private Integer productId;
	private Integer quantity;
	
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
