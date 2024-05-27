package com.ourhome.auth.entity;

/**
 * AccessToken, RefreshToken, MaxAge를 전달하는 클래스
 * 사용자의 생성된 토큰을 관리하기 위한 클래스
 */

public class AuthEntity {
	private User user;
	private String accessToken;
	private String refreshToken;
	private long maxAge;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public long getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(long maxAge) {
		this.maxAge = maxAge;
	}
}
