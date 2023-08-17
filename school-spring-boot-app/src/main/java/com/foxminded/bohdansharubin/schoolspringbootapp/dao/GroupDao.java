package com.foxminded.bohdansharubin.schoolspringbootapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.bohdansharubin.schoolspringbootapp.model.Group;

@Repository
public interface GroupDao extends JpaRepository<Group, Integer> {

}
