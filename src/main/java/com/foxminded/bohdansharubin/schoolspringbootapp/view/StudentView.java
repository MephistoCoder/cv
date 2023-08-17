package com.foxminded.bohdansharubin.schoolspringbootapp.view;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.foxminded.bohdansharubin.schoolspringbootapp.model.Student;

@Component
public class StudentView extends AbstractView<Student> {
	private static final String STRING_STUDENT_ID = "id";
	private static final String STRING_STUDENT_GROUP_ID = "groupId";
	private static final String STRING_STUDENT_FIRST_NAME = "first name";
	private static final String STRING_STUDENT_LAST_NAME = "last name";
	
	private int longestIdLength;
	private int longestGroupIdLength;
	private int longestFirstNameLength;
	private int longestLastNameLength;

	public void printInfo(List<Student> students) {
		students.sort(Comparator.comparingInt(Student::getId));
		StringBuilder studentsInfo = new StringBuilder();
		calculateLongestElements(students);
		studentsInfo.append(buildHeaderLine())
					.append(buildSeparatorLine(STRING_HORIZONTAL_SPLITTER, studentsInfo.length()))
					.append(STRING_DEFAULT_EOL);
		students.forEach(student -> studentsInfo.append(buildStudentInfoLine(student)));
		System.out.println(studentsInfo);
	}
	
	@Override
	protected String buildHeaderLine() {
		return new StringBuilder().append(elementSupplement(longestIdLength, STRING_STUDENT_ID))
								  .append(STRING_VERTICAL_SPLITTER)
								  .append(elementSupplement(longestGroupIdLength, STRING_STUDENT_GROUP_ID))
								  .append(STRING_VERTICAL_SPLITTER)
								  .append(elementSupplement(longestFirstNameLength, STRING_STUDENT_FIRST_NAME))
								  .append(STRING_VERTICAL_SPLITTER)
								  .append(elementSupplement(longestLastNameLength, STRING_STUDENT_LAST_NAME))
								  .append(STRING_DEFAULT_EOL)
								  .toString();
	}
	
	@Override
	protected void calculateLongestElements(List<Student> students) {
		longestIdLength = calculateLongestLength(STRING_STUDENT_ID, 
								   				 students.stream()
											   			 .map(student -> String.valueOf(student.getId()))
											   			 .collect(Collectors.toList())
								  );
		longestGroupIdLength = calculateLongestLength(STRING_STUDENT_GROUP_ID,
													  students.stream()
															  .map(student -> String.valueOf(student.getGroup()))
															  .collect(Collectors.toList())
									   );
		
		longestFirstNameLength =  calculateLongestLength(STRING_STUDENT_FIRST_NAME,
										   				 students.stream()
													  			 .map(Student::getFirstName)
																 .collect(Collectors.toList())
										  );
		longestLastNameLength =  calculateLongestLength(STRING_STUDENT_LAST_NAME,
										   				students.stream()
																.map(Student::getLastName)
																.collect(Collectors.toList())
										 );
	}
	
	private String buildStudentInfoLine(Student student) {
		return new StringBuilder().append(elementSupplement(longestIdLength, String.valueOf(student.getId())))
				                  .append(STRING_VERTICAL_SPLITTER)
					              .append(elementSupplement(longestGroupIdLength, String.valueOf(student.getGroup())))
					              .append(STRING_VERTICAL_SPLITTER)
					              .append(elementSupplement(longestFirstNameLength, student.getFirstName()))
					              .append(STRING_VERTICAL_SPLITTER)
					              .append(elementSupplement(longestLastNameLength, student.getLastName()))
							      .append(STRING_DEFAULT_EOL)
							      .toString();
	}

}
