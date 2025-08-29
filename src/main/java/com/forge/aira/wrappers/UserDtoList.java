package com.forge.aira.wrappers;

import java.util.ArrayList;
import java.util.List;

import com.forge.aira.dtos.UserDto;

import lombok.Data;

@Data
public class UserDtoList {

    private List<UserDto> users;

    public UserDtoList() {
        users = new ArrayList<>();
    }
}
