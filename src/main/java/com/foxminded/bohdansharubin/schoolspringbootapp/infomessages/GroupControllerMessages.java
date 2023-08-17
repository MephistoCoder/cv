package com.foxminded.bohdansharubin.schoolspringbootapp.infomessages;

public enum GroupControllerMessages {
	ENTER_MAXIMUM_NUMBER_OF_STUDENTS_IN_GROUP_MESSAGE("Enter the maximum number of students in the group to search for: "),
	ENTER_GROUP_ID_FOR_ASSIGNING_STUDENT_MESSAGE("Enter the group id for assigning student or non-existent group id for skip: "),
	INVALID_NUMBER_OF_STUDENTS_INPUT_MESSAGE("Invalid number of students. Number of students must be greater than 0. Please try again"),
	INVALID_GROUP_ID_INPUT_MESSAGE("Invalid group id. Group id must be greater than 0. Please try again");
	
	GroupControllerMessages(String message) {
		this.message = message;
	}
	
	private String message;
	
	public String getMessage() {
		return message;
	}
}
