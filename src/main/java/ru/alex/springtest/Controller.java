package ru.alex.springtest;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/hello-world")
    public String helloWorld(){
        return "hello_world";
    }

}
