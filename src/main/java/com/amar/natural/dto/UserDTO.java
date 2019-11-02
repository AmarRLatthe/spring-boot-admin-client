package com.amar.natural.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserDTO {

	@Id()
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	@Column
	private String fullName;
	@Column
	private String username;
	@Column
	private String emailId;
	@Column
	private String password;
	@Column
	private String confirmPassword;
	@Column
	private String mobileNo;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", fullName=" + fullName + ", username=" + username + ", emailId="
				+ emailId + ", password=" + password + ", confirmPassword=" + confirmPassword + ", mobileNo=" + mobileNo
				+ "]";
	}
	
}
