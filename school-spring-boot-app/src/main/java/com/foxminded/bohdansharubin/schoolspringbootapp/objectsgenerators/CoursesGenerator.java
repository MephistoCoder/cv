package com.foxminded.bohdansharubin.schoolspringbootapp.objectsgenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.foxminded.bohdansharubin.schoolspringbootapp.utils.ValidatorUtils.NAME_DEFAULT_PATTERN;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Course;
import com.foxminded.bohdansharubin.schoolspringbootapp.utils.FileUtils;

@Component
public class CoursesGenerator extends ObjectGenerator<Course> {
	private static final String DATA_DEFAULT_PATH = "src/main/resources/courses_data_for_generator.txt";
	private static final String DEFAULT_COURSE_DESCRIPTION = "course of main themes";
	private static final int DEFAULT_COURSES_TO_GENERATE = 10;
	private List<String> coursesNamesList = new ArrayList<>();
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public CoursesGenerator() {
		super(DATA_DEFAULT_PATH, DEFAULT_COURSES_TO_GENERATE);
	}

	public CoursesGenerator(String dataFilePath) {
		super(dataFilePath);
	}

	@Override
	public List<Course> generateObjects() {
		if(getCountToGenerate() <= 0) {
			logger.warn("Count of generate courses is less or equal 0");
			return getObjectsList();
		}
		coursesNamesList = FileUtils.fileToLines(getDataFilePath());
		if(getCountToGenerate() > coursesNamesList.size() 
			||
		   !validateCoursesNamesList()) {
			logger.warn("List with courses names for generating empty or data is'not match default pattern {}",
							NAME_DEFAULT_PATTERN);
			return getObjectsList();
		}
		IntStream.range(0, getCountToGenerate())
				 .forEach(i -> getObjectsList().add(new Course(coursesNamesList.get(i), DEFAULT_COURSE_DESCRIPTION)));
		logger.info("Courses generate successfully");
		return getObjectsList();
	}
	
	public boolean validateCoursesNamesList() {
		return !coursesNamesList.isEmpty() 
				&&
			   coursesNamesList.stream()
			   				   .allMatch(name -> name.matches(NAME_DEFAULT_PATTERN));
	}

}
