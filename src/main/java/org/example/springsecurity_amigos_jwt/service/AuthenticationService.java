package org.example.springsecurity_amigos_jwt.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springsecurity_amigos_jwt.entity.UserEntity;
import org.example.springsecurity_amigos_jwt.enums.Role;
import org.example.springsecurity_amigos_jwt.mapper.UserMapper;
import org.example.springsecurity_amigos_jwt.model.request.AuthenticateRequest;
import org.example.springsecurity_amigos_jwt.model.request.RegisterRequest;
import org.example.springsecurity_amigos_jwt.model.response.AuthenticationResponse;
import org.example.springsecurity_amigos_jwt.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class AuthenticationService {

    UserService  userService;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;
    AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {
        UserEntity user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userService.saveUserToDb(user);

        String jwtToken = jwtService.generateJwtTokenWithoutExtraClaims(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    public AuthenticationResponse authenticate(AuthenticateRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                 request.getEmail()
                ,request.getPassword()));

        UserEntity user = userService.findUserByEmail(request.getEmail());
        String jwtToken = jwtService.generateJwtTokenWithoutExtraClaims(user);

        return AuthenticationResponse.builder().token(jwtToken).build();

    }

}
