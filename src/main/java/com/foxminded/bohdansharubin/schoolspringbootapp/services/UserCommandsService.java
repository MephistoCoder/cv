package com.foxminded.bohdansharubin.schoolspringbootapp.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.bohdansharubin.schoolspringbootapp.commands.UserCommands;
import com.foxminded.bohdansharubin.schoolspringbootapp.view.CommandView;

@Service
public class UserCommandsService {

	private CommandView commandView;
	
	@Autowired
	public UserCommandsService(CommandView commandView) {
		this.commandView = commandView;
	}
	
	public void printUserCommandsInfo() {
		commandView.printInfo(Arrays.asList(UserCommands.values()));
	}

}
	