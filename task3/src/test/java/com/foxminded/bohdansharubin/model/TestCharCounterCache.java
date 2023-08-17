package com.foxminded.bohdansharubin.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class TestCharCounterCache {
	
	private CharCounterCache charCounterCache = new CharCounterCache();
	
	@Test
	void addAnalyzedLineToCache_cacheOfAnalyzedLinesIsNotEmpty_correctAnalyzedLineAndMap() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		String analyzedLine = "hello world! It's my first Program";
		Map<Character, Integer> mapOfUniqueSymbols = new HashMap<>();
		charCounterCache.addAnalyzedLineToCache(analyzedLine, mapOfUniqueSymbols);
		Field cacheOfAnalyzedLines = CharCounterCache.class.getDeclaredField("cacheOfAnalyzedLines");
		cacheOfAnalyzedLines.setAccessible(true);
		@SuppressWarnings("unchecked")
		Map<String, Map<Character, Integer>> cache= (Map<String, Map<Character, Integer>>) cacheOfAnalyzedLines.get(charCounterCache);
		Assertions.assertFalse(cache.isEmpty());
	}
	
	@ParameterizedTest
	@NullSource
	void addAnalyzedLineToCache_cacheOfAnalyzedLinesIsEmpty_nullInput(String inputLine) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		CharCounterCache anotherCharCounterCache = new CharCounterCache();
		Map<Character, Integer> mapOfUniqueSymbols = null;
		charCounterCache.addAnalyzedLineToCache(inputLine, mapOfUniqueSymbols);
		Field cacheOfAnalyzedLines = CharCounterCache.class.getDeclaredField("cacheOfAnalyzedLines");
		cacheOfAnalyzedLines.setAccessible(true);
		@SuppressWarnings("unchecked")
		Map<String, Map<Character, Integer>> cache= (Map<String, Map<Character, Integer>>) cacheOfAnalyzedLines.get(anotherCharCounterCache);
		Assertions.assertTrue(cache.isEmpty());
	}
	
	@Test
	void findMapForLineInCache_returnNecessaryMap_inputLineAsKeyExsistInMap() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field cacheOfAnalyzedLines = CharCounterCache.class.getDeclaredField("cacheOfAnalyzedLines");
		String analyzedLine = "hello World";
		Map<Character, Integer> amountUniqueSymbolsMap = new LinkedHashMap<>();
		Map<String, Map<Character, Integer>> cacheMap = new HashMap<>();
		cacheMap.put(analyzedLine, amountUniqueSymbolsMap);
		cacheOfAnalyzedLines.setAccessible(true);
		cacheOfAnalyzedLines.set(charCounterCache, cacheMap);
		Assertions.assertEquals(amountUniqueSymbolsMap, charCounterCache.findMapForLineInCache(analyzedLine));
	}
	
	@Test
	void findMapForLineInCache_returnNull_inputLineAsKeyNotExsistInMap() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		String analyzedLine = "hello";
		Assertions.assertNull(charCounterCache.findMapForLineInCache(analyzedLine));
	}

}
