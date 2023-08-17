package com.foxminded.bohdansharubin.universitycms.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.bohdansharubin.universitycms.models.Group;
import com.foxminded.bohdansharubin.universitycms.models.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
	List<Lesson> findByDateBetweenAndTeacherId(LocalDate start, LocalDate end, int teacherId);
	List<Lesson> findByDateBetweenAndGroups(LocalDate start, LocalDate end, Group group);
	List<Lesson> findByDateAndTeacherId(LocalDate date, int teacherId);
	List<Lesson> findByDateAndGroups(LocalDate date, Group group);
	
}
