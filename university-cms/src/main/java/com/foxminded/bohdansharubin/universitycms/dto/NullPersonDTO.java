package com.foxminded.bohdansharubin.universitycms.dto;

public class NullPersonDTO extends PersonDTO {

	public NullPersonDTO() {
		super(0, "", "", 0, "", "", null);
	}

	@Override
	public boolean isNull() {
		return true;
	}

}
