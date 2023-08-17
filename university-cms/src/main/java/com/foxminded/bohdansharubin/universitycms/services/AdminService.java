package com.foxminded.bohdansharubin.universitycms.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.bohdansharubin.universitycms.convertor.ModelConvertor;
import com.foxminded.bohdansharubin.universitycms.dto.NullPersonDTO;
import com.foxminded.bohdansharubin.universitycms.dto.PersonDTO;
import com.foxminded.bohdansharubin.universitycms.models.Admin;
import com.foxminded.bohdansharubin.universitycms.repositories.AdminRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminService extends AbstractPersonService {
	
	private AdminRepository adminRepository;
	
	@Autowired
	public AdminService(AdminRepository adminRepository,
		ModelConvertor modelConvertor) {
		super(modelConvertor);
		this.adminRepository = adminRepository;
	}
	
	public List<PersonDTO> findAll() {
		log.debug("Finding all admins");
		return modelConvertor.convertToPersonDtoList(adminRepository.findAll());
	}
	
	@Transactional
	public PersonDTO save(PersonDTO personDTO) {
		Admin adminForSaving = modelConvertor.convertToAdmin(personDTO);
		if(!adminForSaving.isValid()) {
			log.warn("Can't save admin with incorrect data = {}", adminForSaving);
			return new NullPersonDTO();
		} 
		log.info("Created new admin = {}", adminForSaving);
		return modelConvertor.convertToPersonDto(adminRepository.save(adminForSaving));
	}
	
	@Transactional
	public void deleteById(int id) {
		log.debug("Id for deleting admin = {}", id);
		adminRepository.deleteById(id);
	}
	
	protected boolean existsById(int id) {
		log.debug("Id for checking existence of admin = {}", id);
		return adminRepository.existsById(id);
	}
		
}
