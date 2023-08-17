package com.foxminded.bohdansharubin.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.foxminded.bohdansharubin.model.IntegerDivision;
import com.foxminded.bohdansharubin.view.IntegerDivisionView;

public class IntegerDivisionController {
	IntegerDivision integerDivisionModel;
	IntegerDivisionView integerDivisionView;
	
	public IntegerDivisionController(IntegerDivision integerDivisionModel, IntegerDivisionView integerDivisionView) {
		this.integerDivisionModel = integerDivisionModel;
		this.integerDivisionView = integerDivisionView;
	}
	
	public void runDivision() {
		String userInputData = userInput();
		if(!validateInput(userInputData)) {
			integerDivisionView.printAlertMessage();
			return;
		}
		Map<String, Integer> userInputDataMap = parseUserInput(userInputData);
		setIntegerDivisionModelData(userInputDataMap);
		if(!integerDivisionModel.validateDivision()) {
			integerDivisionView.printAlertMessage();
			return;
		}
		integerDivisionModel.makeDivisionSteps();
		if(integerDivisionModel.getQuotient().intValue() == 0) {
			integerDivisionView.printAlertMessage();
			return;
		}
		String divident = integerDivisionModel.getDivident().toString();
		String divider = integerDivisionModel.getDivider().toString();
		String quotient = integerDivisionModel.getQuotient().toString();
		integerDivisionView.printIntegerDivisionDetail(divident,
														divider,
														quotient,
														integerDivisionModel.getDivisionStepsAsString()
														);
	}
	
	public boolean validateInput(String inputLine) {
		String validateRegex = "^[^0\\D]\\d+\\s*/\\s*\\d+";
		return inputLine != null && inputLine.matches(validateRegex);
	}
	
	private String userInput() {
		Scanner userInputData = new Scanner(System.in);
		String inputLine = userInputData.nextLine();
		userInputData.close();
		return inputLine;
	}
	
	private void setIntegerDivisionModelData(Map<String, Integer> userInputMap) {
		integerDivisionModel.setDivident(userInputMap.get("Divident"));
		integerDivisionModel.setDivider(userInputMap.get("Divider"));
	}
	
	public Map<String, Integer> parseUserInput(String userInput) {
		HashMap<String, Integer> hashMapOfUserData = new HashMap<>();
		String[] stringsOfUserData = userInput.split("/");
		int[] numbersOfUserData = new int[stringsOfUserData.length];
		for(int i = 0; i < numbersOfUserData.length; i++) {
			numbersOfUserData[i] = Integer.valueOf(stringsOfUserData[i].trim());
		}
		hashMapOfUserData.put("Divident", numbersOfUserData[0]);
		hashMapOfUserData.put("Divider", numbersOfUserData[1]);
		return hashMapOfUserData;
	}
	
}
