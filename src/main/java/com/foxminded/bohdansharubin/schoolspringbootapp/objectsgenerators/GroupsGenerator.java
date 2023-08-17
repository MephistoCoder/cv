package com.foxminded.bohdansharubin.schoolspringbootapp.objectsgenerators;

import java.util.List;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.foxminded.bohdansharubin.schoolspringbootapp.model.Group;

@Component
public class GroupsGenerator extends ObjectGenerator<Group> {
	private static final String ENGLISH_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String STRING_DEFAULT_HYPHEN = "-"; 
	private static final int DEFAULT_GROUPS_TO_GENERATE = 10;
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected GroupsGenerator() {
		super(DEFAULT_GROUPS_TO_GENERATE);	
	}
	
	@Override
	public List<Group> generateObjects() {
		IntStream.range(0, getCountToGenerate())
				 .forEach(i -> getObjectsList().add(new Group(createRandomGroupName())));	
		logger.info("Successfully generated groups");
		return getObjectsList();
	}
	
	private String createRandomGroupName() {
		StringBuilder randomGroupName = new StringBuilder();
		IntStream.range(0, 2).forEach(i -> randomGroupName.append(ENGLISH_LETTERS.charAt(getRandomNumber(ENGLISH_LETTERS.length()))));
		randomGroupName.append(STRING_DEFAULT_HYPHEN);
		IntStream.range(0, 2).forEach(i -> randomGroupName.append(getRandomNumber(10)));
		return randomGroupName.toString(); 
	}
	
}
