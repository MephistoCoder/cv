package com.foxminded.bohdansharubin.schoolspringbootapp.commands;

public enum UserCommands {
	ALL_STUDENTS("get all students"),
	ADD_STUDENT("add new student"),
	ADD_STUDENT_TO_COURSE("add student to course"),
	ADD_STUDENT_TO_GROUP("add student to group"),
	DELETE_STUDENT("delete student by student id"), 
	DELETE_STUDENT_FROM_COURSE("delete student from course"),
	ALL_GROUPS("get all groups"),
	GET_ALL_GROUPS_WITH_STUDENTS_COUNT("get all groups with less or equal students count"),
	ALL_COURSES("get all courses"),
	GET_STUDENTS_BY_COURSE_NAME("get students by course name"),
	QUIT_APP("quit the app");
	
	private String action;
	
	UserCommands(String action) {
		this.action = action;
	}
	
	public String getAction() {
		return this.action;
	}

}
