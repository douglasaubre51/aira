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
        RestTemplate template = new RestTemplate();
        UserDtoList dto = template.getForObject(host + uri, UserDtoList.class);

        List<UserDto> users = new ArrayList<>();
        users = dto.getUsers();

        model.addAttribute("user_list", users);
    }
}
