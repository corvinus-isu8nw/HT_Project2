package edu.corvinus.secondtask.controllers;

import edu.corvinus.secondtask.models.LoginForm;
import edu.corvinus.secondtask.models.User;
import edu.corvinus.secondtask.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final UserRepository repository;

    @Autowired
    public LoginController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/login")
    public String login(@Valid LoginForm loginForm, BindingResult bindingResult){

        final boolean userIsRegistered = repository.findByUsername(loginForm.getUsername()).isPresent();
        if (!userIsRegistered){
            logger.info("User not found!");
            bindingResult.rejectValue("username",null);
            return "login";
        } else {
            Optional<User> foundUser = repository.findByUsername(loginForm.getUsername());
            final User user = foundUser.get();
            String storedPassword = user.getPassword();
            if (!Objects.equals(storedPassword, String.valueOf(loginForm.getPassword().hashCode()))){
                logger.info("Wrong password");
                bindingResult.rejectValue("password",null);
                return "login";

            }
            else {
                logger.info("Login successful");
                return "redirect:/welcome?username=" + loginForm.getUsername();
            }
        }
    }
}
