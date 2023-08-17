package com.foxminded.bohdansharubin.universitycms.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "teachers")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true )
@ToString(callSuper = true)
public class Teacher extends Person {
	
	@ToString.Exclude
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "teachers_groups",
				joinColumns = @JoinColumn(name = "teacher_id"),
				inverseJoinColumns = @JoinColumn(name = "group_id"))
	private List<Group> groups = new ArrayList<>();
	
	
	@ToString.Exclude
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "teachers_courses",
				joinColumns = @JoinColumn(name = "teacher_id"),
				inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> courses = new ArrayList<>();
	
	public boolean addCourse(Course course) {
		if(courses.contains(course)) {
			return false;
		}
		return courses.add(course);
	}
	
	public boolean removeCourse(Course course) {
		return courses.remove(course);
	}
	
}
