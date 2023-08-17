package com.foxminded.bohdansharubin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Anagram {
	private String line;
	private String lineOfAnagram = "";
	private List<Word> wordsList;
	static final String DEFAULT_STRING_SPLITTER = " ";

	static class Word {
		private String wordInOriginalOrder;
		private String wordInReverseOrder;

		public static List<Word> splitWordsFromStringLine(String line) {
			List<Word> wordsList = new ArrayList<>();
			for (String stringWord : line.split(DEFAULT_STRING_SPLITTER)) {
				wordsList.add(new Word(stringWord));
			}
			return wordsList;
		}

		private Word deleteNonLettersFromWordInReverseOrder() {
			String wordWithoutNonLettersInReverseOrder = this.getWordInReverseOrder().chars()
					.filter(Character::isLetter)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
			this.setWordInReverseOrder(wordWithoutNonLettersInReverseOrder);
			return this;
		}

		public String getWordInReverseOrder() {
			return wordInReverseOrder;
		}

		public Word setWordInReverseOrder(String wordInReverseOrder) {
			this.wordInReverseOrder = wordInReverseOrder;
			return this;
		}

		public Word(String wordInOriginalOrder) {
			this.wordInOriginalOrder = wordInOriginalOrder;
		}

		public String getWordInOriginalOrder() {
			return wordInOriginalOrder;
		}

		@Override
		public int hashCode() {
			return Objects.hash(wordInOriginalOrder, wordInReverseOrder);
		}

		@Override
		public boolean equals(Object otherObject) {
			if (this == otherObject)
				return true;
			if (otherObject == null)
				return false;
			if (this.getClass() != otherObject.getClass())
				return false;
			Word other = (Word) otherObject;
			return Objects.equals(wordInOriginalOrder, other.wordInOriginalOrder)
					&& Objects.equals(wordInReverseOrder, other.wordInReverseOrder);
		}

	}

	public Anagram() {
	}

	public Anagram(String line) {
		this.line = line;
		this.wordsList = Word.splitWordsFromStringLine(this.line);
	}

	private String reverseWord(Word word) {
		return new StringBuilder(word.getWordInOriginalOrder()).reverse().toString();
	}

	public void makeAnagram() {
			if(validateInput()) {
				reverseLine();				
			} else {
				setLine("");
			}
	}
	
	private String reverseLine() {
		StringBuilder lineInReverseOrder = new StringBuilder();
		for (Word word : wordsList) {
			if (!containsLetter(word)) {
				lineInReverseOrder.append(word.getWordInOriginalOrder()).append(DEFAULT_STRING_SPLITTER);
			} else {
				word.setWordInReverseOrder(reverseWord(word)).deleteNonLettersFromWordInReverseOrder();
				lineInReverseOrder.append(insertNonLettersInOriginalPositions(word)).append(DEFAULT_STRING_SPLITTER);
			}
		}
		lineInReverseOrder.deleteCharAt(lineInReverseOrder.length() - 1);
		this.lineOfAnagram = lineInReverseOrder.toString();
		return getAnagram();
	}

	private String insertNonLettersInOriginalPositions(Word word) {
		StringBuilder wordInReverseOrder = new StringBuilder(word.getWordInReverseOrder());
		char[] charsInOriginalOrder = word.getWordInOriginalOrder().toCharArray();
		for (int i = 0; i < charsInOriginalOrder.length; i++) {
			if (!Character.isLetter(charsInOriginalOrder[i])) {
				wordInReverseOrder.insert(i, charsInOriginalOrder[i]);
			}
		}
		word.setWordInReverseOrder(wordInReverseOrder.toString());
		return word.getWordInReverseOrder();
	}

	private boolean containsLetter(Word word) {
		for (char i : word.getWordInOriginalOrder().toCharArray()) {
			if (Character.isLetter(i)) {
				return true;
			}
		}
		return false;
	}

	private boolean isInputStringCorrect(String inputString) {
		if(inputString == null 
				|| inputString.isEmpty() 
				|| inputString.length() == 1 
				|| inputString.trim().length() == 0 
				|| inputString.chars().collect(HashSet::new, HashSet::add, HashSet::addAll).size() == 1) {
			throw new IllegalArgumentException("String to convert to anagram has invalid data");
		}
		return true;
	}
	
	private boolean validateInput() {
		try {
			isInputStringCorrect(getLine());
			for(Word word: wordsList) {
				isInputStringCorrect(word.getWordInOriginalOrder());
			} 
		} catch(IllegalArgumentException e) {
				System.err.println(e.getMessage() + " line = '" + getLine() + "'");
				return false;
			}
		return true;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		if(line == null) {
			this.line = "";
		} else {
			this.line = line;
			this.wordsList = Word.splitWordsFromStringLine(this.line);			
		}
	}

	public String getAnagram() {
		return lineOfAnagram;
	}

	@Override
	public String toString() {
		return "Anagram [line=" + line + ", anagram=" + lineOfAnagram + "]";
	}

}
