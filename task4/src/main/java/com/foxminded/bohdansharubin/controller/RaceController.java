package com.foxminded.bohdansharubin.controller;

import com.foxminded.bohdansharubin.model.RaceDataCollector;
import com.foxminded.bohdansharubin.view.RaceView;

public class RaceController {
	private RaceDataCollector raceDataCollectorModel;
	private RaceView raceView;
	
	public RaceController(RaceDataCollector raceDataCollectorModel, RaceView raceView) {
		this.raceDataCollectorModel = raceDataCollectorModel;
		this.raceView = raceView;
	}
	
	public void start() {
		raceDataCollectorModel.calculateRaceData();
		if(raceDataCollectorModel.getRacers().isEmpty()) {
			raceView.printWarningMessage();
			return;
		}
		raceView.printRaceInfo(raceDataCollectorModel.getRacers());
	}
}
