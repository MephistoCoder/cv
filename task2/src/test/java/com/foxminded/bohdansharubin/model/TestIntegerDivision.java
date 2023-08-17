package com.foxminded.bohdansharubin.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.Queue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class TestIntegerDivision {
	private static IntegerDivision integerDivision = new IntegerDivision();
	
	@ParameterizedTest
	@CsvSource({"100, 4"})
	void makeDivisionSteps_fillDivisionStepsQueue_correctDividentAndDivider(int divident, int divider) {
		Queue<Integer> expectedQueue = new ArrayDeque<>();
		expectedQueue.add(10);
		expectedQueue.add(8);
		expectedQueue.add(20);
		expectedQueue.add(0);
		integerDivision.setDivident(divident);
		integerDivision.setDivider(divider);
		integerDivision.makeDivisionSteps();
		Assertions.assertTrue(expectedQueue.containsAll(integerDivision.getDivisionSteps()));
	}
	
	@ParameterizedTest
	@CsvSource({"-100, -4", "-12, 0"})
	void makeDivisionSteps_divisionStepsQueueIsEmpty_incorrectDividentAndDivider(int divident, int divider) {
		integerDivision.setDivident(divident);
		integerDivision.setDivider(divider);
		integerDivision.makeDivisionSteps();
		Assertions.assertTrue(integerDivision.getDivisionSteps().isEmpty());
	}
	
	@Test
	void validateDivision_returnFalse_dividerIsZero() {
		Assertions.assertFalse(new IntegerDivision(1, 0).validateDivision());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-1, -43, -456})
	void validateDivision_returnFalse_dividerLessThenZero(int divider) {
		Assertions.assertFalse(new IntegerDivision(1, divider).validateDivision());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-1, -43, -456})
	void validateDivision_returnFalse_dividentLessThenZero(int divident) {
		Assertions.assertFalse(new IntegerDivision(divident, 1).validateDivision());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-1, -43, -456, 0})
	void makeDivision_divisionIsNotPerform_dividerIsIncorrect(int divider) {
		integerDivision.setDivident(1);
		integerDivision.setDivider(divider);
		integerDivision.makeDivision();
		assertEquals(0, integerDivision.getQuotient());
		assertEquals(0, integerDivision.getRemainder());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-1, -43, -456})
	void makeDivision_divisionIsNotPerform_dividentIsIncorrect(int divident) {
		integerDivision.setDivident(divident);
		integerDivision.setDivider(1);
		integerDivision.makeDivision();
		assertEquals(0, integerDivision.getQuotient());
		assertEquals(0, integerDivision.getRemainder());
	}
	
	@ParameterizedTest
	@CsvSource({"-1, 0", "-124, -45"})
	void makeDivision_divisionIsNotPerform_dividentAndDividerIsIncorrect(int divident, int divider) {
		integerDivision.setDivident(divident);
		integerDivision.setDivider(divider);
		integerDivision.makeDivision();
		assertEquals(0, integerDivision.getQuotient());
		assertEquals(0, integerDivision.getRemainder());
	}
	
	@Test
	void isZeroQuotient_returnTrue_quotientIsZero() {
		integerDivision.setDivident(1);
		integerDivision.setDivider(2);
		integerDivision.makeDivision();
		Assertions.assertTrue(integerDivision.isZeroQuotient());
	}
	
	@Test
	void isZeroQuotient_returnFalse_quotientIsNotZero() {
		integerDivision.setDivident(2);
		integerDivision.setDivider(2);
		integerDivision.makeDivision();
		Assertions.assertFalse(integerDivision.isZeroQuotient());
	}
	
	@Test
	void isZeroRemainder_returnTrue_remainderIsZero() {
		integerDivision.setDivident(2);
		integerDivision.setDivider(2);
		integerDivision.makeDivision();
		Assertions.assertTrue(integerDivision.isZeroRemainder());
	}
	
	@Test
	void isZeroRemainder_returnFalse_remainderIsNotZero() {
		integerDivision.setDivident(3);
		integerDivision.setDivider(2);
		integerDivision.makeDivision();
		Assertions.assertFalse(integerDivision.isZeroRemainder());
	}
}
