package com.foxminded.bohdansharubin.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.foxminded.bohdansharubin.utils.FilesConfig;

class TestRaceDataCollector {
	private static RaceDataCollector raceDataCollector = new RaceDataCollector();
	private static final String TEST_ABBREVIATION_FILE_PATH = "src/test/resources/testAbbreviations.txt";
	private static final String TEST_START_LAP_TIME_FILE_PATH = "src/test/resources/testStart.log";
	private static final String TEST_END_LAP_TIME_FILE_PATH = "src/test/resources/testEnd.log";
	private static FilesConfig filesConfig = new FilesConfig(TEST_ABBREVIATION_FILE_PATH,
															 TEST_START_LAP_TIME_FILE_PATH,
															 TEST_END_LAP_TIME_FILE_PATH);
	@BeforeAll
	static void setConfigFileToRaceDataCollector() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field filesConfigField = RaceDataCollector.class.getDeclaredField("filesConfig");
		filesConfigField.setAccessible(true);
		filesConfigField.set(raceDataCollector, filesConfig);
	}
	
	private void setBestLapTime(Racer racer, int bestLapTime) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field bestLapInMilliSecondsField = Racer.class.getDeclaredField("bestLapInMilliSeconds");
		bestLapInMilliSecondsField.setAccessible(true);
		bestLapInMilliSecondsField.set(racer, bestLapTime);
	}
	
	@Test
	void calculateRaceData_listOfRacersEqualsExpectedList_validData() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		List<Racer> expectedRacersList = new ArrayList<>();
		expectedRacersList.add(new Racer("SVF", "Sebastian Vettel", "FERRARI"));
		expectedRacersList.add(new Racer("DRR", "Daniel Ricciardo", "RED BULL RACING TAG HEUER"));
		setBestLapTime(expectedRacersList.get(0), 64415);
		setBestLapTime(expectedRacersList.get(1), 72013);
		raceDataCollector.calculateRaceData();
		Assertions.assertFalse(raceDataCollector.getRacers().isEmpty());
		Assertions.assertTrue(IntStream.range(0, expectedRacersList.size()).allMatch(i -> expectedRacersList.get(i).equals(raceDataCollector.getRacers().get(i))));
	}
}
