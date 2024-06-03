package com.ourhome.api.chatgpt.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ourhome.api.chatgpt.entity.Query;

public interface ChatGPTService {

	String request(Query query) throws JsonProcessingException, IOException;
}
