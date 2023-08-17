package com.foxminded.bohdansharubin;

import com.foxminded.bohdansharubin.controller.CharCounterController;
import com.foxminded.bohdansharubin.model.CharCounter;
import com.foxminded.bohdansharubin.view.CharCounterView;

public class TaskEntryPoint {

	public static void main(String[] args) {
		CharCounter charCounterModel = new CharCounter();
		CharCounterView charCounterView = new CharCounterView();
		CharCounterController charCounterController = new CharCounterController(charCounterModel, charCounterView);
		charCounterController.start();
	}

}
