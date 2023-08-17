package com.foxminded.bohdansharubin.schoolspringbootapp.objectsgenerators;

import java.util.List;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.foxminded.bohdansharubin.schoolspringbootapp.model.Course;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Student;

@Component
public class CourseStudentRelationsGenerator extends RelationsGenerator {
	private int minCoursesForStudent = 1;
	private int maxCoursesForStudent = 3;
	private List<Course> courses;
	private List<Student> students;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public CourseStudentRelationsGenerator() {
	}

	@Override
	public boolean validateGenerationParameters() {
		return courses != null
				&&
			   students != null
			    &&
			   !courses.isEmpty()
			    &&
			   !students.isEmpty()
				&&
			   maxCoursesForStudent > 0
			    &&
			   maxCoursesForStudent > minCoursesForStudent
			    &&
			   minCoursesForStudent < courses.size()
			    &&
			   maxCoursesForStudent <= courses.size();
	}

	@Override
	public void generateDependencies() {
		if (!validateGenerationParameters()) {
			logger.warn("Dependencies between courses and students don't generate, incorrect parameters");
			return;
		}
		IntStream.range(0, students.size())
				 .forEach(i -> addStudentToCourses(i));
		logger.info("Dependencies between courses and students generate successfully");
	}

	public final int getMinCoursesForStudent() {
		return minCoursesForStudent;
	}

	public final void setMinCoursesForStudent(int minCoursesForStudent) {
		this.minCoursesForStudent = minCoursesForStudent;
	}

	public final int getMaxCoursesForStudent() {
		return maxCoursesForStudent;
	}

	public final void setMaxCoursesForStudent(int maxCoursesForStudent) {
		this.maxCoursesForStudent = maxCoursesForStudent;
	}

	public final void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public final void setStudents(List<Student> students) {
		this.students = students;
	}
	
	private void addStudentToCourses(int studentId) {
		getRandomInts(getRandomInRange(minCoursesForStudent, maxCoursesForStudent),
			            0,
			            courses.size())
				.forEach(i -> students.get(studentId)
						    		  .addStudentToCourse(courses.get(i)));
		logger.debug("Student with id = {} was added to courses", studentId);
	}
}
