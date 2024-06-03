package com.ourhome.home.entity;

import java.util.Date;

public class Home {
	private long id;
	
	private String name;
	
	private String type;
	
	private String address;
	
	private int buildYear;
	
	private int jeonsae;
	
	private String monthlyDeposit;
	
	private String monthlyPay;
	
	private double area;
	
	private String phone;
	
	private String lng;
	
	private String lat;
	
	private boolean isFavorite;
	
	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	private Date registeredDate;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getBuildYear() {
		return buildYear;
	}

	public void setBuildYear(int buildYear) {
		this.buildYear = buildYear;
	}

	public int getJeonsae() {
		return jeonsae;
	}

	public void setJeonsae(int jeonsae) {
		this.jeonsae = jeonsae;
	}

	public String getMonthlyDeposit() {
		return monthlyDeposit;
	}

	public void setMonthlyDeposit(String monthlyDeposit) {
		this.monthlyDeposit = monthlyDeposit;
	}

	public String getMonthlyPay() {
		return monthlyPay;
	}

	public void setMonthlyPay(String monthlyPay) {
		this.monthlyPay = monthlyPay;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	@Override
	public String toString() {
		return "Home [id=" + id + ", name=" + name + ", type=" + type + ", address=" + address + ", buildYear="
				+ buildYear + ", jeonsae=" + jeonsae + ", monthlyDeposit=" + monthlyDeposit + ", monthlyPay="
				+ monthlyPay + ", area=" + area + ", phone=" + phone + ", lng=" + lng + ", lat=" + lat
				+ ", registeredDate=" + registeredDate + "]";
	}
}
