package com.foxminded.bohdansharubin.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.IntStream;

import com.foxminded.bohdansharubin.model.Racer;

public class RaceView {
	private static final String STRING_DEFAULT_SPLITTER = " ";
	private static final String STRING_DEFAULT_EOL = "\n";
	private static final String STRING_VERTICAL_SPLITTER = "|";
	private static final String STRING_DEFAULT_DOT = ".";
	private static final String STRING_HORIZONTAL_SEPARATOR = "-";
	private static final String DEFAULT_TIME_FORMAT = "mm:ss.SSS";
	private static final int HORIZONTAL_SEPARATOR_LINE_POSITION = 16;
	private static final int DEFAULT_TAB_VALUE = 2;
	private static final String LONGEST_FULL_NAME_LENGTH = "longestFullNameLength";
	private static final String LONGEST_TEAM_NAME_LENGTH = "longestTeamNameLength";
	private static final String DEFAULT_WARNING_MESSAGE = "It is not possible to print information about the result of the F1 qualification."
															+ " Something wrong with files or file data";
	
	public void printRaceInfo(List<Racer> racers) {
		Map<String, Integer> elementsMaxLength =  findElementsMaxLength(racers);
		StringBuilder raceInfo = new StringBuilder();
		IntStream.range(0, racers.size())
				 .forEach(i -> {
								 if(i + 1 == HORIZONTAL_SEPARATOR_LINE_POSITION) {
										raceInfo.append(buildSeparatorLine(STRING_HORIZONTAL_SEPARATOR, sizeOfHorizontalSeparatorLine(elementsMaxLength)))
												.append(STRING_DEFAULT_EOL);
									}
									raceInfo.append(buildRacerInfoLine(racers.get(i), i + 1, elementsMaxLength))
											.append(STRING_DEFAULT_EOL);
							   }
		);
		System.out.println(raceInfo);	
	}
	
	public void printWarningMessage() {
		System.out.println(DEFAULT_WARNING_MESSAGE);
	}
	
	private String buildSeparatorLine(String separator, int length) {
		StringBuilder separatorLine = new StringBuilder();
		for(int i = 0; i < length; i++) {
			separatorLine.append(separator);
		}
		return separatorLine.toString();
	}
	
	private Map<String, Integer> findElementsMaxLength(List<Racer> racers) {
		Map<String, Integer> elementsMaxLength = new HashMap<>();
		String longestFullName = racers.stream()
				                       .map(Racer::getFullName)
				                       .max(Comparator.comparingInt(String::length))
				                       .orElseThrow(RuntimeException::new);
		String longestTeamName =  racers.stream()
                .map(Racer::getRacingTeam)
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(RuntimeException::new);
		elementsMaxLength.put(LONGEST_FULL_NAME_LENGTH, longestFullName.length());
		elementsMaxLength.put(LONGEST_TEAM_NAME_LENGTH, longestTeamName.length());
		return elementsMaxLength;
	}
	
	private String convertLongTimeToStringFormat(long timeInMilliseconds) {
		Date date = new Date(timeInMilliseconds);
		DateFormat formatter = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		return formatter.format(date);
	}
	
	private String buildRacerInfoLine(Racer racer, int position, Map<String, Integer> elementsMaxLength) {
		StringBuilder racerInfoLine = new StringBuilder();
		racerInfoLine.append(position)
					 .append(STRING_DEFAULT_DOT)
					 .append(racer.getFullName())
					 .append(buildSeparatorLine(STRING_DEFAULT_SPLITTER,
							 					elementsMaxLength.get(LONGEST_FULL_NAME_LENGTH)
							 												- racer.getFullName().length()
							 												- String.valueOf(position).length()
							 												+ DEFAULT_TAB_VALUE)
							 )
					 .append(STRING_VERTICAL_SPLITTER)
					 .append(STRING_DEFAULT_SPLITTER)
					 .append(racer.getRacingTeam())
					 .append(buildSeparatorLine(STRING_DEFAULT_SPLITTER,
							 					elementsMaxLength.get(LONGEST_TEAM_NAME_LENGTH)
							 												- racer.getRacingTeam().length()
							 												+ DEFAULT_TAB_VALUE)
							)
					 .append(STRING_VERTICAL_SPLITTER)
					 .append(STRING_DEFAULT_SPLITTER)
					 .append(convertLongTimeToStringFormat(racer.getBestLap())
					 );
		return racerInfoLine.toString();
	}
	
	private int sizeOfHorizontalSeparatorLine(Map<String, Integer> elementsMaxLength) {
		return elementsMaxLength.get(LONGEST_FULL_NAME_LENGTH)
				+ elementsMaxLength.get(LONGEST_TEAM_NAME_LENGTH)
				+ DEFAULT_TAB_VALUE * 2
				+ DEFAULT_TIME_FORMAT.length()
				+ STRING_VERTICAL_SPLITTER.length() * 2
				+ STRING_DEFAULT_SPLITTER.length() * 2
				+ STRING_DEFAULT_DOT.length();
	}
}
