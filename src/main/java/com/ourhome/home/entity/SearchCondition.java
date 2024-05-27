package com.ourhome.home.entity;

public class SearchCondition {
	
	private String address;
	
	private String name;
	
	private MatchType matchType = MatchType.AND;
	
	private long userId;
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	private double minLat;
	
	private double minLng;
	
	private double maxLat;
	
	private double maxLng;
	
	public double getMinLat() {
		return minLat;
	}

	public void setMinLat(double minLat) {
		this.minLat = minLat;
	}

	public double getMinLng() {
		return minLng;
	}

	public void setMinLng(double minLng) {
		this.minLng = minLng;
	}

	public double getMaxLat() {
		return maxLat;
	}

	public void setMaxLat(double maxLat) {
		this.maxLat = maxLat;
	}

	public double getMaxLng() {
		return maxLng;
	}

	public void setMaxLng(double maxLng) {
		this.maxLng = maxLng;
	}

	private int limit;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public void setMatchType(MatchType matchType) {
		this.matchType = matchType;
	}
	
	public String getMatchTypeName() {
		return matchType.name();
	}

	@Override
	public String toString() {
		return "SearchCondition [address=" + address + ", name=" + name + ", matchType=" + matchType + ", minLat="
				+ minLat + ", minLng=" + minLng + ", maxLat=" + maxLat + ", maxLng=" + maxLng + ", limit=" + limit
				+ "]";
	}
	
}
