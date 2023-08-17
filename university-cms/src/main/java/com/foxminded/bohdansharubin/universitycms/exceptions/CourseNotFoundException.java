package com.foxminded.bohdansharubin.universitycms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Course Not Found")
public class CourseNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3679070884934115601L;

	public CourseNotFoundException(int id) {
		super("Course with id = " + id + " not found");
	}

}
