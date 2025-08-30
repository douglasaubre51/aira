package com.forge.aira.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.view.RedirectView;

import com.forge.aira.dtos.PrimaryFormDto;
import com.forge.aira.dtos.SecondaryFormDto;
import com.forge.aira.dtos.UserDto;
import com.forge.aira.services.ApiService;
import com.forge.aira.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    String host = "http://localhost:5000";
    String errorMessage;

    private final UserService _userService;
    private final ApiService _apiService;

    public HomeController(
            UserService userService,
            ApiService apiService) {
        _userService = userService;
        _apiService = apiService;
        errorMessage = new String();
    }

    @GetMapping("/")
    public String getHomeView(Model model) {
        try {
            String result = _apiService.getApiStatus(host);
            model.addAttribute("waguri_status", result);

            List<UserDto> users = _userService.getUsers(host);
            model.addAttribute("user_list", users);

            PrimaryFormDto createUserFormDto = new PrimaryFormDto();
            model.addAttribute("create_user_form", createUserFormDto);

            SecondaryFormDto confirmUserDto = new SecondaryFormDto();
            model.addAttribute("confirm_user_form", confirmUserDto);

            SecondaryFormDto formDto = new SecondaryFormDto();
            model.addAttribute("FieldDto", formDto);

            model.addAttribute("error_message", errorMessage);

            return "index";

        } catch (ResourceAccessException ex) {
            model.addAttribute("waguri_status", "lost");
            model.addAttribute("user_list", new ArrayList<UserDto>());
            model.addAttribute("create_user_form", new PrimaryFormDto());
            model.addAttribute("confirm_user_form", new SecondaryFormDto());
            model.addAttribute("FieldDto", new SecondaryFormDto());

            return "index";

        } catch (Exception ex) {
            System.out.println("getHomeView error:\n" + ex.getMessage());
            return new String("something went wrong!");
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
            boolean result = _userService.removeUserByUserName(dto.getUserName(), host);
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

            boolean result = _userService.createNewUser(dto, host);
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
            boolean result = _userService.confirmUserByUserName(dto.getUserName(), host);
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