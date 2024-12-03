package com.digitalmarket.dto;
import java.util.*;


public class WishlistRequestDTO {
private String userName;
private List<ProductName> productList;
public static class ProductName{
	String productName;
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProductName() {
		return productName;
	}
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public List<ProductName> getProducts(){
	return productList;
}

public void setProducts(List<ProductName> productList) {
	this.productList = productList;
}

}
