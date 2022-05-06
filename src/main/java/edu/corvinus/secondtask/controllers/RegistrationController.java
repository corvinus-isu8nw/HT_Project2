package edu.corvinus.secondtask.controllers;

import edu.corvinus.secondtask.models.LoginForm;
import edu.corvinus.secondtask.models.RegistrationForm;
import edu.corvinus.secondtask.models.User;
import edu.corvinus.secondtask.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.slf4j.Logger;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class RegistrationController {
    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final UserRepository repository;

    @Autowired
    public RegistrationController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/registration")
    public String register(@Valid RegistrationForm registrationForm, BindingResult bindingResult){

        logger.info("Registering user with username: {}",registrationForm.getUsername());
        final boolean userIsRegistered = repository.findByUsername(registrationForm.getUsername()).isPresent();

        if (bindingResult.hasErrors()){
            logger.info("Validation errors occured!");
            return "registration";
        } else if (userIsRegistered) {
            logger.info("Already exists");
            bindingResult.rejectValue("username",null);
            return "registration";
        }
        else {
            logger.info("Registered user with username: {}",registrationForm.getUsername());
            repository.save(new User(registrationForm.getUsername(),registrationForm.getRealname(),String.valueOf(registrationForm.getPassword().hashCode())));
            return "redirect:/login";
        }
    }

    @GetMapping("/welcome")
    public String showWelcomePage(@RequestParam(required = false) String username, Model model){
        final Optional<User> foundUser = repository.findByUsername(username);
        if (foundUser.isEmpty()){
            return "redirect:/";
        }
        return "welcome";
    }




}
