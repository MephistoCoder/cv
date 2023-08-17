package com.foxminded.bohdansharubin.model;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.util.Pair;

public class CharCounter {
	private CharCounterCache cache = new CharCounterCache();
	
	public Pair<String, Map<Character, Integer>> doCount(String inputLine) {
		if(cache.findMapForLineInCache(inputLine) == null) {
			cache.addAnalyzedLineToCache(inputLine, countingTheNumberOfUsesOfUniqueChars(splitWordsToCharArray(inputLine)));
		}
		return new Pair<>(inputLine, cache.findMapForLineInCache(inputLine));
	}
	private char[] splitWordsToCharArray(String wordsLine) {
		return wordsLine.toCharArray();
	}
 	
 	public Map<Character, Integer> countingTheNumberOfUsesOfUniqueChars(char[] arraySymbols) {
 		LinkedHashMap<Character, Integer> amountUniqueSymbolsMap = new LinkedHashMap<>();
		if(arraySymbols.length == 0) {
			return amountUniqueSymbolsMap;
		}
		for(char character: arraySymbols) {
			amountUniqueSymbolsMap.compute(character, (key, value) -> (value == null) ? 1 : value + 1);
		}
		return amountUniqueSymbolsMap;
	}
}
