package com.ourhome.auth.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * HTTP 통신에서 Header에 필요한 설정을 위한 클래스
 */
public class HeaderUtil {
	private static final String AUTHORIZATION = "Authorization";
	private static final String REFRESH_TOKEN = "RefreshToken";
	private static final String TOKEN_PREFIX = "Bearer ";
	
	public static String getAuthorazationHeader() {
		return AUTHORIZATION;
	}
	
	public static String getTokenPrefix() {
		return TOKEN_PREFIX;
	}
	
	public static String getRefreshCookie() {
		return REFRESH_TOKEN;
	}
	
	public static String getAccessToken(HttpServletRequest request) {
		// Authorization Header에서 accessToken을 가져온다.
		String authorization = request.getHeader(AUTHORIZATION);
		
		// accessToken에서 앞에 Bearer Token의 접두어를 제거한 후 accessToken만 반환
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return authorization.substring(TOKEN_PREFIX.length());
		}
		
		return null;
	}
	
	public static String getRefreshToken(HttpServletRequest request) {
		Cookie[] cookieList = request.getCookies();
		
		// cookie에 담아서 보냈던 RefreshToken의 존재 여부 확인
		if (cookieList == null) {
			return null;
		}
		
		// 쿠키에서 RefreshToken을 추출하여 반환
		for (Cookie cookie : cookieList) {
			if (cookie.getName().equals(REFRESH_TOKEN)) {
				return cookie.getValue();
			}
		}
		
		return null;
	}
}
