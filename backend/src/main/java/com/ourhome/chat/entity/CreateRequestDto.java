package com.ourhome.chat.entity;

public class CreateRequestDto {
	
	private long postId;
	
	private long userId;

	private String name;

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CreateRequestDto [postId=" + postId + ", userId=" + userId + ", name=" + name + ", getPostId()="
				+ getPostId() + ", getUserId()=" + getUserId() + ", getName()=" + getName() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
