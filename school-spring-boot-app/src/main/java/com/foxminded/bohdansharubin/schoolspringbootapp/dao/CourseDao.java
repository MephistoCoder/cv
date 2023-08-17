package com.foxminded.bohdansharubin.schoolspringbootapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.bohdansharubin.schoolspringbootapp.model.Course;

@Repository
public interface CourseDao extends JpaRepository<Course, Integer> {
	Optional<Course> findByName(String name);

}
