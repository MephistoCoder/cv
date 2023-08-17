package com.foxminded.bohdansharubin.view;

import java.util.Queue;

public class IntegerDivisionView {
	private static final String MINUS_DEFAULT_SYMBOL = "_";
	private static final String HORIZONTAL_DEFAULT_DIVIDER = "-";
	private static final String VERTICAL_DEFAULT_DIVIDER = "|";
	private static final String STRING_DEFAULT_SPLITER = " ";
	private static final String STRING_DEFAULT_EOL = "\n";
	private static final String DEFAULT_ALLERT_MESSAGE = "Invalid input. Can't do division";
	
	public void printIntegerDivisionDetail(String divident, String divider, String quotient, Queue<String> divisionSteps) {
		StringBuilder lineOfDivisionStep = new StringBuilder();
		String previousElementOfDivision = divisionSteps.poll();
		String currentElementOfDivision = divisionSteps.poll();
		int	startTabForAnotherLines = previousElementOfDivision.length() - currentElementOfDivision.length();
		currentElementOfDivision = getSameLengthElementsAsMaxElementLength(previousElementOfDivision, currentElementOfDivision);
		
		lineOfDivisionStep.append(buildFirstLineOfDivision(divident, divider))
						  .append(buildSecondLineOfDivision(currentElementOfDivision,
															divident,
															Math.max(Integer.parseInt(divider), Integer.parseInt(quotient))))
						  .append(buildThirdLineOfDivision(currentElementOfDivision, divident, quotient))
						  .append(buildAnotherLinesOfDivision(divident, startTabForAnotherLines, divisionSteps));
		System.out.println(lineOfDivisionStep.toString());
	}
 
	private String buildFirstLineOfDivision(String divident, String divider) {
		StringBuilder firstLineOfDivision = new StringBuilder();
		firstLineOfDivision.append(MINUS_DEFAULT_SYMBOL)
						   .append(divident)
						   .append(VERTICAL_DEFAULT_DIVIDER)
						   .append(divider)
						   .append(STRING_DEFAULT_EOL);
		return firstLineOfDivision.toString();
	}
	
	private String buildSecondLineOfDivision(String elementOfDivisionSteps, String divident, int maxNumber) {
		StringBuilder secondLineOfDivision = new StringBuilder();
		secondLineOfDivision.append(STRING_DEFAULT_SPLITER)
							.append(elementOfDivisionSteps)
							.append(buildSpaceSeparatorLine(divident.length() - elementOfDivisionSteps.length()))
							.append(VERTICAL_DEFAULT_DIVIDER)
							.append(buildHorizontalSeparatorLine(String.valueOf(maxNumber)))
							.append(STRING_DEFAULT_EOL);
		return secondLineOfDivision.toString();
	}
	
	private String buildThirdLineOfDivision(String elementOfDivisionSteps, String divident, String quotient) {
		StringBuilder thirdLineOfDivision = new StringBuilder();
		thirdLineOfDivision.append(STRING_DEFAULT_SPLITER)
						   .append(buildHorizontalSeparatorLine(elementOfDivisionSteps))
						   .append(buildSpaceSeparatorLine(divident.length() - elementOfDivisionSteps.length()))
						   .append(VERTICAL_DEFAULT_DIVIDER)
						   .append(quotient)
						   .append(STRING_DEFAULT_EOL);
		return thirdLineOfDivision.toString();
	}
	
	private String buildAnotherLinesOfDivision(String divident, int startTabForAnotherLines, Queue<String> divisionSteps) {
		String currentElementOfDivision;
		String previousElementOfDivision;
		StringBuilder anotherLineOfDivision = new StringBuilder();
		
		for(int i = 0; (previousElementOfDivision = divisionSteps.poll()) != null; i++) {
			currentElementOfDivision = divisionSteps.poll();
			if(currentElementOfDivision == null) {
				break;
			}
			currentElementOfDivision = getSameLengthElementsAsMaxElementLength(previousElementOfDivision, currentElementOfDivision);
			anotherLineOfDivision.append(buildStepOfDivision(previousElementOfDivision, currentElementOfDivision, i + startTabForAnotherLines))
								 .append(buildSpaceSeparatorLine(i + startTabForAnotherLines + MINUS_DEFAULT_SYMBOL.length()))
								 .append(buildHorizontalSeparatorLine(currentElementOfDivision))
								 .append(STRING_DEFAULT_EOL);
		}
		previousElementOfDivision = getSameLengthElementsAsMaxElementLength(divident, previousElementOfDivision); 			
		anotherLineOfDivision.append(STRING_DEFAULT_SPLITER)
							 .append(previousElementOfDivision);
		return anotherLineOfDivision.toString();
	}
	private String buildStepOfDivision(String firstElementOfStep,  String secondElementOfStep, int tabForStep) {
		StringBuilder stepOfDivision = new StringBuilder();
		
		for(int j = 0; j < 2; j++) {
			stepOfDivision.append(buildSpaceSeparatorLine(tabForStep));
			if(j == 0) {
				stepOfDivision.append(MINUS_DEFAULT_SYMBOL)
									 .append(firstElementOfStep)
									 .append(STRING_DEFAULT_EOL);
			} else {
				stepOfDivision.append(STRING_DEFAULT_SPLITER)
									 .append(secondElementOfStep)
									 .append(STRING_DEFAULT_EOL);		
			}
		}
		
		return stepOfDivision.toString();
	}
	
	private String buildHorizontalSeparatorLine(String quotient) {
		StringBuilder separatorLine = new StringBuilder();
		for(int i = 0; i < quotient.length(); i++) {
			separatorLine.append(HORIZONTAL_DEFAULT_DIVIDER);
		}
		return separatorLine.toString();
	}
	
	private String buildSpaceSeparatorLine(int length) {
		StringBuilder separatorLine = new StringBuilder();
		for(int i = 0; i < length; i++) {
			separatorLine.append(STRING_DEFAULT_SPLITER);
		}
		return separatorLine.toString();
	}
	
	private String getSameLengthElementsAsMaxElementLength (String firstElement, String secondElement) {
		String minLengthElement = firstElement.length() < secondElement.length() ?  firstElement : secondElement;
		StringBuilder formattedElement = new StringBuilder();
		formattedElement.append(buildSpaceSeparatorLine(Math.abs(firstElement.length() - secondElement.length())))
						.append(minLengthElement);
		return formattedElement.toString();		
	}
	
	public void printAlertMessage() {
		System.out.println(DEFAULT_ALLERT_MESSAGE);
	}
}
