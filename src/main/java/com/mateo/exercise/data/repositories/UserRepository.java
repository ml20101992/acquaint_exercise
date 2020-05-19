package com.mateo.exercise.data.repositories;

import com.mateo.exercise.data.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findUserByUsername(String username);
}
