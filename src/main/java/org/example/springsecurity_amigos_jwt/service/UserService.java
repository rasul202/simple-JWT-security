package org.example.springsecurity_amigos_jwt.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springsecurity_amigos_jwt.constants.StatusConstants;
import org.example.springsecurity_amigos_jwt.entity.UserEntity;
import org.example.springsecurity_amigos_jwt.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class UserService {

    UserRepository userRepository;

    public UserEntity findUserByEmail(String email){
        return userRepository.findByEmailAndStatus(email , StatusConstants.ACTIVE.getStatus())
                .orElseThrow(()->  new UsernameNotFoundException("there is no user with this " + email + " email"));
    }

    public void saveUserToDb(UserEntity user){
        userRepository.save(user);
    }

}
