package com.digitalmarket.dto;
import java.util.*;
import javax.validation.constraints.*;
import javax.validation.Valid;

public class WishlistRequestDTO {
@NotNull(message = "User Id cannot be null")
private Integer userId;
@NotEmpty(message="Product list cannot be empty")
@Valid
public WishlistRequestDTO(Integer userId,List<ProductName> productList) {
	this.userId= userId;
	this.productList = productList;
}
private List<ProductName> productList;
public static class ProductName{
	@NotNull(message="Product name cannot be null")
	private String productName;
	
	public ProductName(String productName) {
		this.productName = productName;
	}
	
	public void setProduct(String productName) {
		this.productName = productName;
	}
	
	public String getProductName() {
		return productName;
	}
}

public Integer getUserId() {
	return userId;
}

public void setUserId(Integer userId) {
	this.userId = userId;
}

public List<ProductName> getProductList(){
	return productList;
}

public void setProductList(List<ProductName> productList) {
	this.productList = productList;
}

}
