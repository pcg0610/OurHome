package com.ourhome.home.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ourhome.home.dao.HomeDao;
import com.ourhome.home.entity.ComboboxItemDto;
import com.ourhome.home.entity.Home;
import com.ourhome.home.entity.SearchCondition;

@Service
public class HomeServiceImpl implements HomeService {
	
	private final HomeDao homeDao;
	
	HomeServiceImpl(HomeDao homeDao) {
		this.homeDao = homeDao;
	}
	
	@Override
	public List<ComboboxItemDto> getComboboxItemsByName(String content) {
		return homeDao.findComboboxItemsByName(content);
	}

	@Override
	public Home getHome(long homeId) {
		return homeDao.findOne(homeId);
	}

	@Override
	public List<Home> getFavoritesList(long userId) {
		return homeDao.findAllByUserId(userId);
	}

	@Override
	public List<Home> getHomeList(SearchCondition searchCondition) {
		return homeDao.findAll(searchCondition);
	}

	@Override
	public int insertFavoriteItem(long userId, long homeId) {
		return homeDao.insertFavoriteItem(userId, homeId);
	}

	@Override
	public int deleteFavoriteItem(long userId, long homeId) {
		return homeDao.deleteFavoriteItem(userId, homeId);
	}

}
