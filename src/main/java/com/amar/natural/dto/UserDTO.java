package com.amar.natural.dto;

import javax.persistence.Entity;

public class UserDTO {

	private String fullName;
	private String username;
	private String emailId;
	private String password;
	private String confirmPassword;
	private String mobileNo;
	
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
		return "UserDTO [fullName=" + fullName + ", username=" + username + ", emailId=" + emailId + ", password="
				+ password + ", confirmPassword=" + confirmPassword + ", mobileNo=" + mobileNo + "]";
	}
}
