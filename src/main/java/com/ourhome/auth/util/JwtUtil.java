package com.ourhome.auth.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ourhome.auth.entity.TokenEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Autowired
	private HashUtil hashUtil;
	
	private final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 15; //15분
	private final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 4; // 4시간

	@Value("${jwt.secretkey.accesstoken}")
	private String accessTokenSecretKey;
	
	@Value("${jwt.secretkey.refreshtoken}")
	private String refreshTokenSecretKey;
	
	/**
	 * type에 맞추어 secretKey를 생성
	 * @param type : 사용할 키를 구분하기 위한 역할
	 * @return : 생성된 key를 반환
	 */
	private SecretKey getSecretKey(String type) {
		if (type.equals("AccessToken")) {
			return Keys.hmacShaKeyFor(accessTokenSecretKey.getBytes());
		}
		else if (type.equals("RefreshToken")) {
			return Keys.hmacShaKeyFor(refreshTokenSecretKey.getBytes());
		}
		
		return null;
	}
	
	/**
	 * 발행 시간을 기준으로 만료 시간을 설정한다.
	 * @param issuedAt : 해당 토큰이 발행된 시간
	 * @param type : 사용할 키를 구별하기 위한 역할
	 * @return : 만료 시간 반환
	 */
	private long getExpiration(long issuedAt, String type) {
		if (type.equals("AccessToken")) {
			return issuedAt + ACCESS_TOKEN_EXPIRE_TIME;
		}
		else if (type.equals("RefreshToken")) {
			return issuedAt + REFRESH_TOKEN_EXPIRE_TIME;
		}
		
		return 0L;
	}
	
	/**
	 * 사용자의 ID를 기반으로 토큰을 생성한다.
	 * @param userId : 토큰 생성에 필요한 사용자 ID
	 * @param type : 타입에 따른 토큰 생성
	 * @return : 생성된 토큰 문자열 반환
	 */
	public TokenEntity generateToken(long id, String userId, String type) {
		long issuedAt = System.currentTimeMillis();
		long expiration = getExpiration(issuedAt, type);
		
		Map<String, String> header = new HashMap<>();
		header.put("typ", "jwt");
		header.put("alg", "HS256");
		Jwts.header().add(header).build();
		
		String token = Jwts.builder()
				.header().add(header).and()
				.claim("userId", userId)
				.issuedAt(new Date(issuedAt)) // 토큰 발행 시간 설정
				.expiration(new Date(expiration))
				.signWith(getSecretKey(type))
				.compact();
		
		TokenEntity tokenEntity = new TokenEntity();
		tokenEntity.setId(id);
		tokenEntity.setUserId(userId);
		tokenEntity.setToken(token);
		tokenEntity.setHashedToken(hashUtil.getCipherText(token));
		tokenEntity.setIssuedAt(issuedAt);
		tokenEntity.setExpiration(expiration);
		return tokenEntity;
	}
	
	public String getUserId(String token, String type) {
		if (isValidToken(token, type)) {
			Claims payload = Jwts.parser()
					.verifyWith(getSecretKey(type))
					.build().parseSignedClaims(token).getPayload();
			
			return payload.get("userId", String.class);
		}
		
		return null;
	}
	
	/**
	 * token의 유효성을 검증하여 유효한 토큰인 경우 payload를 반환한다.
	 * @param token : 검증 대상이 되는 token
	 * @param type : 사용할 secretKey 타입
	 * @return : 유효한 토큰인 경우 payload를 아닌 경우에는 null을 반환
	 */
	public boolean isValidToken(String token, String type) {
		Jws<Claims> payload = null;
		try {
			payload = Jwts.parser()
					.verifyWith(getSecretKey(type))
					.build().parseSignedClaims(token);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
}
