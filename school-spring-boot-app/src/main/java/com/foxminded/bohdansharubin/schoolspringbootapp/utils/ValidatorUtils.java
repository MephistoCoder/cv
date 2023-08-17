package com.foxminded.bohdansharubin.schoolspringbootapp.utils;

public class ValidatorUtils {
	public static final String NAME_DEFAULT_PATTERN = "[a-zA-Z]{2,}";
	public static final String ANSWER_DEFAULT_PATTERN = "yes|no";
	
	private ValidatorUtils() {
	}
		
	public static boolean isStringMatchesPattern(String data, String pattern) {
		return data != null
				&&
			   pattern != null
				&&
			   data.matches(pattern);
	}
	
	public static boolean isIntegerGreaterThanZero(Integer number) {
		return number != null
				&&
			   number > 0;
	}

}
