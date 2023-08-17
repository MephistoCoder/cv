package com.foxminded.bohdansharubin.universitycms.dto;

public class NullUserDTO extends UserDTO {

	public NullUserDTO() {
		super(0, "", "", null);
	}

	@Override
	public boolean isNull() {
		return true;
	}
	
}
