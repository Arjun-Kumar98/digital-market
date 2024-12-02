package com.digitalmarket.model;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.digitalmarket.model.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="shoppingcart_product_mapping")
public class BasketEntity {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@JsonProperty("cartProductMapId")
@Column(name="cart_product_map_id")
private Integer cartProductMapId;

@ManyToOne
@NotNull(message = "cart Id cannot be null")
@JoinColumn(name="cart_id")
@JsonProperty("cartId")
private ShoppingcartEntity cartId;
@ManyToOne
@NotNull(message="Product Id cannot be null")
@JsonProperty("productId")
@JoinColumn(name="product_id")
private ProductEntity productId;

@NotNull(message = "Quantity cannot be null")
@Min(value=1, message="quantity should be atleast 1")
@Column(name="quantity")
@JsonProperty("quantity")
private Integer quantity;

public Integer getcartProductMapId() {
	return cartProductMapId;
}
public void setcartProductMapId(Integer cartProductMapId) {
	this.cartProductMapId = cartProductMapId;
}
public Integer getProductId() {
	return productId!=null?productId.getProductId():null;
}
public void setProductId(Integer productId) {
	if(productId!=null) {
		ProductEntity productEntity = new ProductEntity();
		productEntity.setProductId(productId);
		this.productId = productEntity;
	}else {
		this.productId = null;
	}
}

public Integer getCartId() {
	return cartId!=null?cartId.getCartId():null;
}
public void setCartId(Integer cartId) {
	if(cartId!=null) {
		ShoppingcartEntity cartEntity = new ShoppingcartEntity();
		cartEntity.setCartId(cartId);
		this.cartId = cartEntity;
	}else {
		cartId = null;
	}
}

public Integer getQuantity() {
	return quantity;
}

public void setQuantity(Integer quantity) {
	this.quantity=quantity;
}
}
