package com.foxminded.bohdansharubin.schoolspringbootapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
	
@Entity
@Table(name = "courses")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private int id;
	
	@Column(name = "course_name")
	private String name;
	
	@Column(name = "course_description")
	private String description;
	
	@ManyToMany(mappedBy = "courses",
				fetch = FetchType.EAGER)
	List<Student> students = new ArrayList<>();

	public Course(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public Course() {
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name);
	}

	@Override
	public boolean equals(Object otherObject) {
		if (this == otherObject) {
			return true;
		}
		if (!(otherObject instanceof Course)) {
			return false;
		}
		Course other = (Course) otherObject;
		return Objects.equals(description, other.description)
				&&
			   id == other.id
			    &&
			   Objects.equals(name, other.name);
	}
	
}
