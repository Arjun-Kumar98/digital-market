package com.digitalmarket.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "wishlist",uniqueConstraints = @UniqueConstraint(columnNames= {"user_name","product_name"}))
public class WishlistEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wish_id")
	@JsonProperty("wishId")
	private Integer wishId;

	@ManyToOne
	@JoinColumn(name ="user_name")
	@JsonProperty("userName")
	@NotNull(message = "username cannot be null")
	private UserEntity userName;

	@Column(name = "product_name")
	@JsonProperty("productName")
	private String productName;

	public Integer getWishId() {
		return wishId;
	}

	public void setWishId(Integer wishId) {
		this.wishId = wishId;
	}

	public String getUserName() {
		return userName != null ? userName.getUsername() : null;
	}

	public void setUserName(String userName) {
		if (userName != null) {
			UserEntity user = new UserEntity();
			user.setUsername(userName);
			this.userName = user;
		} else {
			this.userName = null;
		}
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
