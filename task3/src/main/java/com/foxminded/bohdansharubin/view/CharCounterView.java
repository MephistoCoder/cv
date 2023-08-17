package com.foxminded.bohdansharubin.view;

import java.util.Map;

import javafx.util.Pair;

public class CharCounterView {
	private static final String DEFAULT_STRING_QUOTES = "\"";
	private static final String DEFAULT_STRING_EOL = "\n";
	private static final String DEFAULT_STRING_HYPHEN = "-";
	private static final String DEFAULT_STRING_SPLITTER = " ";
	private static final String DEFAULT_ALERT_MESSAGE = "Invalid Input. Please, try again";
	
	public void printNumberOfUniqueCharactersUsed(Pair<String, Map<Character, Integer>> lineAndUniqueSymbolsMap) {
		StringBuilder linesOfCharAndNumberInfo = new StringBuilder();
		String originalLine = lineAndUniqueSymbolsMap.getKey();
		Map<Character, Integer> charAndNumberMap = lineAndUniqueSymbolsMap.getValue();
		linesOfCharAndNumberInfo.append(lineAndUniqueSymbolsMap.getKey())
								.append(DEFAULT_STRING_EOL);
		originalLine.chars()
					.distinct()
					.forEachOrdered(uniqueCharCodePoint -> linesOfCharAndNumberInfo.append(DEFAULT_STRING_QUOTES)
																				   .append(Character.toChars(uniqueCharCodePoint))
																				   .append(DEFAULT_STRING_QUOTES)
																				   .append(DEFAULT_STRING_SPLITTER)
																				   .append(DEFAULT_STRING_HYPHEN)
																				   .append(DEFAULT_STRING_SPLITTER)
																				   .append(charAndNumberMap.get((char) uniqueCharCodePoint))
																				   .append(DEFAULT_STRING_EOL)
					);
		System.out.println(linesOfCharAndNumberInfo.toString());
	}
	
	public void printAlertMessage() {
		System.out.println(DEFAULT_ALERT_MESSAGE);
	}
}
