package com.foxminded.bohdansharubin.universitycms.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.foxminded.bohdansharubin.universitycms.convertor.ModelConvertor;
import com.foxminded.bohdansharubin.universitycms.dto.PersonDTO;
import com.foxminded.bohdansharubin.universitycms.models.Person;
import com.foxminded.bohdansharubin.universitycms.models.Roles;
import com.foxminded.bohdansharubin.universitycms.models.User;

abstract class TestAbstractPersonService {
	
	@Autowired
	protected ModelConvertor convertor = new ModelConvertor();
	
	protected boolean isPersonAndDtoEqual(Person person, PersonDTO dto) {
		return person.getId() == dto.getId() &&
			person.getFirstName() == dto.getFirstName() &&
			person.getLastName() == dto.getLastName() &&
			person.getUser().getId() == dto.getUserId() &&
			person.getUser().getUsername() == dto.getUserUsername() &&
			person.getUser().getRole() == dto.getUserRole();
	}
	
	protected User getCorrectUser() {
		return User.builder()
			.id(1)
			.username("Username1234")
			.password("Pass1234")
			.role(Roles.ROLE_STUDENT)
			.build();
	}
}
