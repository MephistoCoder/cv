package com.foxminded.bohdansharubin.schoolspringbootapp.objectsgenerators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.foxminded.bohdansharubin.schoolspringbootapp.utils.ValidatorUtils.NAME_DEFAULT_PATTERN;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Student;
import com.foxminded.bohdansharubin.schoolspringbootapp.utils.FileUtils;

@Component
public class StudentsGenerator extends ObjectGenerator<Student> {
	private static final String DATA_DEFAULT_PATH = "src/main/resources/students_data_for_generator.txt";
	private static final String STRING_DEFAULT_SPLITTER = " ";
	private static final int DEFAULT_STUDENTS_TO_GENERATE = 200;
	private static final byte DEFAULT_FIRST_NAME_POSITION = 0;
	private static final byte DEFAULT_LAST_NAME_POSITION = 1;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private List<String> studentsFirstNamesList = new ArrayList<>();
	private List<String> studentsLastNamesList = new ArrayList<>();
	
	public StudentsGenerator() {
		super(DATA_DEFAULT_PATH, DEFAULT_STUDENTS_TO_GENERATE);
	}
	
	public StudentsGenerator(String dataFilePath) {
		super(dataFilePath);
	}

	@Override
	public List<Student> generateObjects() {
		getDataFromFile(getDataFilePath());
		if( getCountToGenerate() <= 0) {
			logger.warn("Count of students to generate less or equals 0");
			return getObjectsList();
		}
		if(!validateNamesLists()) {
			logger.warn("Lists of names for generating students empty or don't matches pattern {}",
							NAME_DEFAULT_PATTERN);
			return getObjectsList();
		}
		IntStream.range(0, getCountToGenerate())
				 .forEach(i -> getObjectsList().add(new Student(getRandomFirstName(), getRandomLastName())));
		logger.info("Students generated successfully");
		return getObjectsList();
	}
	
	public void getDataFromFile(String dataFilePath) {
		List<String> dataFromFile = FileUtils.fileToLines(dataFilePath);
		if(dataFromFile.isEmpty()) {
			logger.warn("Can't get names from file to generate");
			return;
		}
		studentsFirstNamesList = parseLineToStringList(dataFromFile.get(DEFAULT_FIRST_NAME_POSITION)); 
		studentsLastNamesList = parseLineToStringList(dataFromFile.get(DEFAULT_LAST_NAME_POSITION)); 
	}
	
	public boolean validateNamesLists() {
		return 	!studentsFirstNamesList.isEmpty()
				 &&
				!studentsLastNamesList.isEmpty()
				 &&
				studentsFirstNamesList.stream()
									  .allMatch(firstName -> firstName.matches(NAME_DEFAULT_PATTERN))
				 &&
				studentsLastNamesList.stream()
									 .allMatch(lastName -> lastName.matches(NAME_DEFAULT_PATTERN));
	}
	
	private String getRandomFirstName() {
		return studentsFirstNamesList.get(getRandomNumber(studentsFirstNamesList.size()));
	}
	
	private String getRandomLastName() {
		return studentsLastNamesList.get(getRandomNumber(studentsLastNamesList.size()));
	}
	
	private List<String> parseLineToStringList(String line) {
		return Arrays.asList(line.split(STRING_DEFAULT_SPLITTER));
	}
	
	public final List<String> getStudentsFirstNamesList() {
		return studentsFirstNamesList;
	}

	public final List<String> getStudentsLastNamesList() {
		return studentsLastNamesList;
	}

}
