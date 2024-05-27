package com.ourhome.chat.entity;

/**
 * Post ID를 가져오기 위한 객체
 */
public class PostEntity {
	
	private long userId;
	private long homeId;
	
	public PostEntity(long userId, long homeId) {
		this.userId = userId;
		this.homeId = homeId;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}


	public long getHomeId() {
		return homeId;
	}
	public void setHomeId(long homeId) {
		this.homeId = homeId;
	}

	@Override
	public String toString() {
		return "PostEntity [userId=" + userId + ", homeId=" + homeId + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
}
