package com.forge.aira.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.view.RedirectView;

import com.forge.aira.dtos.EmailDto;
import com.forge.aira.dtos.PrimaryFormDto;
import com.forge.aira.dtos.UserDto;
import com.forge.aira.services.ApiService;
import com.forge.aira.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Value("${WAGURI_BASE_URI}")
    private String host;
    private String message;

    private final UserService _userService;
    private final ApiService _apiService;

    public HomeController(
            UserService userService,
            ApiService apiService) {

        _userService = userService;
        _apiService = apiService;
        message = new String();
    }

    // home view
    @GetMapping("/")
    public String getHomeView(Model model) {
        try {

            // init attributes
            model.addAttribute("message", message);
            model.addAttribute("primary_form_dto", new PrimaryFormDto());
            model.addAttribute("email_dto", new EmailDto());

            boolean result = _apiService.getApiStatus();
            if (!result)
                model.addAttribute("waguri_status", "lost");
            else
                model.addAttribute("waguri_status", "live");

            return "index";

        } catch (ResourceAccessException ex) {

            model.addAttribute("waguri_status", "lost");

            System.out.println("conn lost: " + ex.getMessage());
            return "index";

        } catch (Exception ex) {

            model.addAttribute("message", ex.getMessage());

            System.out.println("getHomeView error: " + ex.getMessage());
            return "error";
        }
    }

    @PostMapping("/user/create")
    public RedirectView createUser(@ModelAttribute("primary_form_dto") PrimaryFormDto dto) {
        try {

            System.out.println("triggered create user!");
            boolean result = _userService.createNewUser(dto);
            if (result == false)
                message += "couldnot create new user!";
            else
                message += "user created successfully!";

            return new RedirectView("/");

        } catch (Exception ex) {

            message += ex.getMessage();
            return new RedirectView("/");
        }

    }

    @PostMapping("/user/remove")
    public RedirectView removeUser(@ModelAttribute("email_dto") EmailDto dto) {
        try {

            System.out.println(dto.getEmail());

            return new RedirectView("/");

        } catch (Exception ex) {

            System.out.println("removeUser error: " + ex.getMessage());
            message += ex.getMessage();
            return new RedirectView("/");
        }
    }

    @GetMapping("/user/view")
    public String getUserView(Model model) {
        try {

            List<UserDto> users = _userService.getUsers();
            model.addAttribute("user_list", users);

            return "users";

        } catch (ResourceAccessException ex) {
            return "users_error";
        } catch (Exception ex) {

            model.addAttribute("message", ex.getMessage());

            System.out.println("getHomeView error: " + ex.getMessage());
            return "error";
        }
    }

    @GetMapping("/message/clear")
    public RedirectView clearMessages() {

        message = "";
        return new RedirectView("/");
    }
}