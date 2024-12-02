package com.digitalmarket.model;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import com.digitalmarket.model.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="order_list")
public class OrderEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="order_id")
private Integer orderId;

@ManyToOne
@JoinColumn(name="cart_id")
@NotNull(message = "cart id cannot be null")
@JsonProperty("cartId")
private ShoppingcartEntity cartId;

@ManyToOne
@JoinColumn(name="user_id")
@NotNull(message= "user Id cannot be null")
@JsonProperty("userId")
private UserEntity userId;

@Column(name="order_status")
@NotNull(message = "order status cannot be null")
@JsonProperty("orderStatus")
private String orderStatus;

public Integer getOrderId() {
	return orderId;
}
public void setOrderId(Integer orderId) {
	this.orderId = orderId;
}
public Integer getCartId() {
	return cartId!=null?cartId.getCartId():null;
}
public void setCartId(Integer cartId) {
	if(cartId!=null) {
		ShoppingcartEntity cart = new ShoppingcartEntity();
		cart.setCartId(cartId);
		this.cartId = cart;
	}else {
		this.cartId=null;
	}
}
public Integer getUserId() {
	return userId!=null?userId.getUserId():null;
}
public void setUserId(Integer userId) {
	if(userId!=null) {
		UserEntity user = new UserEntity();
		user.setUserId(userId);
		this.userId = user;
	}else {
		this.userId=null;
	}
}
public String getOrderStatus() {
	return orderStatus;
}
public void setOrderStatus(String orderStatus) {
	this.orderStatus = orderStatus;
}
}
