package com.foxminded.bohdansharubin.universitycms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.bohdansharubin.universitycms.models.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
	List<Teacher> findDistinctByIdIn(List<Integer> ids);

}
