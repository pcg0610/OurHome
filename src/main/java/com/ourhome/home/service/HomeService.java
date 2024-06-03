package com.ourhome.home.service;

import java.util.List;

import com.ourhome.home.entity.ComboboxItemDto;
import com.ourhome.home.entity.Home;
import com.ourhome.home.entity.SearchCondition;

public interface HomeService {
	
	List<Home> getHomeList(SearchCondition searchCondition);
	
	List<ComboboxItemDto> getComboboxItemsByName(String content);

	Home getHome(long homeId);

	List<Home> getFavoritesList(long userId);
	
	int insertFavoriteItem(long userId, long homeId);
	
	int deleteFavoriteItem(long userId, long homeId);
}
