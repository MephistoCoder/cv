package com.foxminded.bohdansharubin.schoolspringbootapp.view;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.foxminded.bohdansharubin.schoolspringbootapp.commands.UserCommands;

@Component
public class CommandView extends AbstractView<UserCommands> {
	private static final String USER_COMMAND_NAME = "COMMAND";
	private static final String USER_COMMAND_ACTION = "ACTION";
	private int longestCommandNameLength;
	private int longestCommandActionLength;
	
	
	@Override
	public void printInfo(List<UserCommands> userCommands) {
		StringBuilder userCommandsInfo = new StringBuilder();
		calculateLongestElements(userCommands);
		userCommandsInfo.append(buildHeaderLine())
						.append(buildSeparatorLine(STRING_HORIZONTAL_SPLITTER ,userCommandsInfo.length()))
						.append(STRING_DEFAULT_EOL);
		userCommands.forEach(userCommand -> userCommandsInfo.append(buildUserCommandInfo(userCommand)));
		System.out.println(userCommandsInfo);
	}

	@Override
	protected String buildHeaderLine() {
		return new StringBuilder().append(elementSupplement(longestCommandNameLength, USER_COMMAND_NAME))
								  .append(STRING_VERTICAL_SPLITTER)
								  .append(elementSupplement(longestCommandActionLength, USER_COMMAND_ACTION))
								  .append(STRING_DEFAULT_EOL)
								  .toString();
	}

	@Override
	protected void calculateLongestElements(List<UserCommands> userCommands) {
		longestCommandNameLength = calculateLongestLength(USER_COMMAND_NAME,
														  userCommands.stream()
				  													  .map(UserCommands::name)
				  													  .collect(Collectors.toList())
				  									     );
		longestCommandActionLength = calculateLongestLength(USER_COMMAND_ACTION,
											  				userCommands.stream()
													  		            .map(UserCommands::getAction)
													  					.collect(Collectors.toList())
													  	  );
	}
	
	private String buildUserCommandInfo(UserCommands userCommand) {
		return new StringBuilder().append(elementSupplement(longestCommandNameLength, userCommand.name()))
					       		  .append(STRING_VERTICAL_SPLITTER)
					       		  .append(elementSupplement(longestCommandActionLength, userCommand.getAction()))
					       		  .append(STRING_DEFAULT_EOL)
					       		  .toString();
	}
	
}
