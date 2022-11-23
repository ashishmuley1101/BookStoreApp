package com.bridgelabz.bookstore.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public @ToString class UserLoginDTO {


    public String email;

    public String password;


}
