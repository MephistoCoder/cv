package com.foxminded.bohdansharubin.universitycms.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.foxminded.bohdansharubin.universitycms.convertor.ModelConvertor;
import com.foxminded.bohdansharubin.universitycms.dto.NullPersonDTO;
import com.foxminded.bohdansharubin.universitycms.dto.PersonDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractPersonService {
	
	protected ModelConvertor modelConvertor;
	
	@Autowired
	protected AbstractPersonService(ModelConvertor modelConvertor) {
		this.modelConvertor = modelConvertor;
	}

	public abstract PersonDTO save(PersonDTO dto);
	
	public abstract List<PersonDTO> findAll();
	
	@Transactional
	public PersonDTO update(PersonDTO dto) {
		if(!existsById(dto.getId())) {
			log.warn("Person for updating with id = {} not found", dto.getId());
			return new NullPersonDTO();
		}
		log.info("Person updating = {}", dto);
		return save(dto);
	}
	
	public abstract void deleteById(int id);
	
	protected abstract boolean existsById(int id);
	
}
