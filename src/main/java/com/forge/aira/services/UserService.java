package com.forge.aira.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.forge.aira.dtos.UserDto;
import com.forge.aira.wrappers.UserDtoList;

@Service
public class UserService {

    String host = "http://localhost:5000";

    public void getUsers(Model model) {

        String uri = "/get-all-users";
        System.out.println("getting user details!");
        RestTemplate template = new RestTemplate();
        UserDtoList dto = template.getForObject(host + uri, UserDtoList.class);
        System.out.println(dto == null);

        List<UserDto> users = new ArrayList<>();
        users = dto.getUsers();

        users.forEach(user -> {
            System.out.println("UserName: " + user.UserName);
        });

        model.addAttribute("user_list", users);
    }
}
