package org.example.springsecurity_amigos_jwt.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springsecurity_amigos_jwt.model.request.AuthenticateRequest;
import org.example.springsecurity_amigos_jwt.model.request.RegisterRequest;
import org.example.springsecurity_amigos_jwt.model.response.AuthenticationResponse;
import org.example.springsecurity_amigos_jwt.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest request){
        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody AuthenticateRequest request){
        return authenticationService.authenticate(request);
    }

}
