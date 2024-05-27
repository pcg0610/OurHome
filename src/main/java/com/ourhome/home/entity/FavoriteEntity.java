package com.ourhome.home.entity;

public class FavoriteEntity {
	private long userId;
	private long homeId;
	
	public FavoriteEntity() {
		
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
	
	public FavoriteEntity(long userId, long homeId) {
		super();
		this.userId = userId;
		this.homeId = homeId;
	}
	
	

}
