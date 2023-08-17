package com.foxminded.bohdansharubin.universitycms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.bohdansharubin.universitycms.models.Audience;

@Repository
public interface AudienceRepository extends JpaRepository<Audience, Integer> {
	Optional<Audience> findByName(String name);
}
