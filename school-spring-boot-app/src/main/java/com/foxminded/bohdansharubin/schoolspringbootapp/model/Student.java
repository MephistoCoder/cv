package com.foxminded.bohdansharubin.schoolspringbootapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private int id;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE},
				fetch = FetchType.EAGER)
	@JoinColumn(name = "group_id")
	private Group group;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
	@JoinTable(name = "courses_students",
			   joinColumns = @JoinColumn(name = "student_id"),
			   inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> courses = new ArrayList<>();
	
	public Student(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Student() {
	}
	
	public void addStudentToCourse(Course course) {
		courses.add(course);
	}
	
	public void deleteStudentFromCourse(Course course) {
		courses.remove(course);
	}

	public int getId() {
		return id;
	}
	
	public Group getGroup() {
		return group;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", group=" + group + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", courses=" + courses + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, group, id, lastName);
	}

	@Override
	public boolean equals(Object otherObject) {
		if (this == otherObject) {
			return true;
		}
		if (!(otherObject instanceof Student)) {
			return false;
		}
		Student other = (Student) otherObject;
		return Objects.equals(firstName, other.firstName)
				&&
			   Objects.equals(group, other.group)
			    &&
			   id == other.id
				&&
			   Objects.equals(lastName, other.lastName);
	}
		
}
