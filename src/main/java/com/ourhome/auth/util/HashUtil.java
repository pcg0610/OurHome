package com.ourhome.auth.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

/*
 * 비밀번호 암호화를 위한 클래스
 */
@Component
public class HashUtil {
	public String getCipherText(String plainText) {
		// MessageDisget : 단방향 해시 값을 구할 때 사용
		MessageDigest digest = null;
		
		try {
			digest = MessageDigest.getInstance("SHA-512"); // 해시 알고리즘을 수행하는 객체 생성
			digest.reset(); // 이전 사용해서 저장되어 있는 값을 초기화
			digest.update(plainText.getBytes("UTF-8")); // digest 객체 내에 존재하는 해시 값을 갱신한다.
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] result = digest.digest(); // 실제 해시 계산을 수행하여 얻은 해시 결과값
		
		// 해시 결과 값을 128자리의 16진수 문자열로 변경한다.
		return String.format("%0128x", new BigInteger(1, result)); 
	}
}
