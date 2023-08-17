package com.foxminded.bohdansharubin.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.foxminded.bohdansharubin.model.IntegerDivision;
import com.foxminded.bohdansharubin.view.IntegerDivisionView;

class TestIntegerDivisionController {
	private static IntegerDivisionController  integerDivisionController= new IntegerDivisionController(
																			 new IntegerDivision(),
																			 new IntegerDivisionView()
																			 );
	
	@ParameterizedTest
	@ValueSource(strings = {"10/2", "10 /2", "10/ 2", "10 / 2", "10   /  2"})
	void validateInput_returnTrue_correctInput(String inputLine) {
		Assertions.assertTrue(integerDivisionController.validateInput(inputLine));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {" 10/2", "10/2 ", " 10/2 ", "10a / 2", "10/2a", "a/2", "10/a", "", "1!/2"})
	void validateInput_returnFalse_incorrectInput(String inputLine) {
		Assertions.assertFalse(integerDivisionController.validateInput(inputLine));
	}
	
	@ParameterizedTest
	@NullSource
	void validateInput_returnFalse_nullInput(String inputLine) {
		Assertions.assertFalse(integerDivisionController.validateInput(inputLine));
	}
	
}
