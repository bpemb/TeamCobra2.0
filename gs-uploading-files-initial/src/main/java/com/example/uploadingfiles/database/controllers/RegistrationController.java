package com.example.uploadingfiles.database.controllers;

import com.example.uploadingfiles.database.Users;
import com.example.uploadingfiles.database.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
//@RequestMapping("/registration")
//@AllArgsConstructor
public class RegistrationController{

    @Autowired
    UserService userService;

        @RequestMapping(value = "/register", method = RequestMethod.GET)
        public ModelAndView register()
        {
            ModelAndView modelAndView = new ModelAndView();
            Users users = new Users();
            modelAndView.addObject("users", users);
            modelAndView.setViewName("register");
            return modelAndView;
        }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public ModelAndView registerUser(@Valid Users users, BindingResult bindingResult, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView();

        if(bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "Please correct the errors in form!");
            modelMap.addAttribute("bindingResult", bindingResult);
        }
        else if(userService.isUserAlreadyPresent(users)){
            modelAndView.addObject("successMessage", "user already exists!");
        }
        else {
            users.setRole("USER");
            userService.saveUser(users);
            modelAndView.addObject("successMessage", "User is registered successfully!");
        }
        modelAndView.addObject("user", new Users());
        modelAndView.setViewName("register");
        return modelAndView;
    }

}
