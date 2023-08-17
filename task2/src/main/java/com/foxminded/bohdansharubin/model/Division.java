package com.foxminded.bohdansharubin.model;

public abstract class Division {
	
	private Number divident;
	private Number divider;
	private Number quotient;
	private Number remainder;
	private boolean	divisionCorrectness;
	
	protected Division() {
	}

	protected Division(Number divident, Number divider) {
		this.divident = divident;
		this.divider = divider;
		validateDivision();
	}
	
	public abstract void makeDivision();
	
	public abstract boolean validateDivision();
	
	public void resetQuotientAndRemainder() {
		this.quotient = 0;
		this.remainder = 0;
	}

	public Number getDivident() {
		return divident;
	}

	public final void setDivident(Number divident) {
		this.divident = divident;
		setDivisionCorrectness(false);
		resetQuotientAndRemainder();
	}

	public Number getDivider() {
		return divider;
	}
	
	public final void setDivider(Number divider) {
		this.divider = divider;
		setDivisionCorrectness(false);
		resetQuotientAndRemainder();
	}

	public Number getQuotient() {
		return quotient;
	}

	protected final void setQuotient(Number quotient) {
		this.quotient = quotient;	
	}

	public Number getRemainder() {
		return remainder;
	}

	protected final void setRemainder(Number remainder) {
		this.remainder = remainder;
	}

	
	public boolean isDivisionCorrectness() {
		return divisionCorrectness;
	}

	
	protected final void setDivisionCorrectness(boolean divisionCorrectness) {
		this.divisionCorrectness = divisionCorrectness;
	}

}
