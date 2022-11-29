package com.bridgelabz.bookstore.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Generated;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Generated
@Setter
public @ToString class UserDTO {

    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$",message = " Invalid First name..!")
    public String firstName;

    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$",message = " Invalid last name..!")
    public String lastName;

    @NotNull(message = "Email Cannot Be Empty")
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message = "Email Is Invalid")
    public String email;

    @NotNull(message = "Address cannot be null")
    public String address;

    @JsonFormat(pattern = "dd MMM yyyy")
    @NotNull(message = "Date of Birth Cannot be Empty")
    public LocalDate dob;

    public String password;

    public String verificationCode;

    public boolean enabled;

}
