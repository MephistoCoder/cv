package com.foxminded.bohdansharubin.universitycms.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
	
	private static final String DEFAULT_NAME_PATTERN = "^[A-Z][a-zA-Z\\s]+";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column
	@NotNull
	@Pattern(regexp = "^[A-Z][a-zA-Z\\s]+")
	private String name;
	
	@Column
	private String description;
	
	@Builder.Default
	@ManyToMany(mappedBy = "courses")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Student> students = new ArrayList<>();
	
	@Builder.Default
	@ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Teacher> teachers = new ArrayList<>();
	
	public boolean isValid() {
		return id >= 0 &&
			name.matches(DEFAULT_NAME_PATTERN);
	}
	
	public boolean addTeacher(Teacher teacher) {
		if(teachers.contains(teacher)) {
			return false;
		}
		teacher.addCourse(this);
		return teachers.add(teacher);
	}
	
	public boolean removeTeacher(Teacher teacher) {
		if(!teachers.contains(teacher)) {
			return false;
		}
		teacher.removeCourse(this);
		return teachers.remove(teacher);
	}
	
	public boolean addStudent(Student student) {
		return students.add(student);
	}
	
	public boolean setTeachers(List<Teacher> newTeachers) {
		if(teachers.contains(null)) {
			teachers.clear();
		}
		teachers.forEach(teacher -> teacher.removeCourse(this));
		teachers = newTeachers;
		newTeachers.forEach(teacher -> teacher.addCourse(this));
		return true;
	}
	
}
