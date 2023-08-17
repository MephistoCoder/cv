package com.foxminded.bohdansharubin.schoolspringbootapp.utils;

import java.util.Scanner;

import com.foxminded.bohdansharubin.schoolspringbootapp.commands.UserCommands;

public class UserInputUtils {
	private static final Scanner DEFAULT_USER_SCANNER = new Scanner(System.in);
	
	private UserInputUtils() {
	}
	
	public static String getStringInputFromUser() {
		try {
			return DEFAULT_USER_SCANNER.nextLine().trim();
		} catch(Exception e) {
			return UserCommands.QUIT_APP.name();
		}
	}
	
	public static Integer getIntegerInputFromUser() {
		try {
			return Integer.valueOf(DEFAULT_USER_SCANNER.nextLine().trim());
		} catch(Exception e) {
			return null;
		}
	}

}
