package com.foxminded.bohdansharubin.universitycms.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.foxminded.bohdansharubin.universitycms.models.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	@Min(0)
	private int id;
	
	@NotBlank
	private String username;
	
	@NotNull
	private String password = "";
	
	@NotNull
	private Roles role;
	
	public boolean isNull () {
		return false;
	}
	
}
