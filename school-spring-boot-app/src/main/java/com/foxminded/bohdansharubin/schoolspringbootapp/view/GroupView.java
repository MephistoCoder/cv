package com.foxminded.bohdansharubin.schoolspringbootapp.view;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.foxminded.bohdansharubin.schoolspringbootapp.model.Group;

@Component
public class GroupView extends AbstractView<Group> {
	private static final String STRING_GROUP_ID = "id";
	private static final String STRING_GROUP_NAME = "name";
	
	private int longestIdLength;
	private int longestNameLength;
	
	@Override
	public void printInfo(List<Group> groups) { 
		StringBuilder groupsInfo = new StringBuilder();
		calculateLongestElements(groups);
		groupsInfo.append(buildHeaderLine())
				  .append(buildSeparatorLine(STRING_HORIZONTAL_SPLITTER, groupsInfo.length()))
				  .append(STRING_DEFAULT_EOL);
		groups.forEach(group -> groupsInfo.append(buildGroupInfoLine(group)));
		System.out.println(groupsInfo);
	}
	
	@Override
	protected String buildHeaderLine() {
		return new StringBuilder().append(elementSupplement(longestIdLength, STRING_GROUP_ID))
								  .append(STRING_VERTICAL_SPLITTER)
								  .append(elementSupplement(longestNameLength, STRING_GROUP_NAME))
								  .append(STRING_DEFAULT_EOL)
								  .toString();
	}
	
	@Override
	protected void calculateLongestElements(List<Group> groups) {
		longestIdLength = calculateLongestLength(STRING_GROUP_ID,
												 groups.stream()
										 		   	   .map(group -> String.valueOf(group.getId()))
										 		   	   .collect(Collectors.toList())
								  );
		longestNameLength =  calculateLongestLength(STRING_GROUP_NAME,
													groups.stream()
														  .map(Group::getName)
														  .collect(Collectors.toList())
								  );
	}
	
	private String buildGroupInfoLine(Group group) {
		return new StringBuilder().append(elementSupplement(longestIdLength, String.valueOf(group.getId())))
					 .append(STRING_VERTICAL_SPLITTER)
					 .append(elementSupplement(longestNameLength, group.getName()))
					 .append(STRING_DEFAULT_EOL)
					 .toString();
	}

}
