package com.forge.aira.dtos;

import lombok.Data;

@Data
public class UserDto {

    public String UserName;
    public String Email;
    public boolean EmailConfirmed;

    public String FirstName;
    public String LastName;

    public String ProjectId;
}
