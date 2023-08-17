package com.foxminded.bohdansharubin.universitycms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.bohdansharubin.universitycms.models.Group;
import com.foxminded.bohdansharubin.universitycms.repositories.GroupRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GroupService {

	private GroupRepository groupRepository;
	
	@Autowired
	public GroupService(GroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}
	
	@Transactional
	public Optional<Group> save(String name) {
		Group groupForSaving = Group.builder()
			.name(name)
			.build();
		if(!groupForSaving.isValid()) {
			log.warn("Can't create group with incorrect name");
			return Optional.empty();
		}
		log.debug("Group = {} successfully created", groupForSaving);
		return Optional.of(groupRepository.save(groupForSaving));
	}
	
	public Optional<Group> findById(int id) {
		log.debug("Id = {} for finding group", id);
		return Optional.ofNullable(groupRepository.getReferenceById(id));
	}
	
	public List<Group> findAll() {
		log.debug("Finding all groups");
		return groupRepository.findAll();
	}
	
	public Optional<Group> findByName(String name) {
		if(name == null) {
			log.warn("Name is null");
			return Optional.empty();
		}
		log.debug("Name = {} for finding group", name);
		return groupRepository.findByName(name);
	}
	
	@Transactional
	public Optional<Group> update(int id, String name) {
		if(!existsById(id)) {
			log.warn("Group with id = {} is not found", id);
			return Optional.empty();
		} 
		Group groupForUpdating = Group.builder()
			.id(id)
			.name(name)
			.build();
		if(!groupForUpdating.isValid()) {
			log.warn("Can't update group with incorrect name");
			return Optional.empty();			
		}
		log.debug("Group after updating = {}", groupForUpdating);
		return Optional.of(groupRepository.save(groupForUpdating));
	}
	
	@Transactional
	public void deleteById(int id) {
		log.debug("Id = {} for deleting group", id);
		groupRepository.deleteById(id);
	}
	
	private boolean existsById(int id) {
		log.debug("Id for checking existence = {}", id);
		return groupRepository.existsById(id);
	}

}
