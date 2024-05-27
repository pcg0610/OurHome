package com.ourhome.auth.entity;

import java.util.Date;
import java.util.List;

/*
 * 사용자 정보를 담고 있는 Entity
 */
public class User {
	private long id;
	private String userId;
	private String password;
	private String phoneNumber;
	private String name;
	private Date birth;
	private char gender;
	private String image;
	
	private List<String> personality;
	
	public User() {
		
	}
	
	public User(long id, String userId, String password, String phoneNumber, String name, Date birth, char gender,
			String image, List<String> personality) {
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.image = image;
		this.personality = personality;
	}

	public long getId() {
		return id;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public List<String> getPersonality() {
		return personality;
	}
	public void setPersonality(List<String> personality) {
		this.personality = personality;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", password=" + password + ", phoneNumber=" + phoneNumber
				+ ", name=" + name + ", birth=" + birth + ", gender=" + gender + ", image=" + image + ", personality="
				+ personality + "]";
	}
}
