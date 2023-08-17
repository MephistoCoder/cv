package com.foxminded.bohdansharubin.schoolspringbootapp.services;

import static com.foxminded.bohdansharubin.schoolspringbootapp.infomessages.GroupControllerMessages.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.bohdansharubin.schoolspringbootapp.dao.GroupDao;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Group;
import com.foxminded.bohdansharubin.schoolspringbootapp.utils.UserInputUtils;
import com.foxminded.bohdansharubin.schoolspringbootapp.utils.ValidatorUtils;
import com.foxminded.bohdansharubin.schoolspringbootapp.view.GroupView;

@Service
public class GroupService {
	
	private GroupDao groupDao;
	private GroupView groupView;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public GroupService(GroupDao postgresGroupDao,
						GroupView groupView) {	
		this.groupDao = postgresGroupDao;
		this.groupView = groupView;
	}
	
	public void printGroupsInfo(List<Group> groups) {
		groupView.printInfo(groups);
	}
	
	public List<Group> getAllGroups() {
		return groupDao.findAll();
	}
	
	public List<Group> getGroupsByMaxNumberOfStudents() {
		int numberOfStudents = getNumberOfStudentsFromUser(ENTER_MAXIMUM_NUMBER_OF_STUDENTS_IN_GROUP_MESSAGE.getMessage(),
														   INVALID_NUMBER_OF_STUDENTS_INPUT_MESSAGE.getMessage());
		logger.debug("Number of students for query = {}", numberOfStudents);
		List<Group> groups = groupDao.findAll();
		logger.debug("All groups from database = {}", groups);
		return groups.stream()
				  	 .filter(group -> group.getStudents()
				  			 			   .size() <= numberOfStudents)
				  	 .collect(Collectors.toList());
	}
	
	public Group getById(int id) {
		try {
			return groupDao.getReferenceById(id);
		} catch(EntityNotFoundException e) {
			return null;
		}
	}
	
	private int getNumberOfStudentsFromUser(String infoMessage, String warningMessage) {
		Integer numberOfStudentsFromUser;
		while(true) {
			System.out.println(infoMessage);				
			numberOfStudentsFromUser = UserInputUtils.getIntegerInputFromUser();
			logger.debug("User enter number of students = {}", numberOfStudentsFromUser);
			if(ValidatorUtils.isIntegerGreaterThanZero(numberOfStudentsFromUser)) {
				return numberOfStudentsFromUser;
			} else {				
				System.out.println(warningMessage);
			} 
		}
	}

}
