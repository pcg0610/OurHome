package com.ourhome.api.chatgpt.entity;

public class ResponseFormat {
	
	private final String DEFAULT_FORMAT = "json_object";
	
	private String type = DEFAULT_FORMAT;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
