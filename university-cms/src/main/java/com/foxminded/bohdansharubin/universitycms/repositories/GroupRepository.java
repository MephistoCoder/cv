package com.foxminded.bohdansharubin.universitycms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.bohdansharubin.universitycms.models.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
	Optional<Group> findByName(String name);
}
