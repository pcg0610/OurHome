package com.ourhome.article.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ourhome.article.entity.ArticleEntity;
import com.ourhome.article.service.ArticleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/article")
@Tag(name="board_controller", description="게시글 관련 기능")
public class BoardController {
	
	@Autowired
	private ArticleService articleService;
	
	// 각 집에 대하여 게시글을 추가한다.
	@PostMapping("/write")
	@Operation(
			description ="게시글 추가",
			responses = {
					@ApiResponse(responseCode = "201", description="게시글 생성 완료"),
					@ApiResponse(responseCode = "204", description="게시글 생성 실패")})
	public ResponseEntity<?> writeArticle(@RequestBody ArticleEntity article) {
		int check = articleService.writeArticle(article);
		return new ResponseEntity<>(check, check == 1 ? HttpStatus.CREATED : HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateArticle(@RequestBody ArticleEntity article) {
		int check = articleService.updateArticle(article);
		
		return new ResponseEntity<>(check, check > 0 ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST);
	}
	
	// 각 집에 대하여 사람들이 남긴 게시글을 조회
	@GetMapping("/home/list")
	public ResponseEntity<?> getArticleListByHome(@RequestParam long homeId) {
		List<ArticleEntity> articleList = articleService.getArticleListByHome(homeId);
		return new ResponseEntity<> (articleList, articleList != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/id/list")
	public ResponseEntity<?> getArticleListById(@RequestParam long userId) {
		List<ArticleEntity> articleList = articleService.getArticleListById(userId);
		return new ResponseEntity<> (articleList, articleList != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/remove")
	public ResponseEntity<?> removeArticle(@RequestBody ArticleEntity article) {
		boolean check = articleService.removeArticle(article);
		if (check) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.badRequest().build();
	}
}
