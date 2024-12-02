package com.digitalmarket.model;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import com.digitalmarket.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="shoppingcart")
public class ShoppingcartEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="cart_id")
private Integer cartId;

@ManyToOne
@NotNull(message="user id cannot be null")
@JoinColumn(name="user_id")
@JsonProperty("userId")
private UserEntity userId;

public Integer getCartId() {
	return cartId;
}

public void setCartId(Integer cartId) {
	this.cartId = cartId;
}

public Integer getUserId() {
	return userId!=null?userId.getUserId():null;
}
public void setUserId(Integer userId) {
	if(userId!=null) {
	UserEntity newUser = new UserEntity();
	newUser.setUserId(userId);
	this.userId = newUser;
}else {
	this.userId=null;
}
}
}
