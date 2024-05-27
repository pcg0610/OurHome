package com.ourhome.chat.service;

import java.util.List;

import com.ourhome.chat.entity.ChatRoom;
import com.ourhome.chat.entity.CreateRequestDto;
import com.ourhome.chat.entity.EnterRequestDto;

public interface ChatRoomService {
	List<ChatRoom> getEnteredList(long userId);

	void save(CreateRequestDto chatRoom);
	
	boolean enter(EnterRequestDto enterRequestDto);
	
	long getPostId(long userId, long homeId);
}
