package com.foxminded.bohdansharubin.schoolspringbootapp.view;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public abstract class AbstractView<T> {
	public static final String STRING_DEFAULT_EOL = System.lineSeparator();
	public static final String STRING_DEFAULT_SPLITTER = " ";
	public static final String STRING_VERTICAL_SPLITTER = "|";
	public static final String STRING_HORIZONTAL_SPLITTER = "-";
	
	public abstract void printInfo(List<T> objects);
	
	protected abstract String buildHeaderLine();
	
	protected abstract void calculateLongestElements(List<T> objects);
	
	protected String buildSeparatorLine(String separator, int length) {
		StringBuilder separatorLine = new StringBuilder();
		IntStream.range(0, length)
				 .forEach(i -> separatorLine.append(separator));
		return separatorLine.toString();
	}
	
	protected String elementSupplement(int targetLength, String inputElement) {
		return new StringBuilder().append(buildSeparatorLine(STRING_DEFAULT_SPLITTER, 
												targetLength - inputElement.length()))
								  .append(inputElement)
								  .toString();
	}
	
	protected int calculateLongestLength(String initialElement, Collection<String> comparedElements) {
		return Math.max(initialElement.length(),
						longestStringLength(comparedElements));
	}
	
	private int longestStringLength(Collection<String> stringElements) {
		return stringElements.stream()
							 .max(Comparator.comparing(String::length))
							 .map(String::length)
							 .orElse(0);
	}

}
