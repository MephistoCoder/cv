package com.foxminded.bohdansharubin.schoolspringbootapp.objectsgenerators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.foxminded.bohdansharubin.schoolspringbootapp.model.Group;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Student;

@Component
public class GroupStudentRelationsGenerator extends RelationsGenerator {
	private int minStudentsInGroup = 10;
	private int maxStudentsInGroup = 30;
	private List<Group> groups;
	private List<Student> students;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public GroupStudentRelationsGenerator() {
		this.groups = new ArrayList<>();
		this.students = new ArrayList<>();
	}

	@Override
	public boolean validateGenerationParameters() {
		return groups != null
				&&
			   students != null
			    &&
			   !groups.isEmpty()
			    &&
			   !students.isEmpty()
				&&
			   minStudentsInGroup >= 0
				&&
			   maxStudentsInGroup > minStudentsInGroup
			    &&
			   minStudentsInGroup < students.size()
			    &&
			   maxStudentsInGroup <= students.size();
	}

	@Override
	public void generateDependencies() {
		if (!validateGenerationParameters()) {
			logger.warn("Dependencies between groups and students didn't generate, because generated parameters were incorect");
			return;
		}
		students.sort(Comparator.comparing(Student::getFirstName));
		List<Student> allStudents = students.stream()
				   							.collect(Collectors.toList());
		IntStream.range(0, groups.size())
				 .forEach(i -> { addStudentsToGroup(allStudents, i);
				 				 removeStudentsWithGroup(allStudents);
						      }
						 );
		logger.info("Dependencies between courses and students generated successfully");
	}

	public final List<Group> getGroups() {
		return groups;
	}

	public final void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public final List<Student> getStudents() {
		return students;
	}

	public final void setStudents(List<Student> students) {
		this.students = students;
	}

	public final int getMinStudentsInGroup() {
		return minStudentsInGroup;
	}

	public final int getMaxStudentsInGroup() {
		return maxStudentsInGroup;
	}
	
	public final void setMinStudentsInGroup(int minStudentsInGroup) {
		this.minStudentsInGroup = minStudentsInGroup;
	}

	public final void setMaxStudentsInGroup(int maxStudentsInGroup) {
		this.maxStudentsInGroup = maxStudentsInGroup;
	}
	
	private void removeStudentsWithGroup(List<Student> studentsWithoutGroup) {
		logger.debug("Students without groups = {}", studentsWithoutGroup);
		studentsWithoutGroup.removeAll(studentsWithoutGroup.stream()
							.limit(getRandomInRange(minStudentsInGroup,
									 					   maxStudentsInGroup))
							.collect(Collectors.toList()));
	}
	
	private void addStudentsToGroup(List<Student> studentsWithoutGroup, int groupId) {
		studentsWithoutGroup.stream()
				    	    .limit(getRandomInRange(minStudentsInGroup, maxStudentsInGroup))
				    	    .forEach(student -> student.setGroup(groups.get(groupId)));
		logger.debug("Students was added to group with id = {}", groupId);
	}

}
