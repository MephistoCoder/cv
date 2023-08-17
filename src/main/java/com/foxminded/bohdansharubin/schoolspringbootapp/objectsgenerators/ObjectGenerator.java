package com.foxminded.bohdansharubin.schoolspringbootapp.objectsgenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class ObjectGenerator<T> {
	private int countToGenerate = 1;
	private List<T> objectsList = new ArrayList<>();
	private String dataFilePath;
	private Random random = new Random();
	
	protected ObjectGenerator() {
	}
	
	protected ObjectGenerator(int objectsToGenerate) {
		this.countToGenerate = objectsToGenerate;
	}
	
	protected ObjectGenerator(String dataFilePath) {
		this.dataFilePath = dataFilePath;
	}
	protected ObjectGenerator(String dataFilePath, int objectsToGenerate) {
		this.dataFilePath = dataFilePath;
		this.countToGenerate = objectsToGenerate;
	}
	
	public abstract List<T> generateObjects();

	protected int getRandomNumber(int endRange) {
		return random.nextInt(endRange);
	}
	
	public final int getCountToGenerate() {
		return countToGenerate;
	}

	public final void setCountToGenerate(int objectsToGenerate) {
		this.countToGenerate = objectsToGenerate;
	}

	public final List<T> getObjectsList() {
		return objectsList;
	}

	public final void setObjectsList(List<T> objectsList) {
		this.objectsList = objectsList;
	}

	public final String getDataFilePath() {
		return dataFilePath;
	}

	public final void setDataFilePath(String dataFilePath) {
		this.dataFilePath = dataFilePath;
	}
	
}
