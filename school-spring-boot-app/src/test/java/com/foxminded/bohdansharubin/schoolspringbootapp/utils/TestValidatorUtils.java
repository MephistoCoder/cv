package com.foxminded.bohdansharubin.schoolspringbootapp.utils;

import static com.foxminded.bohdansharubin.schoolspringbootapp.utils.ValidatorUtils.isIntegerGreaterThanZero;
import static com.foxminded.bohdansharubin.schoolspringbootapp.utils.ValidatorUtils.isStringMatchesPattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class TestValidatorUtils {
	private static final String STRING_TEST_PATTERN = "[a-zA-Z]{2,}";
	
	@ParameterizedTest
	@ValueSource(strings = {"asd", "ASd", "sdaasdASDA", "sa", "AD"})
	void isStringMatchesPattern_returnTrue_stringMatchesPattern(String testData) {
		Assertions.assertTrue(isStringMatchesPattern(testData, STRING_TEST_PATTERN));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"a", "A", "sda!asdASDA", "s23a", "123", "!$@"})
	void isStringMatchesPattern_returnFalse_stringNotMatchesPattern(String testData) {
		Assertions.assertFalse(isStringMatchesPattern(testData, STRING_TEST_PATTERN));
	}
	
	@ParameterizedTest
	@NullSource
	void isStringMatchesPattern_returnFalse_dataIsNull(String testData) {
		Assertions.assertFalse(isStringMatchesPattern(testData, STRING_TEST_PATTERN));
	}
	
	@ParameterizedTest
	@NullSource
	void isStringMatchesPattern_returnFalse_patternIsNull(String testPattern) {
		Assertions.assertFalse(isStringMatchesPattern("test", testPattern));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1, 10, 150})
	void isIntegerGreaterThanZero_returnTrue_integerGreaterThanZero(Integer testInteger) {
		Assertions.assertTrue(isIntegerGreaterThanZero(testInteger));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, -10, -150})
	void isIntegerGreaterThanZero_returnFalse_integerLessThanZero(Integer testInteger) {
		Assertions.assertFalse(isIntegerGreaterThanZero(testInteger));
	}
	
	@ParameterizedTest
	@NullSource
	void isIntegerGreaterThanZero_returnFalse_integerIsNull(Integer testInteger) {
		Assertions.assertFalse(isIntegerGreaterThanZero(testInteger));
	}

}
