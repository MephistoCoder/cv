package com.foxminded.bohdansharubin.universitycms.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseDTO {
	
	@Min(0)
	private int id;
	
	@NotNull
	@Pattern(regexp = "^[A-Z][a-zA-Z\\s]+")
	private String name = "";
	
	private String description;
	private List<Integer> teachers = new ArrayList<>();
}
