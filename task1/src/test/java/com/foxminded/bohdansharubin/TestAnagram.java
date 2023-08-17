package com.foxminded.bohdansharubin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.foxminded.bohdansharubin.Anagram.Word;

class TestAnagram {
	private final Anagram anagram = new Anagram();
	private static List<Method> methodsOfAnagramAndWordClass;
	private static List<Field> fieldsOfAnagramAndWordClass;
	
	@BeforeAll
	static void getAllMethodsFromAnagramAndWord() {
		Method[] methodsOfAnagramClass;
		Method[] methodsOfWordClass;
		methodsOfAnagramClass = Anagram.class.getDeclaredMethods();
		methodsOfWordClass = Word.class.getDeclaredMethods();
		methodsOfAnagramAndWordClass = new ArrayList<>();
		methodsOfAnagramAndWordClass.addAll(Arrays.asList(methodsOfAnagramClass));
		methodsOfAnagramAndWordClass.addAll(Arrays.asList(methodsOfWordClass));
	}
	
	@BeforeAll
	static void getAllFieldsFromAnagramAndWord() {
		Field[] fieldsOfAnagramClass;
		Field[] fieldsOfWordClass;
		fieldsOfAnagramClass = Anagram.class.getDeclaredFields();
		fieldsOfWordClass = Word.class.getDeclaredFields();
		fieldsOfAnagramAndWordClass = new ArrayList<>();
		fieldsOfAnagramAndWordClass.addAll(Arrays.asList(fieldsOfAnagramClass));
		fieldsOfAnagramAndWordClass.addAll(Arrays.asList(fieldsOfWordClass));
	}
	
	private Method getMethod(String nameOfMethod) {
		for(Method method: methodsOfAnagramAndWordClass) {
			if (method.getName().equals(nameOfMethod)) {
				method.setAccessible(true);
				return method;
			}
		}
		return null;
	}
	
	private Field getField(String nameOfField) {
		for(Field field: fieldsOfAnagramAndWordClass) {
			if (field.getName().equals(nameOfField)) {
				field.setAccessible(true);
				return field;
			}
		}
		return null;
	}
	
