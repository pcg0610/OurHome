package com.ourhome.auth.entity;

import java.util.Date;

/**
 * 각 사용자의 Token 정보를 관리하기 위한 Entity
 */

/**
 * userId를 기반으로 token을 생성한다.
 */
public class TokenEntity {
	private long id;
	private String userId;
	private String token;
	private String hashedToken;
	private long issuedAt;
	private long expiration;
	
	public TokenEntity() {
		
	}
	
	public TokenEntity(long id, String userId, String token, long issuedAt, long expiration) {
		this.id = id;
		this.userId = userId;
		this.token = token;
		this.issuedAt = issuedAt;
		this.expiration = expiration;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public long getIssuedAt() {
		return issuedAt;
	}
	public void setIssuedAt(long issuedAt) {
		this.issuedAt = issuedAt;
	}
	
	public long getExpiration() {
		return expiration;
	}
	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}
	
	// DB 저장용
	public Date getExpirationDate() {
		return new Date(expiration);
	}
	
	public Date getIssuedAtDate() {
		return new Date(issuedAt);
	}
	
	public String getHashedToken() {
		return hashedToken;
	}

	public void setHashedToken(String hashedToken) {
		this.hashedToken = hashedToken;
	}

	@Override
	public String toString() {
		return "TokenEntity [id=" + id + ", userId=" + userId + ", token=" + token + ", hashedToken=" + hashedToken
				+ ", issuedAt=" + issuedAt + ", expiration=" + expiration + "]";
	}
}
