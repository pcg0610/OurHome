package com.ourhome.auth.entity;

import java.util.Date;
import java.util.List;

/*
 * user 정보를 조회할 수 있는 마이 페이지를 위한 객체
 */
public class MyPageEntity {
	private long id;
	private String name;
	private String userId;
	private String gender;
	private Date birth;
	private String phoneNumber;
	
	private String img;
	private List<String> items;
	
	public MyPageEntity() {
		
	}

	public MyPageEntity(long id, String name, String userId, String gender, Date birth, String phoneNumber, String img,
			List<String> items) {
		this.id = id;
		this.name = name;
		this.userId = userId;
		this.gender = gender;
		this.birth = birth;
		this.phoneNumber = phoneNumber;
		this.img = img;
		this.items = items;
	}

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "MyPageEntity [id=" + id + ", name=" + name + ", userId=" + userId + ", gender=" + gender + ", birth="
				+ birth + ", phoneNumber=" + phoneNumber + ", img=" + img + ", items=" + items + "]";
	}
}
