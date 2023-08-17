package com.foxminded.bohdansharubin.schoolspringbootapp.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	
	private FileUtils() {
	}
	
	public static List<String> fileToLines(String filePath) {
		List<String> dataFromFile = new ArrayList<>();
		if(!validateFile(filePath)) {
			return dataFromFile;
		}
		Path path = Paths.get(filePath);
		try {
			dataFromFile = Files.readAllLines(path);
		} catch(IOException ignored) {
			System.err.println("File " + filePath + " not found");
		}
		return dataFromFile;
	}
	
	public static boolean validateFile(String filePath) {
		return filePath != null
				&&
			   Paths.get(filePath)
					.toFile()
					.canRead();
	}
	
}
