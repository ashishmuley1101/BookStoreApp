package com.bridgelabz.bookstore.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public @ToString class UserLoginDTO {


    @NotNull(message = "Email Cannot Be Empty")
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message = "Email Is Invalid")
    public String email;

    @NotEmpty(message = "Password Field can't be Empty")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%!]).{8,}$", message = "Invalid Password")
    public String password;


}
