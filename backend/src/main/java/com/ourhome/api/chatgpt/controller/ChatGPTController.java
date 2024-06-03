package com.ourhome.api.chatgpt.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ourhome.api.chatgpt.entity.Query;
import com.ourhome.api.chatgpt.service.ChatGPTService;

@RestController
@RequestMapping("/chatgpt")
public class ChatGPTController {
	
	private ChatGPTService chatGPTService; 

	public ChatGPTController(ChatGPTService chatGPTService) {
		super();
		this.chatGPTService = chatGPTService;
	}

	@GetMapping
	public ResponseEntity<?> requestLocationCoords(Query query) {
		System.out.println(query.getContent());
		
		try {
			chatGPTService.request(query);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
