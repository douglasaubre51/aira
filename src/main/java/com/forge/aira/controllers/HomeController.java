package com.forge.aira.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.view.RedirectView;

import com.forge.aira.dtos.PrimaryFormDto;
import com.forge.aira.dtos.SecondaryFormDto;
import com.forge.aira.services.ApiService;
import com.forge.aira.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class HomeController {

    @Value("${WAGURI_BASE_URI}")
    private String host;
    private String errorMessage;

    private final UserService _userService;
    private final ApiService _apiService;

    public HomeController(
            UserService userService,
            ApiService apiService) {

        _userService = userService;
        _apiService = apiService;
        errorMessage = new String();
    }

    // home view
    @GetMapping("/")
    public String getHomeView(Model model) {
        try {

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

            model.addAttribute("msg", ex.getMessage());

            System.out.println("getHomeView error: " + ex.getMessage());
            return "error";
        }
    }

    @GetMapping("/clear-error-messages")
    public RedirectView getMethodName() {
        errorMessage = "";
        return new RedirectView("/");
    }

    @PostMapping("/remove-user")
    public RedirectView removeUser(
            @ModelAttribute("FieldDto") SecondaryFormDto dto,
            Model model) {
        try {

            System.out.println("removing user: " + dto.getUserName());
            boolean result = _userService.removeUserByUserName(dto.getUserName());
            if (result == false) {
                errorMessage = "user can't be removed!";
                return new RedirectView("/");
            }

            System.out.println("user" + dto.getUserName() + " removed successfully!");
            return new RedirectView("/");
        } catch (Exception ex) {

            errorMessage = "removeUser error: " + ex.getMessage();
            return new RedirectView("/");
        }
    }

    @PostMapping("/create-user")
    public RedirectView createUser(@ModelAttribute("create_user_form") PrimaryFormDto dto) {
        try {

            boolean result = _userService.createNewUser(dto);
            if (result == false) {
                errorMessage = "couldnt create new user!";
                return new RedirectView("/");
            }

            System.out.println("user" + dto.getEmail() + " created successfully!");
            return new RedirectView("/");
        } catch (Exception ex) {
            errorMessage = "createUser error: " + ex.getMessage();
            return new RedirectView("/");
        }
    }

    @PostMapping("/confirm-user")
    public RedirectView confirmUser(
            @ModelAttribute("confirm_user_form") SecondaryFormDto dto) {
        try {

            System.out.println("confirming user: " + dto.getUserName());
            boolean result = _userService.confirmUserByUserName(dto.getUserName());
            if (result == false) {
                errorMessage = "user can't be confirmed!";
                return new RedirectView("/");
            }

            System.out.println(
                    "user " + dto.getUserName()
                            + " email has been sent to " + dto.UserName + " !");

            return new RedirectView("/");

        } catch (Exception ex) {

            errorMessage = "confirmUser error: " + ex.getMessage();
            return new RedirectView("/");
        }
    }
}