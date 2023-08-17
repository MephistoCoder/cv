package com.foxminded.bohdansharubin.schoolspringbootapp.services;

import static com.foxminded.bohdansharubin.schoolspringbootapp.infomessages.CourseControllerMessages.SUCCESSFULLY_ADDED_TO_COURSE_MESSAGE;
import static com.foxminded.bohdansharubin.schoolspringbootapp.infomessages.CourseControllerMessages.SUCCESSFULLY_DELETED_FROM_COURSE_MESSAGE;
import static com.foxminded.bohdansharubin.schoolspringbootapp.infomessages.CourseControllerMessages.UNABLE_TO_ADD_STUDENT_TO_COURSE_MESSAGE;
import static com.foxminded.bohdansharubin.schoolspringbootapp.infomessages.CourseControllerMessages.UNABLE_TO_FIND_COURSE_BY_ID_MESSAGE;
import static com.foxminded.bohdansharubin.schoolspringbootapp.infomessages.GroupControllerMessages.ENTER_GROUP_ID_FOR_ASSIGNING_STUDENT_MESSAGE;
import static com.foxminded.bohdansharubin.schoolspringbootapp.infomessages.GroupControllerMessages.INVALID_GROUP_ID_INPUT_MESSAGE;
import static com.foxminded.bohdansharubin.schoolspringbootapp.infomessages.StudentControllerMessages.*;
import static com.foxminded.bohdansharubin.schoolspringbootapp.utils.ValidatorUtils.NAME_DEFAULT_PATTERN;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.bohdansharubin.schoolspringbootapp.dao.StudentDao;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Course;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Group;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Student;
import com.foxminded.bohdansharubin.schoolspringbootapp.utils.UserInputUtils;
import com.foxminded.bohdansharubin.schoolspringbootapp.utils.ValidatorUtils;
import com.foxminded.bohdansharubin.schoolspringbootapp.view.StudentView;

@Service
@Transactional
public class StudentService {

	private GroupService groupService;
	private CourseService courseService;
	private StudentDao studentDao;
	private StudentView studentView;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public StudentService(GroupService groupService,
						  CourseService courseService,
						  StudentDao postgresStudentDao,
						  StudentView studentView) {
		this.groupService = groupService;
		this.courseService = courseService;
		this.studentDao = postgresStudentDao;
		this.studentView = studentView;	
	}
	
	public void printStudentsInfo(List<Student> students) {
		studentView.printInfo(students);
	}
	
	public void addStudent() {
		String firstName = getNameFromUser(ENTER_FIRST_NAME_MESSAGE.getMessage(), INVALID_NAME_INPUT_MESSAGE.getMessage());
		String lastName = getNameFromUser(ENTER_LAST_NAME_MESSAGE.getMessage(), INVALID_NAME_INPUT_MESSAGE.getMessage());
		Student student = new Student(firstName, lastName);	
		studentDao.save(student);
		logger.info("Student = {} was successfully added to database", student);
	}
	
	public void deleteStudent() {
		Student student = getStudentById();
		if(student == null) {
			logger.warn("Finding student = null");
			System.out.println(UNABLE_TO_FIND_STUDENT_BY_ID_MESSAGE.getMessage());
			return;
		}
		studentDao.deleteById(student.getId());
		logger.info("Student = {} was successfully deleted from database", student);
	}
	
	public List<Student> getStudentsByCourse(Course course) {
		if(course == null) {
			logger.warn("Unable to get students of course = null");
			return new ArrayList<Student>();
		}
		logger.debug("Getting all students by course name from course = {}", course);
		return course.getStudents();
	}
	
	public Student getStudentById() {
		int studentId = getIdFromUser(ENTER_ID_MESSAGE.getMessage(), INVALID_ID_INPUT_MESSAGE.getMessage());
		logger.debug("Student id from user = {}", studentId);
		try {
			return studentDao.getReferenceById(studentId);			
		} catch(EntityNotFoundException e) {
			logger.warn("Student with id = {} not found", studentId);
			return null;
		}
	}

