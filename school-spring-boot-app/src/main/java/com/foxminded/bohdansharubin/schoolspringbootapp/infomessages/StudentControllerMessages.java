package com.foxminded.bohdansharubin.schoolspringbootapp.infomessages;

public enum StudentControllerMessages {
	ENTER_ID_MESSAGE("Enter student id : "),
	 ENTER_FIRST_NAME_MESSAGE("Enter student firstname: "),
	 ENTER_LAST_NAME_MESSAGE("Enter student lastname: "),
	 ENTER_GROUP_ID_MESSAGE("Enter group id: "),
	 QUESTION_TO_ADD_STUDENT_TO_GROUP_MESSAGE("Do you want to assign student to group? (yes) | (no)"),
	 INVALID_ANSWER_FROM_USER_MESSAGE("Wrong answer. The answer should be (yes) | (no)"),
	 INVALID_NAME_INPUT_MESSAGE("Invalid student name. Please try again"),
	 INVALID_ID_INPUT_MESSAGE("Invalid student id. Id must be greater than 0. Please try again"), 
	 INVALID_GROUP_ID_INPUT_MESSAGE("Invalid group id. Id must be greater than 0. Please try again"), 
	 UNABLE_TO_FIND_STUDENT_BY_ID_MESSAGE("Can't find student by id");
	
	StudentControllerMessages(String message) {
		this.message = message;
	}
	
	private String message;
	
	public String getMessage() {
		return message;
	}
}
