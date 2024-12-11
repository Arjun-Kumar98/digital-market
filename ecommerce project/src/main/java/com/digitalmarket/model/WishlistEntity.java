package com.digitalmarket.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digitalmarket.controller.ShoppingController;
import com.digitalmarket.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "wishlist",uniqueConstraints = @UniqueConstraint(columnNames= {"user_id","product_name"}))
public class WishlistEntity {
	private static final Logger logger = LoggerFactory.getLogger(WishlistEntity.class);
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wish_id")
	@JsonProperty("wishId")
	private Integer wishId;

	@ManyToOne
	@NotNull(message="user Id cannot be null")
	@JoinColumn(name ="user_id",nullable=false)
	@JsonProperty("userId")
	private UserEntity userId;
   
	@NotNull(message="product name cannot be null")
	@Column(name = "product_name",nullable=false)
	@JsonProperty("productName")
	private String productName;

	public Integer getWishId() {
		return wishId;
	}

	public void setWishId(Integer wishId) {
		this.wishId = wishId;
	}

	public Integer getUserId() {
		return userId != null ? userId.getUserId() : null;
	}

	public void setUserId(Integer userId) {
		if (userId != null) {
			UserEntity user = new UserEntity();
			user.setUserId(userId);
			this.userId = user;
		} else {
			this.userId = null;
		}
	}

	public String getProductName() {
		return productName;
	}

	public void setProduct(String productName) {
		this.productName = productName;
	}

}
