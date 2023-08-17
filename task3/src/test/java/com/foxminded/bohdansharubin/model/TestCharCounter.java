package com.foxminded.bohdansharubin.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;

class TestCharCounter {
	private CharCounter charCounter = new CharCounter();
	
	@Test
	void countingTheNumberOfUsesOfUniqueChars_returnMapOfUniqueSymbolsWithCount_correctCharArrayOfSymbols() {
		HashMap<Character, Integer> expectedHashMap = new HashMap<>();
		String actualString = "hello World!";
		char[] actualCharArray = actualString.toCharArray();
		Arrays.sort(actualCharArray);
		expectedHashMap.put(' ', 1);
		expectedHashMap.put('!', 1);
		expectedHashMap.put('h', 1);
		expectedHashMap.put('e', 1);
		expectedHashMap.put('l', 3);
		expectedHashMap.put('o', 2);
		expectedHashMap.put('W', 1);
		expectedHashMap.put('r', 1);
		expectedHashMap.put('d', 1);
		assertEquals(expectedHashMap, charCounter.countingTheNumberOfUsesOfUniqueChars(actualCharArray));
	}
	
	@Test
	void doCount_returnPairOfStringAndMapWithCountOfUsesSymbols_correctInputString() {
		String inputString = "hello World!!";
		HashMap<Character, Integer> expectedHashMap = new HashMap<>();
		expectedHashMap.put(' ', 1);
		expectedHashMap.put('!', 2);
		expectedHashMap.put('h', 1);
		expectedHashMap.put('e', 1);
		expectedHashMap.put('l', 3);
		expectedHashMap.put('o', 2);
		expectedHashMap.put('W', 1);
		expectedHashMap.put('r', 1);
		expectedHashMap.put('d', 1);
		Pair<String, Map<Character, Integer>> expectedPair = new Pair<>(inputString, expectedHashMap);
		assertEquals(expectedPair, charCounter.doCount(inputString));
	}
}
