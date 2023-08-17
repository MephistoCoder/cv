package com.foxminded.bohdansharubin.schoolspringbootapp.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;

import com.foxminded.bohdansharubin.schoolspringbootapp.commands.UserCommands;
import com.foxminded.bohdansharubin.schoolspringbootapp.services.CourseService;
import com.foxminded.bohdansharubin.schoolspringbootapp.services.GroupService;
import com.foxminded.bohdansharubin.schoolspringbootapp.services.StudentService;
import com.foxminded.bohdansharubin.schoolspringbootapp.services.UserCommandsService;
import com.foxminded.bohdansharubin.schoolspringbootapp.utils.UserInputUtils;

@Controller
@Order(2)
public class SchoolController implements ApplicationRunner {
	private GroupService groupService;
	private StudentService studentService;
	private CourseService courseService;
	private UserCommandsService userCommandService;
	private Map<String, Runnable> actionMap = new ActionMap().userActions;
	
	@Autowired
	public SchoolController(GroupService groupService,
						StudentService studentService,
						CourseService courseService, 
						UserCommandsService userCommandService) {
		this.groupService = groupService;
		this.studentService = studentService;
		this.courseService = courseService;
		this.userCommandService = userCommandService;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		start();
	}
	
	public void start() {
		System.out.println("Welcome to school application");
		while(true) {
			userCommandService.printUserCommandsInfo();
			actionMap.getOrDefault(UserInputUtils.getStringInputFromUser()
												 .toUpperCase(),
								   () -> {})
					 .run();
		}
	}
	
	private class ActionMap {
		private Map<String, Runnable> userActions = new HashMap<>();
		
		{
			userActions.put(UserCommands.QUIT_APP.name(), () -> {
				System.exit(0);
			});
			
			userActions.put(UserCommands.ALL_GROUPS.name(), () -> {
				groupService.printGroupsInfo(groupService.getAllGroups());
			});
			
			userActions.put(UserCommands.ALL_STUDENTS.name(), () -> {
				studentService.printStudentsInfo(studentService.getAllStudents());
			});
			
			userActions.put(UserCommands.ALL_COURSES.name(), () -> {
				courseService.printCoursesInfo(courseService.getAllCourses());
			});
			
			userActions.put(UserCommands.ADD_STUDENT.name(), () -> {
				studentService.addStudent();
			});
			
			userActions.put(UserCommands.ADD_STUDENT_TO_GROUP.name(), () -> {
				studentService.addStudentToGroup();
			});
			
			userActions.put(UserCommands.ADD_STUDENT_TO_COURSE.name(), () -> {
				studentService.addStudentToCourse();
			});
			
			userActions.put(UserCommands.DELETE_STUDENT.name(), () -> {
				studentService.deleteStudent();
			});
			
			userActions.put(UserCommands.DELETE_STUDENT_FROM_COURSE.name(), () -> {
				studentService.deleteStudentFromCourse();
			});
			
			userActions.put(UserCommands.GET_STUDENTS_BY_COURSE_NAME.name(), () -> {
				studentService.printStudentsInfo(studentService.getStudentsByCourse(courseService.findCourseByName()));
			});
			
			userActions.put(UserCommands.GET_ALL_GROUPS_WITH_STUDENTS_COUNT.name(), () -> {
				groupService.printGroupsInfo(groupService.getGroupsByMaxNumberOfStudents());
			});
		}
	}

}
