package com.ourhome.article.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ourhome.article.dao.ArticleDao;
import com.ourhome.article.entity.ArticleEntity;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;
	
	@Override
	public int writeArticle(ArticleEntity article) {
		return articleDao.writeArticle(article);
	}

	@Override
	public List<ArticleEntity> getArticleListByHome(long homeId) {
		return articleDao.getArticleListByHome(homeId);
	}

	@Override
	public List<ArticleEntity> getArticleListById(long userId) {
		return articleDao.getArticleListById(userId);
	}

	@Override
	public boolean removeArticle(ArticleEntity article) {
		int check = articleDao.removeArticle(article);
		
		if (check > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public int updateArticle(ArticleEntity article) {
		return articleDao.updateArticle(article);
	}

}
