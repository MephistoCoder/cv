package com.foxminded.bohdansharubin.schoolspringbootapp.view;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.foxminded.bohdansharubin.schoolspringbootapp.model.Course;

@Component
public class CourseView extends AbstractView<Course> {
	private static final String STRING_COURSE_ID = "id";
	private static final String STRING_COURSE_NAME = "name";
	private static final String STRING_COURSE_DESCRIPTION = "description";
	
	private int longestIdLength;
	private int longestNameLength;
	private int longestDescriptionLength;
	
	@Override
	public void printInfo(List<Course> courses) {
		StringBuilder coursesInfo = new StringBuilder();
		calculateLongestElements(courses);
		coursesInfo.append(buildHeaderLine())
				   .append(buildSeparatorLine(STRING_HORIZONTAL_SPLITTER, coursesInfo.length()))
				   .append(STRING_DEFAULT_EOL);
		courses.forEach(course -> coursesInfo.append(buildCourseInfoLine(course)));
		System.out.println(coursesInfo);
	}

	@Override
	protected String buildHeaderLine() {
		return new StringBuilder().append(elementSupplement(longestIdLength, STRING_COURSE_ID))
								  .append(STRING_VERTICAL_SPLITTER)
								  .append(elementSupplement(longestNameLength, STRING_COURSE_NAME))
								  .append(STRING_VERTICAL_SPLITTER)
								  .append(elementSupplement(longestDescriptionLength, STRING_COURSE_DESCRIPTION))
								  .append(STRING_DEFAULT_EOL)
								  .toString();
	}

	@Override
	protected void calculateLongestElements(List<Course> courses) {
		longestIdLength = calculateLongestLength(STRING_COURSE_ID, 
								   				 courses.stream()
											   			.map(course -> String.valueOf(course.getId()))
											   			.collect(Collectors.toList())
											      );
		longestNameLength = calculateLongestLength(STRING_COURSE_NAME,
									 			   courses.stream()
													  	  .map(Course::getName)
													  	  .collect(Collectors.toList())
												  );
		longestDescriptionLength = calculateLongestLength(STRING_COURSE_DESCRIPTION,
														  courses.stream()
																 .map(Course::getDescription)
																 .collect(Collectors.toList())
										   				 );
	}

	private String buildCourseInfoLine(Course course) {
		return new StringBuilder().append(elementSupplement(longestIdLength, String.valueOf(course.getId())))
				               	  .append(STRING_VERTICAL_SPLITTER)
					              .append(elementSupplement(longestNameLength, course.getName()))
					              .append(STRING_VERTICAL_SPLITTER)
					              .append(elementSupplement(longestDescriptionLength, course.getDescription()))
							      .append(STRING_DEFAULT_EOL)
							      .toString();
	}
	
}
