package com.foxminded.bohdansharubin.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.foxminded.bohdansharubin.model.CharCounter;
import com.foxminded.bohdansharubin.view.CharCounterView;

class TestCharCounterController {
	private CharCounterController charCounterController = new CharCounterController(new CharCounter(),
																					new CharCounterView());
	@ParameterizedTest
	@ValueSource(strings = {"abvdsh", "223fdsa sf sdf2", "12432 425 @$%514$ %3", " !@fkj "})
	void validateUserInput_returnTrue_correctInputLine(String inputLine) {
		Assertions.assertTrue(charCounterController.validateUserInput(inputLine));
		
	}
	
	@ParameterizedTest
	@ValueSource(strings = {""})
	@NullSource
	void validateUserInput_returnFalse_NullOrEmptyInputLine(String inputLine) {
		Assertions.assertFalse(charCounterController.validateUserInput(inputLine));
	}
}
