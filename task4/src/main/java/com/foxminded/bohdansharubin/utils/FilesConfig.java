package com.foxminded.bohdansharubin.utils;

public class FilesConfig {
	private String startLapTimeFilePath;
	private String endLapTimeFilePath;
	private String abbreviationsFilePath;
	
	public FilesConfig() {
		this.startLapTimeFilePath = "src/main/resources/start.log";
		this.endLapTimeFilePath = "src/main/resources/end.log";
		this.abbreviationsFilePath = "src/main/resources/abbreviations.txt";
	}

	public FilesConfig(String abbreviationsFilePath, String startLapTimeFilePath, String endLapTimeFilePath) {
		this.startLapTimeFilePath = startLapTimeFilePath;
		this.endLapTimeFilePath = endLapTimeFilePath;
		this.abbreviationsFilePath = abbreviationsFilePath;
	}

	public String getStartLapTimeFilePath() {
		return startLapTimeFilePath;
	}
	
	public String getEndLapTimeFilePath() {
		return endLapTimeFilePath;
	}
	
	public String getAbbreviationsFilePath() {
		return abbreviationsFilePath;
	}
	
}
