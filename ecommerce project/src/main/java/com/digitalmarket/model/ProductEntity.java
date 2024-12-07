package com.digitalmarket.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



@Entity
@Table(name="product_list")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="product_id")
private Integer productId;

@Column(name="product_name",nullable=false)
@JsonProperty("productName")
@NotNull(message="Product name cannot be null")
private String productName;

@Column(name="description")
@JsonProperty("description")
private String description;

@Column(name="price",nullable=false)
@JsonProperty("price")
@NotNull(message="price cannot be null")
@DecimalMin(value="10.00", message="price should be atleast 10.00")
private Double price;


@Column(name="stock",nullable=false)
@JsonProperty("stock")
@Min(value=1, message="stock should be atleast 1")
@NotNull(message="stock cannot be null")
private Integer stock;

@Column(name="category",nullable=false)
@JsonProperty("category")
private String category;

public Integer getProductId() {
	return productId;
}

public void setProductId(Integer productId) {
	this.productId = productId;
}
public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}

public String getDesription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}

public Double getPrice() {
	return price;
}
public void setPrice(Double price) {
	this.price = price;
}

public Integer getStock() {
	return stock;
}

public void setStock(Integer stock) {
	this.stock = stock;
}
public String getCategory() {
	return category;
}

public void setCategory(String category) {
	this.category = category;
}
}
