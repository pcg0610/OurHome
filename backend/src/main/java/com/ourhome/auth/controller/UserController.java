package com.ourhome.auth.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ourhome.auth.entity.AuthEntity;
import com.ourhome.auth.entity.LoginEntity;
import com.ourhome.auth.entity.MyPageEntity;
import com.ourhome.auth.entity.TokenEntity;
import com.ourhome.auth.entity.User;
import com.ourhome.auth.service.UserService;
import com.ourhome.auth.util.HeaderUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Tag(name="user_controller", description="User_Controller")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 사용자가 입력한 정보로 DB에서 사용자가 존재하는지 확인 & 토큰 발급
	 * @param userEntity : 사용자 id
	 * @return : user가 존재하는 경우 OK(200) 코드를, 존재하지 않는다면 NO_CONTENT(204)코드 반환
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginEntity userEntity) {
		AuthEntity authEntity = userService.selectUser(userEntity.getUserId(), userEntity.getPassword());
		
		// 해당 user가 존재하지 않는 경우 token이 존재 X
		if (authEntity == null) {
			return ResponseEntity.noContent().build();
		}
		
		// 생성된 token에 대하여 accesstoken의 경우 HTTP Header의 Authorization필드에 담아 전달한다.
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HeaderUtil.getAuthorazationHeader(), HeaderUtil.getTokenPrefix() + authEntity.getAccessToken());
		
		// RefreshToken의 경우에는 Cookie로 전달한다.
		ResponseCookie cookie = ResponseCookie
				.from(HeaderUtil.getRefreshCookie(), authEntity.getRefreshToken()) // cookie의 이름 & 값 설정
				.domain("localhost") // 특정 도메인에서만 사용할 수 있도록 제한
				.path("/") // 특정 servlet에만 전달
				.httpOnly(true) // client를 통해 쿠키 탈취 방지
				.secure(true)
				.maxAge(authEntity.getMaxAge() / 1000) // 쿠키의 만료 기간 설정
				.sameSite("None") // 서드파티 요청에 쿠키 전달 여부 설정 (CSRF 관련 문제 해결) ("None", "Strict", "Lax")
				.build();
		
		User user = authEntity.getUser();
		return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
	}
	
	/**
	 * 사용자가 입력한 정보로 회원가입을 진행
	 * @param user : 사용자가 입력한 개인 정보
	 * @return : 회원가입에 성공하는 경우 CREATED(201)코드를, 회원가입에 실패하는 경우 BAD_REQUEST(400)반환
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody User user) {
		int check = userService.insertUser(user);
		
		// 사용자 식별 고유 id값 load
		long id = userService.getId(user.getUserId());
		
		// 회원가입 성공
		if (check == 1) {
			userService.insertPersonality(user.getPersonality(), id);
			return new ResponseEntity<>(check, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>(check, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/checkID/{userId}")
	public ResponseEntity<?> checkID(@PathVariable String userId) {
		int count = userService.checkUserID(userId);
		
		if (count == 0) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/refresh")
	public ResponseEntity<?> refresh(HttpServletRequest request) {
		String refreshToken = HeaderUtil.getRefreshToken(request);
		
		// 로그인을 하지 않은 상황에서 로그인이 필요한 page로의 이동이 필요한 경우 refreshToken은 null
		// null 인경우 badRequest 코드를 반환
		if (refreshToken == null) {
			return ResponseEntity.badRequest().build();
		}
		
		TokenEntity newAccessToken = userService.reGenerateToken(refreshToken);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HeaderUtil.getAuthorazationHeader(), HeaderUtil.getTokenPrefix() + newAccessToken);
	
		return ResponseEntity.ok().headers(httpHeaders).build();
	}
	
	@GetMapping("/mypage")
	public ResponseEntity<?> myPage(HttpServletRequest request) {
		String accessToken = HeaderUtil.getAccessToken(request);
		MyPageEntity userInfo = userService.myPage(accessToken);
		
		userInfo.setItems(userService.getItemList(userInfo.getId()));
		
		return new ResponseEntity<>(userInfo, userInfo != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		String accessToken = HeaderUtil.getAccessToken(request);
		
		userService.logOut(accessToken);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseCookie responseCookie = ResponseCookie
				.from(HeaderUtil.getRefreshCookie(), "")
				.domain("localhost")
				.path("/")
				.httpOnly(true)
				.secure(true)
				.maxAge(0)
				.sameSite("None")
				.build();
		return ResponseEntity.noContent()
				.headers(httpHeaders)
				.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
				.build();
	}
	
	@GetMapping("/name")
	public ResponseEntity<?> getUserName(@RequestParam long userId) {
		String name = userService.getUserName(userId);
		
		return new ResponseEntity<> (name, name != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/items")
	public ResponseEntity<?> getItemList(@RequestParam long userId) {
		List<String> itemList = userService.getItemList(userId);
		return new ResponseEntity<> (itemList, itemList != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
	}
}
