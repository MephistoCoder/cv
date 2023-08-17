package com.foxminded.bohdansharubin.schoolspringbootapp.services;

import static com.foxminded.bohdansharubin.schoolspringbootapp.infomessages.CourseControllerMessages.*;
import static com.foxminded.bohdansharubin.schoolspringbootapp.utils.ValidatorUtils.NAME_DEFAULT_PATTERN;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.bohdansharubin.schoolspringbootapp.dao.CourseDao;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Course;
import com.foxminded.bohdansharubin.schoolspringbootapp.utils.UserInputUtils;
import com.foxminded.bohdansharubin.schoolspringbootapp.utils.ValidatorUtils;
import com.foxminded.bohdansharubin.schoolspringbootapp.view.CourseView;

@Service
public class CourseService {
	
	private CourseDao courseDao;
	private CourseView courseView;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected CourseService(CourseDao postgresCourseDao,
							CourseView courseView) {
		this.courseDao = postgresCourseDao;
		this.courseView = courseView;
	}
	
	public void printCoursesInfo(List<Course> courses) {
		courseView.printInfo(courses);
	}
	
	public List<Course> getAllCourses() {
		return courseDao.findAll();
	}
	
	public Course findCourseById() {
		Integer courseId = getIdFromUser(ENTER_ID_MESSAGE.getMessage(), INVALID_ID_INPUT_MESSAGE.getMessage());
		logger.debug("Course id from user = {}", courseId);
		try {
			return courseDao.getReferenceById(courseId);			
		} catch(EntityNotFoundException e) {
			logger.warn("Course with id = {} not found", courseId);
			return null;
		}
	}
	
	public Course findCourseByName() {
		String courseName = getNameFromUser(ENTER_NAME_MESSAGE.getMessage(), INVALID_NAME_INPUT_MESSAGE.getMessage());
		logger.debug("Course name from user = {}", courseName);
		return courseDao.findByName(courseName)
								.orElse(null);
	}	
	
	private int getIdFromUser(String infoMessage, String warningMessage) {
		Integer idFromUser;
		while(true) {
			System.out.println(infoMessage);				
			idFromUser = UserInputUtils.getIntegerInputFromUser();
			logger.debug("User inputed id = {}", idFromUser);
			if(ValidatorUtils.isIntegerGreaterThanZero(idFromUser)) {
				return idFromUser;
			} else {				
				System.out.println(warningMessage);
			} 
		}
	}
	
	private String getNameFromUser(String infoMessage, String warningMessage) {
		String nameFromUser;
		while(true) {
			System.out.println(infoMessage);
			nameFromUser = UserInputUtils.getStringInputFromUser();
			logger.debug("User inputed name = {}", nameFromUser);
			if(ValidatorUtils.isStringMatchesPattern(nameFromUser, NAME_DEFAULT_PATTERN) ) {
				return nameFromUser;
			} else {				
				System.out.println(warningMessage);
			}
		}
	}
	
}
