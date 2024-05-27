package com.ourhome.api.chatgpt.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestBodyDto {
	
	private final String DEFAULT_MODEL = "gpt-3.5-turbo";

	private String model;
	
	@JsonProperty("response_format")
	private ResponseFormat responseFormat;
	
	private List<Message> messages;
	
	public RequestBodyDto() {
		this.model = DEFAULT_MODEL;
		this.messages = new ArrayList<>();
		this.responseFormat = new ResponseFormat();
	}

	public String getModel() {
		return model;
	}

	public ResponseFormat getResponseFormat() {
		return responseFormat;
	}

	public void setResponseFormat(ResponseFormat responseFormat) {
		this.responseFormat = responseFormat;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
}
