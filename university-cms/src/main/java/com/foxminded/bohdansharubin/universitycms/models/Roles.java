package com.foxminded.bohdansharubin.universitycms.models;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority{
	ROLE_ADMIN("ADMIN"),
	ROLE_TEACHER("TEACHER"),
	ROLE_STUDENT("STUDENT");

	Roles(String authority) {
		this.authority = authority;
	}
	
	private String authority;
	
	@Override
	public String getAuthority() {
		return authority;
	}
	
}