	public List<Student> getAllStudents() {
		return studentDao.findAll();
	}
	
	public void addStudentToGroup() {
		Student student = getStudentById();
		if(student == null) {
			logger.warn("Unable to assign student to group, because student = null");
			System.out.println("Student doesn't assign to group");
			return;
		}
		List<Group> availableGroups = groupService.getAllGroups();
		if(student.getGroup() != null) {			
			availableGroups.remove(student.getGroup());
			groupService.printGroupsInfo(availableGroups);
		}
		int userInputId = getIdFromUser(ENTER_GROUP_ID_FOR_ASSIGNING_STUDENT_MESSAGE.getMessage(),
				   						INVALID_GROUP_ID_INPUT_MESSAGE.getMessage());
		logger.debug("Group id from user = {}", userInputId);
		Group newGroup = groupService.getById(userInputId);
		if(newGroup == null) {
			logger.warn("Group = null");
			System.out.println("Student doesn't assign to group");
			return;
		}
		student.setGroup(newGroup);
		logger.info("Student successfully assigned to group = {}", newGroup);
	}
	
	public void addStudentToCourse() {
		Student student = getStudentById();
		if(student == null) {
			logger.warn("Unable to assign student to course, because student = null");
			System.out.println(UNABLE_TO_ADD_STUDENT_TO_COURSE_MESSAGE.getMessage());
			return;
		}
		List<Course> availableCourses = courseService.getAllCourses();	
		availableCourses.removeAll(student.getCourses());		
		courseService.printCoursesInfo(availableCourses);
		Course course = courseService.findCourseById();
		if(course == null 
				||
		   !availableCourses.contains(course)) {
			logger.warn("Unable to find course or course doesn't contain in available courses = {}", availableCourses);
			System.out.println(UNABLE_TO_FIND_COURSE_BY_ID_MESSAGE.getMessage());
		} else {
			student.addStudentToCourse(course);
			logger.info("Student was successfully added to course");
			System.out.println(SUCCESSFULLY_ADDED_TO_COURSE_MESSAGE.getMessage());
		}
	}
	
	public void deleteStudentFromCourse() {
		Student student = getStudentById();
		if(student == null) {
			logger.warn("Unable to add student to course, because student = null");
			System.out.println(UNABLE_TO_ADD_STUDENT_TO_COURSE_MESSAGE.getMessage());
			return;
		}
		List<Course> studentCourses = student.getCourses();
		courseService.printCoursesInfo(studentCourses);
		Course course = courseService.findCourseById();
		if(course == null
				||
		   !studentCourses.contains(course)) {
			logger.warn("Unable to find course = null or course doesn't relate to student");
			System.out.println(UNABLE_TO_FIND_COURSE_BY_ID_MESSAGE.getMessage());
		} else {
			student.deleteStudentFromCourse(course);
			logger.info("Student was successfully deleted from course");
			System.out.println(SUCCESSFULLY_DELETED_FROM_COURSE_MESSAGE.getMessage());
		}
	}
	
	private String getNameFromUser(String infoMessage, String warningMessage) {
		String nameFromUser;
		while(true) {
			System.out.println(infoMessage);
			nameFromUser = UserInputUtils.getStringInputFromUser();
			logger.debug("User enter name = {}", nameFromUser);
			if(ValidatorUtils.isStringMatchesPattern(nameFromUser.toLowerCase(), NAME_DEFAULT_PATTERN)) {
				return nameFromUser;
			} else {				
				System.out.println(warningMessage);
			}
		}
	}
	
	private int getIdFromUser(String infoMessage, String warningMessage) {
		Integer idFromUser;
		while(true) {
			System.out.println(infoMessage);				
			idFromUser = UserInputUtils.getIntegerInputFromUser();
			logger.debug("User enter id = {}", idFromUser);
			if(ValidatorUtils.isIntegerGreaterThanZero(idFromUser)) {
				return idFromUser;
			} else {				
				System.out.println(warningMessage);
			} 
		}
	}
	
}
