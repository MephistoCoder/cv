package com.foxminded.bohdansharubin.universitycms.dto;

import com.foxminded.bohdansharubin.universitycms.models.Roles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

	private int id;
	private String firstName;
	private String lastName;
	private int userId;
	private String userUsername;
	private String userPassword = "";
	private Roles userRole;
	
	public boolean isNull() {
		return false;
	}
	
}
