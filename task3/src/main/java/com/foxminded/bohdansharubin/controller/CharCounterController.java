package com.foxminded.bohdansharubin.controller;

import java.util.Map;
import java.util.Scanner;

import com.foxminded.bohdansharubin.model.CharCounter;
import com.foxminded.bohdansharubin.view.CharCounterView;

import javafx.util.Pair;

public class CharCounterController {
	private CharCounter charCounterModel;
	private CharCounterView charCounterView;
	
	public CharCounterController(CharCounter charCounterModel, CharCounterView charCounterView) {
		this.charCounterModel = charCounterModel;
		this.charCounterView = charCounterView;
	}

	public void start() {
		Scanner userInputScanner = new Scanner(System.in);
		while(true) {
			String userInputLine = userInput(userInputScanner);
			if(!validateUserInput(userInputLine)) {
				charCounterView.printAlertMessage();
			} else if(userInputLine.equals("/quit")) {
				userInputScanner.close();
				return;
			} else {
				Pair<String, Map<Character, Integer>> counterUniqueSymbolsUsesForLine = charCounterModel.doCount(userInputLine);
				charCounterView.printNumberOfUniqueCharactersUsed(counterUniqueSymbolsUsesForLine);				
			}
			
		}		
	}
	
	private String userInput(Scanner userInputScanner) {
		return userInputScanner.nextLine();
	}
	
	public boolean validateUserInput(String userInput) {
		return userInput != null && !userInput.isEmpty();
	}
}
