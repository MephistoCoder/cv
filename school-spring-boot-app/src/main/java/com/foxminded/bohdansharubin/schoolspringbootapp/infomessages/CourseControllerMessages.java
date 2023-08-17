package com.foxminded.bohdansharubin.schoolspringbootapp.infomessages;

public enum CourseControllerMessages {
	ENTER_ID_MESSAGE("Enter course id : "),
	ENTER_NAME_MESSAGE("Enter course name: "),
	INVALID_NAME_INPUT_MESSAGE("Invalid course name. Please try again"),
	INVALID_ID_INPUT_MESSAGE("Invalid course id. Id must be greater than 0. Please try again"),
	UNABLE_TO_ADD_STUDENT_TO_COURSE_MESSAGE("Unable to add student to course"),
	UNABLE_TO_FIND_COURSE_BY_ID_MESSAGE("Unable to find course by id"),
	SUCCESSFULLY_DELETED_FROM_COURSE_MESSAGE("Student successfully deleted from course"),
	SUCCESSFULLY_ADDED_TO_COURSE_MESSAGE("Student successfully added to course");
	
	CourseControllerMessages(String message) {
		this.message = message;
	}
	
	private String message;
	
	public String getMessage() {
		return message;
	}
}
