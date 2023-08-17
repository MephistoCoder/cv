package com.foxminded.bohdansharubin.universitycms.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "lessons")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column(name = "date_")
	@NonNull
	private LocalDate date;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "lesson_time_id")
	@NonNull
	private LessonTime lessonTime;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "course_id")
	@NonNull
	private Course course;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "audience_id")
	@NonNull
	private Audience audience;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "teacher_id")
	@NonNull
	private Teacher teacher;
	
	@Builder.Default
	@ManyToMany
	@JoinTable(name = "lessons_groups",
				joinColumns = @JoinColumn(name = "lesson_id"),
				inverseJoinColumns = @JoinColumn(name = "group_id"))
	private List<Group> groups = new ArrayList<>();
	
	public boolean isValid() {
		return id >= 0 && 
			!date.getDayOfWeek().equals(DayOfWeek.SUNDAY) &&
			course.isValid() &&
			teacher.isValid() &&
			lessonTime.isValid();
	}
}
