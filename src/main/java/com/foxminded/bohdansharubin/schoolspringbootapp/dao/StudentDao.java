package com.foxminded.bohdansharubin.schoolspringbootapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.bohdansharubin.schoolspringbootapp.model.Group;
import com.foxminded.bohdansharubin.schoolspringbootapp.model.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Integer> {
	
	@Transactional
	@Modifying
	@Query("update Student student set student.group = :group where student.id = :studentId")
	void update(@Param("studentId") int studentId, @Param("group") Group group);
}
