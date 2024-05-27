package com.ourhome.api.chatgpt.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ourhome.api.chatgpt.entity.Message;
import com.ourhome.api.chatgpt.entity.Query;
import com.ourhome.api.chatgpt.entity.RequestBodyDto;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class ChatGPTServiceImpl implements ChatGPTService {
	
	private final String URL = "https://api.openai.com/v1/chat/completions";
	
	@Value("openai.api-key")
	private String API_KEY;
	
	private ObjectMapper objectMapper;
	
	public ChatGPTServiceImpl(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public String request(Query query) throws JsonProcessingException {
		
		OkHttpClient client = new OkHttpClient();
		
		RequestBodyDto requestBodyDto = new RequestBodyDto();
		
		Message systemMessage = new Message();
		systemMessage.setRole("system");
		systemMessage.setContent("너는 대한민국의 각 지역의 좌표를 찾아주는 시스템이야");
		
		Message userMessage = new Message();
		userMessage.setRole("user");
		userMessage.setContent(query.getContent() + "의 좌표를 알려줘");
		
		requestBodyDto.getMessages().add(systemMessage);
		requestBodyDto.getMessages().add(userMessage);
		
		System.out.println(objectMapper.writeValueAsString(requestBodyDto));
		
		RequestBody body = RequestBody.create(
			objectMapper.writeValueAsString(requestBodyDto), MediaType.get("application/json; charset=utf-8"));
		
		Request request = new Request.Builder()
				.url(URL)
				.post(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Bearer " + API_KEY)
				.build();
		
		try (Response response = client.newCall(request).execute();) {
			if (!response.isSuccessful()) {
				throw new IOException();
			}
			
			System.out.println(response.body().string());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
