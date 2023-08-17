package com.foxminded.bohdansharubin.schoolspringbootapp.objectsgenerators;

import java.util.Random;
import java.util.stream.IntStream;

public abstract class RelationsGenerator {
	private Random random = new Random();
	
	protected RelationsGenerator() {
	}
	
	public abstract void generateDependencies();
	
	protected abstract boolean validateGenerationParameters();
	
	protected int getRandomInRange(int startRange, int endRange) {
		return random.nextInt(endRange
							  - startRange
							  + 1)
				+ startRange;
	}
	
	protected IntStream getRandomInts(int length, int start, int end) {
		return random.ints(length, start, end)
					 .distinct();
	}
}
