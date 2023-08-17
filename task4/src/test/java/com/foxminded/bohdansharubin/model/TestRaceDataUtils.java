package com.foxminded.bohdansharubin.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.foxminded.bohdansharubin.utils.FilesConfig;
import com.foxminded.bohdansharubin.utils.RaceDataUtils;

class TestRaceDataUtils {
	private static final String TEST_ABBREVIATION_FILE_PATH = "src/test/resources/testAbbreviations.txt";
	private static final String TEST_START_LAP_TIME_FILE_PATH = "src/test/resources/testStart.log";
	private static final String TEST_END_LAP_TIME_FILE_PATH = "src/test/resources/testEnd.log";
	private static FilesConfig filesConfig = new FilesConfig(TEST_ABBREVIATION_FILE_PATH,
															 TEST_START_LAP_TIME_FILE_PATH,
															 TEST_END_LAP_TIME_FILE_PATH);
	
	private static Stream<String> provideFilesNamesWhichExist() {
		return Stream.of(filesConfig.getAbbreviationsFilePath(),
						 filesConfig.getStartLapTimeFilePath(),
						 filesConfig.getEndLapTimeFilePath()
						 );
	}
	
	@ParameterizedTest
	@MethodSource("provideFilesNamesWhichExist")
	void readRaceDataFromFile_returnedListIsNotEmpty_fileExistAndReadable(String filePath) {
		Assertions.assertFalse(RaceDataUtils.fileToLines(filePath).isEmpty());
	}
	
	@Test
	void readRaceDataFromFile_returnedListElementsEqualsExpectedLines_testAbbreviationsfileExist() {
		List<String> actualListOfLines = RaceDataUtils.fileToLines(filesConfig.getAbbreviationsFilePath());
		Assertions.assertEquals("DRR_Daniel Ricciardo_RED BULL RACING TAG HEUER", actualListOfLines.get(0));
		Assertions.assertEquals("SVF_Sebastian Vettel_FERRARI", actualListOfLines.get(1));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"Start.log", "start.txt", "aBbreviations.txt"})
	void readRaceDataFromFile_returnedListIsEmpty_fileNotExist(String filePath) {
		Assertions.assertTrue(RaceDataUtils.fileToLines(filePath).isEmpty());
	}
	
	@Test
	void parseDateTimeFromString_returnLocalDateTime_correctString() {
		String stringLocalDateTime = "2022-09-16_15:23:12.123";
		LocalDateTime expectedLocalDateTime = LocalDateTime.parse(stringLocalDateTime,
																  DateTimeFormatter.ofPattern(RaceDataUtils.STRING_DATE_AND_TIME_PATTERN)
																  );
		Assertions.assertEquals(expectedLocalDateTime, RaceDataUtils.parseDateTimeFromString(stringLocalDateTime));
	}
	
	
	@ParameterizedTest
	@MethodSource("provideFilesNamesWhichExist")
	void validateFile_returnTrue_fileExistAndReadable(String fileName) {
		Assertions.assertTrue(RaceDataUtils.validateFile(fileName));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"Start.txt", "end.txt", "START.log", "abbreviations.log"})
	void validateFile_returnFalse_fileNotExist(String fileName) {
		Assertions.assertFalse(RaceDataUtils.validateFile(fileName));
	}
	
	@Test
	void validateParsedDataFromFile_returnTrue_validFileDataFromAbbreviationsFile() {
		List<String> testingData = new ArrayList<>();
		testingData.add("DRR_Daniel Ricciardo_RED BULL RACING TAG HEUER");
		testingData.add("FAM_Fernando Alonso_MCLAREN RENAULT");
		Assertions.assertTrue(RaceDataUtils.validateParsedDataFromFile(testingData,
							  RaceDataUtils.STRING_ABBREVIATION_DATA_PATTERN)
							 );
	}
	
	@Test
	void validateParsedDataFromFile_returnFalse_invalidFileDataFromAbbreviationsFile() {
		List<String> testingData = new ArrayList<>();
		testingData.add("1DRR_Daniel Ricciardo_RED BULL RACING TAG HEUER");
		testingData.add("AFAM_Fernando Alonso_MCLAREN RENAULT");
		Assertions.assertFalse(RaceDataUtils.validateParsedDataFromFile(testingData,
							  RaceDataUtils.STRING_ABBREVIATION_DATA_PATTERN)
							 );
	}
	
	@Test
	void validateParsedDataFromFile_returnTrue_validFileDataFromStartOrEndFile() {
		List<String> testingData = new ArrayList<>();
		testingData.add("SVF2018-05-24_12:02:58.917");
		testingData.add("BHS2018-05-24_12:16:05.164");
		Assertions.assertTrue(RaceDataUtils.validateParsedDataFromFile(testingData,
							  RaceDataUtils.STRING_LAP_DATA_PATTERN)
							 );
	}
	
	@Test
	void validateParsedDataFromFile_returnFalse_invalidFileDataFromStartOrEndFile() {
		List<String> testingData = new ArrayList<>();
		testingData.add("SVF2018-05-24_12:02:58.917.1");
		testingData.add("BHSA2018-05-24_12:16:05.164");
		Assertions.assertFalse(RaceDataUtils.validateParsedDataFromFile(testingData,
							  RaceDataUtils.STRING_LAP_DATA_PATTERN)
							 );
	}
	
}
