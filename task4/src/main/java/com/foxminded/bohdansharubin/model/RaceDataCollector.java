package com.foxminded.bohdansharubin.model;

import static com.foxminded.bohdansharubin.utils.RaceDataUtils.STRING_ABBREVIATION_DATA_PATTERN;
import static com.foxminded.bohdansharubin.utils.RaceDataUtils.STRING_DEFAULT_UNDERSCORE;
import static com.foxminded.bohdansharubin.utils.RaceDataUtils.STRING_LAP_DATA_PATTERN;
import static com.foxminded.bohdansharubin.utils.RaceDataUtils.STRING_DEFAULT_SPLITTER;
import static com.foxminded.bohdansharubin.utils.RaceDataUtils.parseDateTimeFromString;
import static com.foxminded.bohdansharubin.utils.RaceDataUtils.fileToLines;
import static com.foxminded.bohdansharubin.utils.RaceDataUtils.validateParsedDataFromFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.foxminded.bohdansharubin.utils.FilesConfig;
import com.foxminded.bohdansharubin.utils.RaceDataUtils;

public class RaceDataCollector {
	private List<Racer> racers = new ArrayList<>();
	private FilesConfig filesConfig = new FilesConfig();
	private static final byte ABBREVIATION_POSITION = 0;
	private static final byte FULL_NAME_POSITION = 1;
	private static final byte RACING_TEAM_POSITION = 2;
	private static final byte DATE_TIME_OF_LAP_POSITION = 1;
	private static final byte ABBREVIATION_DEFAULT_LENGTH = 3;
	
	
	
	public void calculateRaceData() {
		if(createRacersFromAbbreviationsFileInfo() && calculateLapDurationForEachRacer()) {
			Collections.sort(racers);
		}
	}
	
	private boolean createRacersFromAbbreviationsFileInfo() {
		List<String> racersInfo = fileToLines(filesConfig.getAbbreviationsFilePath());
		if(!validateParsedDataFromFile(racersInfo, STRING_ABBREVIATION_DATA_PATTERN)) {
			return false;
		}
		racersInfo.forEach(racerInfoLine -> {
											String[] racerInfo = racerInfoLine.split(STRING_DEFAULT_UNDERSCORE);
											racers.add(new Racer(racerInfo[ABBREVIATION_POSITION],
																 racerInfo[FULL_NAME_POSITION],
																 racerInfo[RACING_TEAM_POSITION]
																)
													);
											}
						   );
		return true;
	}
	
	private boolean calculateLapDurationForEachRacer() {
		Map<String, String> mapOfLapTime = createMapOfLapTimeInfo();
		if(mapOfLapTime.isEmpty()) {
			return false;
		}
		racers.forEach(racer -> {
								 String[] lapInfo = mapOfLapTime.get(racer.getAbbreviation()).split(STRING_DEFAULT_SPLITTER);
								 racer.calculateBestLapTime(parseDateTimeFromString(lapInfo[ABBREVIATION_POSITION]),
										 					parseDateTimeFromString(lapInfo[DATE_TIME_OF_LAP_POSITION])
								     	 					);
								 }
					  );
		return true;
	}
	
	private Map<String, String> createMapOfLapTimeInfo() {
		Map<String, String> mapOfLapTime = new HashMap<>();
		List<String> startLapInfo = fileToLines(filesConfig.getStartLapTimeFilePath());
		List<String> endLapInfo = fileToLines(filesConfig.getEndLapTimeFilePath());
		if(!validateParsedDataFromFile(startLapInfo, STRING_LAP_DATA_PATTERN)
		   ||
		   !RaceDataUtils.validateParsedDataFromFile(endLapInfo, STRING_LAP_DATA_PATTERN)) {
			return mapOfLapTime;
		}
		startLapInfo.forEach(lapInfo -> mapOfLapTime.put(lapInfo.substring(0,  ABBREVIATION_DEFAULT_LENGTH),
														 lapInfo.substring(ABBREVIATION_DEFAULT_LENGTH))
							);
		endLapInfo.forEach(lapInfo -> mapOfLapTime.computeIfPresent(lapInfo.substring(0,  ABBREVIATION_DEFAULT_LENGTH),
												          			(key, value) -> value.concat(STRING_DEFAULT_SPLITTER
												          										 + lapInfo.substring(ABBREVIATION_DEFAULT_LENGTH)))
						  );
		return mapOfLapTime;
	}

	public List<Racer> getRacers() {
		return racers.stream().collect(Collectors.toList());
	}
	
}
