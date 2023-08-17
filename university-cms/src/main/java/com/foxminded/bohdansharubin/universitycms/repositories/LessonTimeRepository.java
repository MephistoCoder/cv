package com.foxminded.bohdansharubin.universitycms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.bohdansharubin.universitycms.models.LessonTime;

@Repository
public interface LessonTimeRepository extends JpaRepository<LessonTime, Integer> {

}
