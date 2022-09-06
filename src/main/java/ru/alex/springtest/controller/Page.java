package ru.alex.springtest.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/calculator")
    public String calculator(@RequestParam("a") int a, @RequestParam("b") int b,
                              @RequestParam("action") String action,
                              Model model){

        double result;

        switch (action){
            case "mul": result = a * b;
            break;
            case "add": result = a + b;
            break;
            case "sub": result = a - b;
            break;
            case "div": result = a / b;
            break;

            default: result = 0;
            break;
        }

        model.addAttribute("action",result);

        return "/calculator";

    }

}
