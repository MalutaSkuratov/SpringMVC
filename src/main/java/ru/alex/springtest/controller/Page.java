package ru.alex.springtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class Page {

    @GetMapping("/first")
    public String firstPage(){
        return "/first";
    }

    @GetMapping("/second")
    public String secondPage(){
        return "/second";
    }

}
