package org.example.springsecurity_amigos_jwt.repository;

import org.example.springsecurity_amigos_jwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmailAndStatus(String email , Integer status);

}
