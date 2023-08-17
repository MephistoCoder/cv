package com.foxminded.bohdansharubin;

import com.foxminded.bohdansharubin.controller.RaceController;
import com.foxminded.bohdansharubin.model.RaceDataCollector;
import com.foxminded.bohdansharubin.view.RaceView;

public class TaskEntryPoint {

	public static void main(String[] args) {
		RaceView raceView = new RaceView();
		RaceDataCollector raceDataCollectorModel = new RaceDataCollector();
		RaceController raceController = new RaceController(raceDataCollectorModel, raceView);
		raceController.start();
	}

}