	@Test
 	void containsLetter_returnTrue_wordContainLetter() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Word wordWithAllLetter = new Word("letter");
		Word wordWithOneNumber = new Word("l1etter");
		Word wordWithOneNumberInTheEnd = new Word("letter1");
		Word wordWithOneLetter = new Word("l12345!");
		Method isWordNotContainLetterMethod = getMethod("containsLetter");
		Assertions.assertAll("wordInOriginalOrder", 
				() -> Assertions.assertTrue((boolean) isWordNotContainLetterMethod.invoke(anagram, wordWithAllLetter)), 
				() -> Assertions.assertTrue((boolean) isWordNotContainLetterMethod.invoke(anagram, wordWithOneNumber)),
				() -> Assertions.assertTrue((boolean) isWordNotContainLetterMethod.invoke(anagram, wordWithOneNumberInTheEnd)),
				() -> Assertions.assertTrue((boolean) isWordNotContainLetterMethod.invoke(anagram, wordWithOneLetter)));
	}
	
	@Test
	void containsLetter_returnFalse_wordNotContainLetter() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Word wordWithoutLettersOnlyNumber = new Word("123412");
		Word wordWithoutLettersOnlyNonNumber = new Word("!..,./");
		Word wordWithoutLettersWithDifferentSymbols = new Word("1234.!");
		Method isWordNotContainLetterMethod = getMethod("containsLetter");
		
		Assertions.assertAll("wordInOriginalOrder", 
				() -> Assertions.assertFalse((boolean) isWordNotContainLetterMethod.invoke(anagram, wordWithoutLettersOnlyNumber)), 
				() -> Assertions.assertFalse((boolean) isWordNotContainLetterMethod.invoke(anagram, wordWithoutLettersOnlyNonNumber)),
				() -> Assertions.assertFalse((boolean) isWordNotContainLetterMethod.invoke(anagram, wordWithoutLettersWithDifferentSymbols)));	
	}

	@Test
	void reverseWord_returnWordInReverseOrder_wordContainsOnlyLetters() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Word word = new Word("abcdefaeg");
		Method methodReverseWord = getMethod("reverseWord");
			assertEquals("geafedcba", (String)methodReverseWord.invoke(anagram, word));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"a1bcd", "efg!h", "123a5b"})
	void reverseWord_returnWordInReverseOrder_wordContainsDifferentSymbols(String inputWord) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method reverseWordMethod = getMethod("reverseWord");	
		assertEquals(new StringBuilder(inputWord).reverse().toString(), (String) reverseWordMethod.invoke(anagram, new Word(inputWord)) );	
	}
	
	@Test
	void splitWordsFromStringLine_returnArrayListOfWords_ManyStringsWord() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			String line = "abcd 1234 a1bcd";
			List<Word> listOfWordsExpected = new ArrayList<>();
			for(String word: line.split(Anagram.DEFAULT_STRING_SPLITTER)) {
				listOfWordsExpected.add(new Word(word));				
			}
			Method splitWordsFromStringLine = getMethod("splitWordsFromStringLine");
			@SuppressWarnings("unchecked")
			List<Word> listOfWordsActual = (List<Word>) splitWordsFromStringLine.invoke(anagram, line);
			assertEquals(listOfWordsExpected, listOfWordsActual);
	}
	
	@Test
	void splitWordsFromStringLine_returnArrayListOfWords_OneStringWord() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			String line = "abcd";
			List<Word> listOfWordsExpected = new ArrayList<>();
			for(String word: line.split(Anagram.DEFAULT_STRING_SPLITTER)) {
				listOfWordsExpected.add(new Word(word));				
			}
			Method splitWordsFromStringLine = getMethod("splitWordsFromStringLine");
			@SuppressWarnings("unchecked")
			List<Word> listOfWordsActual = (List<Word>) splitWordsFromStringLine.invoke(anagram, line);
			assertEquals(listOfWordsExpected, listOfWordsActual);
	}

	@Test
	void deleteNonLettersFromWordInReverseOrder_wordInReverseOrderFieldNotContainNonLetter_wordInReverseOrderFieldNotNull()  throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Word wordExpected = new Word("a1b2cd");
		Word wordActual = new Word("a1b2cd");
		
		Field wordInReverseOrderFieldFromWordExpected = getField("wordInReverseOrder");
		wordInReverseOrderFieldFromWordExpected.set(wordExpected, "abcd");
			
		Field wordInReverseOrderFieldFromWordActual = getField("wordInReverseOrder");
		wordInReverseOrderFieldFromWordActual.set(wordActual, "a1b2cd");
		
		Method methodDeleteNonLettersFromWordInReverseOrder = getMethod("deleteNonLettersFromWordInReverseOrder");
		methodDeleteNonLettersFromWordInReverseOrder.invoke(wordActual);
		assertEquals(wordExpected, wordActual);
	}
	
	@Test
	void insertNonLettersInOriginalPositions_addNonLettersInWordInReverseOrderInOriginalOrder_wordInOriginalOrderNotNull() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Word wordExpected = new Word("a1b2cd");
		Word wordActual = new Word("a1b2cd");
			
		Field wordInReverseOrderFieldFromWordExpected = getField("wordInReverseOrder");
		wordInReverseOrderFieldFromWordExpected.set(wordExpected, "d1c2ba");
			
		Field wordInReverseOrderFieldFromWordActual = getField("wordInReverseOrder");
		wordInReverseOrderFieldFromWordActual.set(wordActual, "dcba");
		
		Method insertNonLettersInOriginalPositions = getMethod("insertNonLettersInOriginalPositions");
		insertNonLettersInOriginalPositions.invoke(anagram, wordActual);
		assertEquals(wordExpected.getWordInReverseOrder(), wordActual.getWordInReverseOrder());
	}
	
	@Test
	void reverseLine_returnLineInReverseOrder_stringLineIsCorrect() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		anagram.setLine("abcd 1234 a1bcd efg!h 12a3b");
		Method reverseLineMethod = getMethod("reverseLine");
		reverseLineMethod.invoke(anagram);
		assertEquals("dcba 1234 d1cba hgf!e 12b3a", anagram.getAnagram());
	}

	@Test
	void isInputStringCorrect_throwsIllegalArgumentException_StringIsNull() throws IllegalAccessException, IllegalArgumentException {
		String nullString = null;
		Method isInputStringCorrect = getMethod("isInputStringCorrect");
		InvocationTargetException expectedException = Assertions.assertThrows(InvocationTargetException.class, () ->{ isInputStringCorrect.invoke(anagram, nullString); });
		assertEquals(IllegalArgumentException.class, expectedException.getCause().getClass());
		assertEquals("String to convert to anagram has invalid data", expectedException.getCause().getMessage());
	}
	
	@Test
	void isInputStringCorrect_throwsIllegalArgumentException_StringIsEmpty() throws IllegalAccessException, IllegalArgumentException {
		String emptyString = "";
		Method isInputStringCorrect = getMethod("isInputStringCorrect");	
		InvocationTargetException expectedException = Assertions.assertThrows(InvocationTargetException.class, () ->{ isInputStringCorrect.invoke(anagram, emptyString); });
		assertEquals(IllegalArgumentException.class, expectedException.getCause().getClass());
		assertEquals("String to convert to anagram has invalid data", expectedException.getCause().getMessage());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {" ", "  ", "      "})
	void isInputStringCorrect_throwIsllegalArgumentException_StringOfDifferentAmountOfSpaces(String inputString) throws IllegalAccessException, IllegalArgumentException {
		Method isInputStringCorrect = getMethod("isInputStringCorrect");	
		InvocationTargetException expectedException = Assertions.assertThrows(InvocationTargetException.class, () ->{ isInputStringCorrect.invoke(anagram, inputString); });
		assertEquals(IllegalArgumentException.class, expectedException.getCause().getClass());
		assertEquals("String to convert to anagram has invalid data", expectedException.getCause().getMessage());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"a", "aaaaaa", "AAAAAAA"})
	void isInputStringCorrect_throwsIllegalArgumentException_StringOfSameCharacter(String inputString) throws IllegalAccessException, IllegalArgumentException {
		Method isInputStringCorrect = getMethod("isInputStringCorrect");	
		InvocationTargetException expectedException = Assertions.assertThrows(InvocationTargetException.class, () ->{ isInputStringCorrect.invoke(anagram, inputString); });
		assertEquals(IllegalArgumentException.class, expectedException.getCause().getClass());
		assertEquals("String to convert to anagram has invalid data", expectedException.getCause().getMessage());
	}
	
	@Test
	void isInputStringCorrect_returnTrue_StringOfSameLettersInDifferentCase() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String stringOfSameLettersinDifferentCase = "aAAaaaAAAAAaaA";
		Method isInputStringCorrect = getMethod("isInputStringCorrect");	
		Assertions.assertTrue((boolean) isInputStringCorrect.invoke(anagram, stringOfSameLettersinDifferentCase));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"abcd", "fghjk", "abcdabcd"})
	void isInputStringCorrect_returnTrue_StringOfDifferentLetters(String inputString) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method isInputStringCorrect = getMethod("isInputStringCorrect");	
		Assertions.assertTrue((boolean) isInputStringCorrect.invoke(anagram, inputString));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"abcd abcd abcd", "fghjk abcd", "abc1dab4cd a!!b!scd"})
	void isInputStringCorrect_returnTrue_StringLineOfSeveraltWords(String inputString) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method isInputStringCorrect = getMethod("isInputStringCorrect");	
		Assertions.assertTrue((boolean) isInputStringCorrect.invoke(anagram, inputString));
	}
	
	@Test
	void makeAnagram_makeAnagramFromLineField_correctInputSeveralWordsLine() {
		String inputString = "abcd asdf 1234 a1bcd efg!h 12a3b as@$%Ga";
		String expectedAnagram = "dcba fdsa 1234 d1cba hgf!e 12b3a aG@$%sa";
		anagram.setLine(inputString);
		anagram.makeAnagram();
		assertEquals(expectedAnagram, anagram.getAnagram());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"abcd", "AAAaaaAAaaaaaAa", "abCdQWety"})
	void makeAnagram_makeAnagramFromLineField_correctInputOneWordLine(String inputString) {
		anagram.setLine(inputString);
		anagram.makeAnagram();
		assertEquals(new StringBuilder(inputString).reverse().toString(), anagram.getAnagram());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"1234!@#$%", "1234 5436", "2314", "!@$%^ !$%%", "1214 !$%%", "12@$% 41%@1"})
	void makeAnagram_makeAnagramFromLineField_correctInputOneWordOnlySymbolsLine(String inputString) {
		anagram.setLine(inputString);
		anagram.makeAnagram();
		assertEquals(inputString, anagram.getAnagram());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"aaaa", "", " ", "   ", "s", "1", "AAAAAA", " asdas", "asdf   das"})
	void makeAnagram_setLineFieldAsEmptyString_incorrectInputLine(String inputString) {
		anagram.setLine(inputString);
		anagram.makeAnagram();
		Assertions.assertTrue(anagram.getAnagram().isEmpty());
	}
	
	@ParameterizedTest
	@NullSource
	void makeAnagram_setLineFieldAsEmptyString_NullInputLine(String inputString) {
		anagram.setLine(inputString);
		anagram.makeAnagram();
		Assertions.assertTrue(anagram.getAnagram().isEmpty());
	}
}
