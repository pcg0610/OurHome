package com.ourhome.auth.service;

import java.util.List;

import com.ourhome.auth.entity.AuthEntity;
import com.ourhome.auth.entity.MyPageEntity;
import com.ourhome.auth.entity.TokenEntity;
import com.ourhome.auth.entity.User;
import com.ourhome.home.entity.Home;

public interface UserService {
	// 회원가입
	public int insertUser(User user);
	
	// 로그인을 위한 사용자 조회
	public AuthEntity selectUser(String userId, String password);
	
	// 회원 가입 시 사용가능 여부 판단
	public int checkUserID(String userId);

	// 토큰이 유효한 토큰인지 확인
	boolean isValidToken(String token);
	
	// 토큰 재생성
	TokenEntity reGenerateToken(String refreshToken);
	
	// 사용자 정보 & item 목록 받아오기
	MyPageEntity myPage(String accessToken);
	
	// 로그아웃
	void logOut(String accessToken);
	
	// 회원가입 시 입력한 item을 DB에 저장
	void insertPersonality(List<String> personality, long userId);
	
	// 사용자 식별 ID로 사용자의 닉네임을 얻어온다
	String getUserName(long userId);
	
	// 사용자 식별 ID로 사용자 해시태그 리스트를 얻어온다.
	List<String> getItemList(long userId);
	
	// 사용자 닉네임으로 사용자 식별 고유 ID를 가져온다.
	long getId(String userId);
}
