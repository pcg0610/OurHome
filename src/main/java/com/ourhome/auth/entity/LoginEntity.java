package com.ourhome.auth.entity;

/**
 * 로그인 정보를 받기 위한 Entity
 */
public class LoginEntity {
	private String userId;
	private String password;
	
	public LoginEntity() {
		
	}

	public LoginEntity(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
