package com.foxminded.bohdansharubin.universitycms.models;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class Person {
	
	private static final String DEFAULT_NAME_PATTERN = "^[A-Z][a-zA-Z^\\s]+";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@Column
	@NotNull
	@Pattern(regexp = "^[A-Z][a-zA-Z^\\s]+")
	private String firstName;
	
	@Column
	@NotNull
	@Pattern(regexp = "^[A-Z][a-zA-Z^\\s]+")
	private String lastName;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	@NotNull
	private User user; 
	
	public boolean isValid() {
		return id>= 0 &&
			isValidFirstName() &&
			isValidLastName() &&
			user.isValid();
	}
	
	private boolean isValidFirstName() {
		return validateName(firstName);
	}
	
	private boolean isValidLastName() {
		return validateName(lastName);
	}
	
	private boolean validateName(String name) {
		if(name.matches(DEFAULT_NAME_PATTERN)) {
			return true;
		} 
		return false;
	}
	
}
