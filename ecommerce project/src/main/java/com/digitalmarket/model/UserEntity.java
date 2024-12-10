package com.digitalmarket.model;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="user_list",uniqueConstraints = @UniqueConstraint(columnNames= {"user_name"}),
indexes=@Index(name = "idx_email", columnList = "email_id"))
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserEntity {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="user_id")
private Integer userId;

@Column(name="user_name",nullable=false)
@NotNull(message="username cannot be null")
@JsonProperty("userName")
private String userName;

@JsonIgnore
@Column(name="password",nullable=false)
@NotNull(message="password cannot be null")
@JsonProperty("password")
private String password;

@Column(name="email_id",nullable=false)
@NotNull(message="emailid cannot be null")
@Pattern(regexp="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message="Invalid email format")
@JsonProperty("emailId")
private String emailId;

@Column(name="address",nullable=false)
@NotNull(message="address cannot be null")
@JsonProperty("address")
private String address;

@Column(name="role_id",nullable=false)
@NotNull(message="role Id cannot be null")
@JsonProperty("roleId")
private Integer roleId;

public Integer getUserId()
{
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}

public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmailId() {
	return emailId;
}
public void setEmailId(String emailId) {
	this.emailId = emailId;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public Integer getRoleId() {
	return roleId;
}
public void setRoleId(Integer roleId) {
	this.roleId = roleId;
}

}
