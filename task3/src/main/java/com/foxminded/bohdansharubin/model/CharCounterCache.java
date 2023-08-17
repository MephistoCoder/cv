package com.foxminded.bohdansharubin.model;

import java.util.HashMap;
import java.util.Map;

public class CharCounterCache {
	private Map<String, Map<Character, Integer>> cacheOfAnalyzedLines = new HashMap<>();
	
	public void addAnalyzedLineToCache(String line, Map<Character, Integer> countingOfUsesChars ) {
			cacheOfAnalyzedLines.put(line, countingOfUsesChars);
	}
	
	public Map<Character, Integer> findMapForLineInCache(String line) {
		return cacheOfAnalyzedLines.get(line);
	}
}
