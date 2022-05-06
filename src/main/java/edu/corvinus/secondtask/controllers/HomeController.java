package edu.corvinus.secondtask.controllers;

import edu.corvinus.secondtask.models.LoginForm;
import edu.corvinus.secondtask.models.RegistrationForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHome(){
        return "index";
    }

    @GetMapping("/login")
    public String showLoginform(LoginForm loginForm){
        return "login";
    }

    @GetMapping("/registration")
    public String showRegistrationform(RegistrationForm registrationForm){
        return "registration";
    }

}
