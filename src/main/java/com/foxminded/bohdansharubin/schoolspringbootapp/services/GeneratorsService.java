package com.foxminded.bohdansharubin.schoolspringbootapp.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.foxminded.bohdansharubin.schoolspringbootapp.dao.CourseDao;
import com.foxminded.bohdansharubin.schoolspringbootapp.dao.GroupDao;
import com.foxminded.bohdansharubin.schoolspringbootapp.dao.StudentDao;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Course;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Group;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Student;
import com.foxminded.bohdansharubin.schoolspringbootapp.objectsgenerators.CourseStudentRelationsGenerator;
import com.foxminded.bohdansharubin.schoolspringbootapp.objectsgenerators.CoursesGenerator;
import com.foxminded.bohdansharubin.schoolspringbootapp.objectsgenerators.GroupStudentRelationsGenerator;
import com.foxminded.bohdansharubin.schoolspringbootapp.objectsgenerators.GroupsGenerator;
import com.foxminded.bohdansharubin.schoolspringbootapp.objectsgenerators.StudentsGenerator;

@Service
@Order(1)
public class GeneratorsService implements ApplicationRunner {
	private GroupsGenerator groupsGenerator;
	private StudentsGenerator studentsGenerator;
	private CoursesGenerator coursesGenerator;
	private GroupStudentRelationsGenerator groupStudentRelationsGenerator;
	private CourseStudentRelationsGenerator courseStudentRelationsGenerator;
	private GroupDao groupDao;
	private StudentDao studentDao;
	private CourseDao courseDao;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public GeneratorsService(GroupsGenerator groupsGenerator,
							 StudentsGenerator studentsGenerator,
							 CoursesGenerator coursesGenerator,
							 GroupStudentRelationsGenerator groupStudentRelationsGenerator,
							 CourseStudentRelationsGenerator courseStudentRelationsGenerator,
							 GroupDao groupDao,
							 StudentDao studentDao,
							 CourseDao courseDao) {
		this.groupsGenerator = groupsGenerator;
		this.studentsGenerator = studentsGenerator;
		this.coursesGenerator = coursesGenerator;
		this.groupStudentRelationsGenerator = groupStudentRelationsGenerator;
		this.courseStudentRelationsGenerator = courseStudentRelationsGenerator;
		this.groupDao = groupDao;
		this.studentDao = studentDao;
		this.courseDao = courseDao;
	}
	
	private List<Group> generateGroups() {
		return groupsGenerator.generateObjects();
	}
	
	private List<Course> generateCourses() {
		return coursesGenerator.generateObjects();
	}
	
	private List<Student> generateStudents() {
		return studentsGenerator.generateObjects();
	}
	
	private void generateGroupStudentRelations(List<Group> groups, List<Student> students) {
		groupStudentRelationsGenerator.setGroups(groups);
		groupStudentRelationsGenerator.setStudents(students);
		groupStudentRelationsGenerator.generateDependencies();
	}
	
	private void generateCourseStudentRelations(List<Course> courses, List<Student> students) {
		courseStudentRelationsGenerator.setCourses(courses);
		courseStudentRelationsGenerator.setStudents(students);
		courseStudentRelationsGenerator.generateDependencies();
	}
	
	private boolean isDatabaseEmpty() {
		return groupDao.findAll().isEmpty()
				&&
			   studentDao.findAll().isEmpty()
				&&
			   courseDao.findAll().isEmpty();
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if(!isDatabaseEmpty()) {
			logger.info("Database is not empty");
			return;
		}
		logger.info("Generating groups");
		List<Group> groups = generateGroups();
		logger.debug("Generated groups {}", groups);
		logger.info("Adding generated groups to database");
		groups.forEach(groupDao::save);
		
		logger.info("Generating courses");
		List<Course> courses = generateCourses();
		logger.debug("Generated courses {}", courses);
		logger.info("Adding generated courses to database");
		courses.forEach(courseDao::save);
		
		logger.info("Generating students");
		List<Student> students = generateStudents();
		logger.debug("Generated students {}", students);
		logger.info("Generating relations beetween groups and students");
		generateGroupStudentRelations(groups, students);
		logger.info("Generating relations between courses and students");
		generateCourseStudentRelations(courses, students);
		logger.info("Adding generated students to database");
		students.forEach(studentDao::save);		
	}
	
}
