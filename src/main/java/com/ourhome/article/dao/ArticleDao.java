package com.ourhome.article.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ourhome.article.entity.ArticleEntity;

@Repository
public interface ArticleDao {
	int writeArticle(ArticleEntity article);
	List<ArticleEntity> getArticleListByHome(long homeId);
	List<ArticleEntity> getArticleListById(long userId);
	int removeArticle(ArticleEntity article);
	int updateArticle(ArticleEntity article);
}
