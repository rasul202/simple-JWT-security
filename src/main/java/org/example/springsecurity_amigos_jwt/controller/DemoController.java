package org.example.springsecurity_amigos_jwt.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class DemoController {

    @GetMapping("/demo1")
    public String demo1(){
        return "demo1";
    }

}
