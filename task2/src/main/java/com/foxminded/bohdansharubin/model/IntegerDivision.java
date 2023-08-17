package com.foxminded.bohdansharubin.model;

import java.util.ArrayDeque;
import java.util.Queue;

public class IntegerDivision extends Division {
	private Queue<Integer> divisionSteps = new ArrayDeque<>();
	
	public IntegerDivision() {
	}

	public IntegerDivision(int divident, int divider) {
		super(divident, divider);
	}

	@Override
	public boolean validateDivision() {
		setDivisionCorrectness(getDivider().intValue() > 0 && getDivident().intValue() >= 0);
		return isDivisionCorrectness();
	}
	
	public void makeDivisionSteps() {
		resetDivisionSteps();
		makeDivision();
		if(isDivisionCorrectness()) {
			String dividentString = String.valueOf(getDivident().intValue());
			int remainder = 0;
			int quotient = 0;
			int intermediateDivident;
			while(!dividentString.isEmpty()) {
				for(int i = 0, j = dividentString.length(); i < j; ++i) {
					intermediateDivident = getNumberFromString(dividentString.substring(0, i + 1));
					IntegerDivision division = new IntegerDivision(intermediateDivident, getDivider().intValue());
					division.makeDivision();
					remainder = division.getRemainder().intValue();
					quotient  = division.getQuotient().intValue();
					if(!division.isZeroQuotient()) {
						divisionSteps.add(intermediateDivident);							
						divisionSteps.add(quotient * division.getDivider().intValue());
						dividentString = remainder + dividentString.substring(i + 1);
						break;
					} else if(i + 1 == j ) {
						divisionSteps.add(intermediateDivident);
						dividentString = "";
					}
				}
			}
		}
	}	
	
	@Override
	public void makeDivision() {
		validateDivision();
		if(isDivisionCorrectness()) {
			setQuotient(getDivident().intValue() / getDivider().intValue());
			setRemainder(getDivident().intValue() - getDivider().intValue() * getQuotient().intValue());
		}
	}

	private int getNumberFromString(String stringOfNumber) {
		return Integer.valueOf(stringOfNumber);
	}
	
	public boolean isZeroQuotient() {
		return getQuotient().intValue() == 0;
	}	
	
	public boolean isZeroRemainder() {
		return getRemainder().intValue() == 0;
	}

	public Queue<Integer> getDivisionSteps() {
		return divisionSteps.stream().collect(ArrayDeque::new, ArrayDeque::add, ArrayDeque::addAll);
	}
	
	public Queue<String> getDivisionStepsAsString() {
		return divisionSteps.stream().map(element -> element.toString()).collect(ArrayDeque::new, ArrayDeque::add, ArrayDeque::addAll);
	}
	
	private void resetDivisionSteps() {
		this.divisionSteps.clear();
	}
	
}
