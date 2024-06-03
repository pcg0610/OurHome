package com.ourhome.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ourhome.auth.dao.UserDao;
import com.ourhome.auth.entity.AuthEntity;
import com.ourhome.auth.entity.MyPageEntity;
import com.ourhome.auth.entity.TokenEntity;
import com.ourhome.auth.entity.User;
import com.ourhome.auth.util.HashUtil;
import com.ourhome.auth.util.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private HashUtil hashUtil;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public int insertUser(User user) {
		String cipherText = hashUtil.getCipherText(user.getPassword());
		user.setPassword(cipherText);
		return userDao.insertUser(user);
	}

	@Override
	public AuthEntity selectUser(String userId, String password) {
		// 비밀번호 암호화
		String cipherText = hashUtil.getCipherText(password);
		
		// id, password로 사용자 확인
		User user = userDao.selectUser(userId, cipherText);
		
		// 해당 사용자가 없는 경우 null
		if (user == null) {
			return null;
		}
		
		// 사용자가 존재하는 경우 accessToken & refreshToken을 발급
		TokenEntity accessToken = jwtUtil.generateToken(user.getId() ,userId, "AccessToken");
		TokenEntity refreshToken = jwtUtil.generateToken(user.getId(), userId, "RefreshToken");
		
		userDao.insertToken(accessToken);
		userDao.insertToken(refreshToken);
		
		AuthEntity authEntity = new AuthEntity();
		authEntity.setUser(user);
		authEntity.setAccessToken(accessToken.getToken());
		authEntity.setRefreshToken(refreshToken.getToken());
		authEntity.setMaxAge(refreshToken.getExpiration());		
		
		return authEntity; // 로그인 성공 시 User 대신 token의 정보를 전달한다.
	}
	
	@Override
	public int checkUserID(String userId) {
		return userDao.getUserID(userId);
	}

	@Override
	public boolean isValidToken(String token) {
		String cipherText = hashUtil.getCipherText(token);
		return userDao.checkValidToken(cipherText);
	}

	@Override
	public TokenEntity reGenerateToken(String refreshToken) {
		String userId = jwtUtil.getUserId(refreshToken, "RefreshToken");
		
		// refreshToken을 제외한 token에 대하여 invalid하게 만든다.
		userDao.setInvalid(userId, hashUtil.getCipherText(refreshToken));
		
		User user = userDao.getUserById(userId);
		
		// 사용자가 존재한다면 새로운 AccessToken을 생성하고 DB에 저장한다.
		if (user != null) {
			TokenEntity newAccessToken = jwtUtil.generateToken(user.getId(), userId, "AccessToken");
			userDao.insertToken(newAccessToken);
			return newAccessToken;
		}
		
		return null;
	}

	@Override
	public MyPageEntity myPage(String accessToken) {
		String userId = jwtUtil.getUserId(accessToken, "AccessToken");
		User user = userDao.getUserById(userId);
		MyPageEntity userInfo = new MyPageEntity();
		
		userInfo.setId(user.getId());
		userInfo.setName(user.getName());
		userInfo.setBirth(user.getBirth());
		userInfo.setPhoneNumber(user.getPhoneNumber());
		
		if (user.getGender() == 'M') {
			userInfo.setGender("남성");
		} else {
			userInfo.setGender("여성");
		}
		
		userInfo.setImg(user.getImage());
		userInfo.setUserId(userId);
		
		return userInfo;
	}

	@Override
	public void logOut(String accessToken) {
		String userId = jwtUtil.getUserId(accessToken, "AccessToken");
		userDao.setInvalidById(userId);
	}

	@Override
	public void insertPersonality(List<String> personality, long userId) { 
		for (String items : personality) {
			userDao.insertPersonality(items, userId);
		}
	}

	@Override
	public String getUserName(long userId) {
		return userDao.getUserName(userId);
	}

	@Override
	public List<String> getItemList(long userId) {
		return userDao.getItemList(userId);
	}

	@Override
	public long getId(String userId) {
		return userDao.getId(userId);
	}
}
