package com.forge.aira.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.forge.aira.services.UserService;

@Controller
public class HomeController {

    String host = "http://localhost:5000";

    private final UserService _userService;

    public HomeController(UserService userService) {
        _userService = userService;
    }

    @GetMapping
    public String getHomeView(Model model) {
        try {
            // call waguri api
            String uri = "/hello";
            RestTemplate template = new RestTemplate();
            String result = template.getForObject(host + uri, String.class);
            model.addAttribute("waguri_status", result);

            // fetch and set user details
            _userService.getUsers(model);
            // load html
            return "index";

        } catch (ResourceAccessException ex) {
            model.addAttribute("waguri_status", "dead");
            return "index";

        } catch (Exception ex) {
            System.out.println("getHomeView error:\n" + ex.getMessage());
            return "index";
        }
    }
}