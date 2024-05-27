package com.ourhome.auth.filter;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ourhome.auth.exception.ErrorResponse;
import com.ourhome.auth.service.UserService;
import com.ourhome.auth.util.HeaderUtil;
import com.ourhome.auth.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 1번의 요청에 대하여 filter를 적용할 때 사용
// OPTIONS를 통해 통신 option이 설정되었는지 check하는 역할
// CORS 문제 방지를 위한 역할

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserService userService;
	
	public JwtFilter(JwtUtil jwtUtil, UserService userService) {
		this.jwtUtil = jwtUtil;
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Preflight에 대한 설정
		// HTTP 통신 옵션을 설정하고 200을 수신하는지 확인 => 200의 경우 정상적인 통신이 가능
		if (request.getMethod().equals("OPTIONS")) {
			response.setHeader("Access-Control-Allow-Credentials", "true"); // CORS 관련 응답 Header client에서 true로 설정해야 함
			response.setHeader("Access-Control-Allow-Headers", "content-type, authorization"); // 허용되는 HTTP Header 목록
			response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173"); // 허용되는 리소스의 출처
			response.setStatus(HttpStatus.OK.value());

			return;
		}
		
		// Refresh 요청이 발생하는 경우
		// 토큰을 확인해서 발급이 가능한 경우 새로운 토큰 발급
		// 토큰이 비정상적 -> null, 기간이 만료된 경우 -> 토큰 발급 X
		if (request.getRequestURI().contains("/user/refresh")) {
			String refreshToken = HeaderUtil.getRefreshToken(request);
			
			if (refreshToken != null) {
				// 토큰이 존재하지 않는 경우 또는 valid한 토큰이 없다면 재발급이 불가능
				if (!jwtUtil.isValidToken(refreshToken, "RefreshToken") || !userService.isValidToken(refreshToken)) {
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
					response.setContentType("application/json;charset=UTF-8");
					
					ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.UNAUTHORIZED.value());
					PrintWriter printWriter = response.getWriter();
					printWriter.print(errorResponse.toString());
					
					return;
				}
			}
		}
		
		// 재발급 요청이 아닌 경우 => 토큰값을 확인해서 유효한 사용자인지를 판단
		else {
			String accessToken = HeaderUtil.getAccessToken(request);
			// access token이 존재 X 또는 유효하지 않은 토큰 값인 경우 또는 valid값이 0인 경우에는 유효한 토큰이 X

			if (accessToken == null || !jwtUtil.isValidToken(accessToken, "AccessToken") || !userService.isValidToken(accessToken)) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
				
				ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.UNAUTHORIZED.value());
				
				PrintWriter printWriter = response.getWriter();
				printWriter.print(errorResponse.toString());
				return;
			}
			
		}
		
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String[] excludeFilterList = {
				"/user/login", "/user/signup", "/user/checkID", "/user/logout",
				"swagger-ui", "api-docs", "/room/entered", "/ws", "/home", "/user/items", "/room"
		};
		
		// true의 경우 filter의 대상이 되지 않는다.
		for (String uri : excludeFilterList) {
			if (request.getRequestURI().contains(uri)) {
				return true;
			}
		}
		
		// false의 경우 filter의 대상이 된다
		return false;
	}
}
