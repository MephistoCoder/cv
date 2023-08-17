package com.foxminded.bohdansharubin.universitycms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.bohdansharubin.universitycms.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	List<User> findAllByOrderByIdAsc();
	Optional<User> findByUsername(String username);
}
