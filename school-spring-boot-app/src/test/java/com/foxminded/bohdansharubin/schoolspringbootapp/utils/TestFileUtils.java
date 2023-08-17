package com.foxminded.bohdansharubin.schoolspringbootapp.utils;

import static com.foxminded.bohdansharubin.schoolspringbootapp.utils.FileUtils.fileToLines;
import static com.foxminded.bohdansharubin.schoolspringbootapp.utils.FileUtils.validateFile;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class TestFileUtils {
	private static final String DEFAULT_TEST_FILE_PATH = "src/test/resources/testFile.txt";
	
	@Test
	void validateFile_returnTrue_fileExist() {
		Assertions.assertTrue(validateFile(DEFAULT_TEST_FILE_PATH));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"test1.txt", "TestFile.txt", "testFile.log", ""})
	void validateFile_returnFalse_fileNotExist(String filePath) {
		Assertions.assertFalse(validateFile(filePath));
	}
	
	@ParameterizedTest
	@NullSource
	void validateFile_returnFalse_nullFilePath(String filePath) {
		Assertions.assertFalse(validateFile(filePath));
	}
	
	@Test
	void fileToLines_returnListWithData_fileExist() {
		List<String> expectedList = new ArrayList<>();
		expectedList.add("Test");
		expectedList.add("Testing");
		expectedList.add("Tester");
		Assertions.assertEquals(expectedList, fileToLines(DEFAULT_TEST_FILE_PATH));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"test1.txt", "TestFile.txt", "testFile.log", ""})
	void fileToLines_returnEmptyList_fileNotExist(String filePath) {
		Assertions.assertTrue(fileToLines(filePath).isEmpty());
	}
	
}
