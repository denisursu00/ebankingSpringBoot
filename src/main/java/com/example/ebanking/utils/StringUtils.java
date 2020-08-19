package com.example.ebanking.utils;

public class StringUtils {

	public static boolean isEmpty(String value) {
		return ((value == null) || value.trim().isEmpty());
	}
	
	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}
}