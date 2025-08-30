package com.forge.aira.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.forge.aira.dtos.PrimaryFormDto;
import com.forge.aira.dtos.UserDto;
import com.forge.aira.wrappers.UserDtoList;

@Service
public class UserService {
    private final RestTemplate _template;

    public UserService() {
        _template = new RestTemplate();
    }

    public List<UserDto> getUsers(String host) {

        String uri = "/get-all-users";
        RestTemplate template = new RestTemplate();
        UserDtoList dto = template.getForObject(host + uri, UserDtoList.class);

        return dto.getUsers();
    }

    public boolean removeUserByUserName(
            String userName,
            String host) {

        String uri = "/user/delete/" + userName;

        ResponseEntity<Object> response = _template.getForEntity(host + uri, Object.class);
        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode.is2xxSuccessful() == false) {
            return false;
        }

        return true;
    }

    public boolean createNewUser(
            PrimaryFormDto dto,
            String host) {

        String uri = "/sign-up";
        ResponseEntity<PrimaryFormDto> response = _template
                .postForEntity(host + uri, dto, PrimaryFormDto.class);
        if (response.getStatusCode().is2xxSuccessful() == false) {
            return false;
        }

        return true;
    }

    public boolean confirmUserByUserName(
            String userName,
            String host) {

        String uri = "/user/confirm/" + userName;

        ResponseEntity<Object> response = _template.getForEntity(host + uri, Object.class);
        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode.is2xxSuccessful() == false) {
            return false;
        }

        return true;
    }
}
