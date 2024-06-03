package com.ourhome.home.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ourhome.home.entity.ComboboxItemDto;
import com.ourhome.home.entity.FavoriteEntity;
import com.ourhome.home.entity.Home;
import com.ourhome.home.entity.SearchCondition;
import com.ourhome.home.service.HomeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/home")
public class HomeController {
	
	private final HomeService homeService;
	
	HomeController(HomeService homeService) {
		this.homeService = homeService;
	}
	
	@GetMapping("")
	@Operation(
			description = "이름을 이용해 매물을 조회해서 반환하는 API",
			method = "GET",
			parameters = {
					@Parameter(description = "매물 이름이 포함할 내용", name = "content")
			},
			responses = {
					@ApiResponse(responseCode = "200"),
					@ApiResponse(responseCode = "204", description = "내용을 포함하는 매물이 없습니다.")
			})
	public ResponseEntity<?> listComboboxItemByName(
			@RequestParam(value = "content") String content) {
		List<ComboboxItemDto> homeList = homeService.getComboboxItemsByName(content);
		
		if (homeList == null || homeList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok().body(homeList);
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> list(SearchCondition searchCondition) {
		List<Home> homeList = homeService.getHomeList(searchCondition);
		
		if (homeList == null || homeList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(homeList);
	}

	@GetMapping("/{homeId}")
	public ResponseEntity<?> detail(
			@Parameter(description = "조회할 매물의 아이디")
			@PathVariable(value = "homeId") long homeId) {
		Home home = homeService.getHome(homeId);
		return ResponseEntity.ok().body(home);
	}
	
	@GetMapping("/favorites")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "사용자가 즐겨찾기한 매물 리스트입니다."),
			@ApiResponse(responseCode = "204", description = "즐겨찾기한 매물이 없습니다!")
	})
	public ResponseEntity<?> favoritesList(@RequestParam long userId) {
		List<Home> favoritesList = homeService.getFavoritesList(userId);

		if (favoritesList == null || favoritesList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok().body(favoritesList);
	}
	
	@PostMapping("/insert")
	public ResponseEntity<?> insertFavoriteItem(@RequestBody FavoriteEntity favorite) {
		int check = homeService.insertFavoriteItem(favorite.getUserId(), favorite.getHomeId());
		
		return new ResponseEntity<> (check, check == 1 ? HttpStatus.CREATED : HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteFavoriteItem(@RequestBody FavoriteEntity favorite) {
		int check = homeService.deleteFavoriteItem(favorite.getUserId(), favorite.getHomeId());
		
		if (check == 1) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.badRequest().build();
		
	}
}