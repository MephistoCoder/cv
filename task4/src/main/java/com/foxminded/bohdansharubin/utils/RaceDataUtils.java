package com.foxminded.bohdansharubin.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RaceDataUtils {
	public static final String STRING_DEFAULT_UNDERSCORE = "_";
	public static final String STRING_ABBREVIATION_DATA_PATTERN = "\\w{3}_\\w+\\s?\\w+_\\D+";
	public static final String STRING_LAP_DATA_PATTERN = "\\w{3}\\d{4}-\\d{2}-\\d{2}_\\d{2}:\\d{2}:\\d{2}.\\d+";
	public static final String STRING_DATE_AND_TIME_PATTERN = "yyyy-MM-dd_HH:mm:ss.SSS";
	public static final String STRING_DEFAULT_SPLITTER = " ";
 	
	private RaceDataUtils() {
		
	}
	
	public static List<String> fileToLines(String filePath) {
		List<String> lines = new ArrayList<>();
		if(!validateFile(filePath)) {
			return lines;
		}
	    Path path = Paths.get(filePath);
	    try {
	        lines = Files.readAllLines(path);
	        
	    } catch (IOException ignored) {
	    }
	    return lines;
	}
	
	public static LocalDateTime parseDateTimeFromString(String dateTime) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(STRING_DATE_AND_TIME_PATTERN);
	    return LocalDateTime.parse(dateTime, dateTimeFormatter);
	}
	
	public static long getDifferenceWithToDates(LocalDateTime firstDateTime, LocalDateTime secondDateTime) {
		return Duration.between(firstDateTime, secondDateTime).toMillis();
	}
	
	public static boolean validateFile(String filePath) {
		Path path = Paths.get(filePath);
		return path.toFile().exists() && path.toFile().canRead(); 
	}
	
	public static boolean validateParsedDataFromFile(List<String> parsedFileInfo, String matchPattern) {
		return !parsedFileInfo.isEmpty() 
				&& parsedFileInfo.stream().
								 allMatch(lineFromFile -> lineFromFile.matches(matchPattern));
	}

}
