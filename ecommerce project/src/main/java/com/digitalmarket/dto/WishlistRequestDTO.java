package com.digitalmarket.dto;
import java.util.*;


public class WishlistRequestDTO {
private Integer userId;
private List<ProductName> productList;
public static class ProductName{
	private String productName;
	
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
