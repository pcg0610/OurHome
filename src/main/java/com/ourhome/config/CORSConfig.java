package com.ourhome.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * 향하 @Configuration 태크 추가 필요
 */
@Configuration
public class CORSConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**") // API 요청 경로
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") 
			// 허용할 HTTP 메소드 cf) OPTIONS=> 실제 데이터 통신  전 보내는 예비 요청으로 CORS 위반 여부를 검사 
			//.allowedOriginPatterns("http://localhost:5173")
			.allowedOrigins("http://localhost:5173/") // 클라이언트 주소
			.allowCredentials(true) // HttpOnly Cookie를 사용하기 위한 설정
			.exposedHeaders("Access-Control-Allow-Headers")
			.exposedHeaders("Authorization") // 클라이언트에서 해당 Header의 값을 확인할 수 있도록 설정.
			.maxAge(3600); // Preflight Cache 설정
		
		WebMvcConfigurer.super.addCorsMappings(registry);
	}
}
