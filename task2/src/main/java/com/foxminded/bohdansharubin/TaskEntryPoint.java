package com.foxminded.bohdansharubin;

import com.foxminded.bohdansharubin.controller.IntegerDivisionController;
import com.foxminded.bohdansharubin.model.IntegerDivision;
import com.foxminded.bohdansharubin.view.IntegerDivisionView;

public class TaskEntryPoint {

	public static void main(String[] args) {
		
		IntegerDivision integerDivisionModel = new IntegerDivision();
		IntegerDivisionView integerDivisionView = new IntegerDivisionView();
		IntegerDivisionController integerDivisionController = new IntegerDivisionController(integerDivisionModel,
																							integerDivisionView
																							); 
		integerDivisionController.runDivision();
	}
	
}
