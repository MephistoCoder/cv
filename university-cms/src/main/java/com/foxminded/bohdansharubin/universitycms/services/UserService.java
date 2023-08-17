package com.foxminded.bohdansharubin.universitycms.services;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.foxminded.bohdansharubin.universitycms.convertor.ModelConvertor;
import com.foxminded.bohdansharubin.universitycms.dto.NullUserDTO;
import com.foxminded.bohdansharubin.universitycms.dto.UserDTO;
import com.foxminded.bohdansharubin.universitycms.models.User;
import com.foxminded.bohdansharubin.universitycms.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService {

	private UserRepository userRepository;
	private ModelConvertor modelConvertor;
	
	@Autowired
	public UserService(UserRepository userRepository,
		ModelConvertor modelConvertor) {
		this.userRepository = userRepository;
		this.modelConvertor = modelConvertor;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("Try to load user with username = {}", username);
		return userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException(username));
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<UserDTO> findAll() {
		log.debug("Finding all users");
		return modelConvertor.convertToUserDtoList(userRepository.findAllByOrderByIdAsc());
	}
	
	public UserDTO findById(int id) {
		log.debug("Id for finding user = {}", id);
		return modelConvertor.convertToUserDto(userRepository.getReferenceById(id));
	}
	
	@Transactional
	@PreAuthorize("hasAuthority('ADMIN')")
	public UserDTO save(@Valid UserDTO dto) {
		User userForSaving = modelConvertor.convertToUser(dto);
		if(!userForSaving.isValid()) {
			log.warn("Can't create user with invalid data, user = {}", userForSaving);
			return new NullUserDTO();
		}
		return modelConvertor.convertToUserDto(userRepository.save(userForSaving));
	}
	
	@Transactional
	@PreAuthorize("hasAuthority('ADMIN')")
	public UserDTO update(@Valid UserDTO dto) {
		if(!existsById(dto.getId())) {
			log.warn("Can't find user for updating with id = {}", dto.getId());
			return new NullUserDTO();
		}
		User userForUpdating = modelConvertor.convertToUser(dto);
		if(dto.getPassword()
			.trim()
			.isEmpty()) {
			userForUpdating.setPassword(userRepository.getReferenceById(dto.getId()).getPassword());			
		}
		if(!userForUpdating.isValid()) {
			return new NullUserDTO();
		}
		log.info("User was updated, user = {}", userForUpdating);
		return modelConvertor.convertToUserDto(userRepository.save(userForUpdating));
	}
	
	private boolean existsById(int id) {
		log.debug("Id for checking existence of user = {}", id);
		return userRepository.existsById(id);
	}
}
