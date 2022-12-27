package com.bridgelabz.bookstore.model;

import com.bridgelabz.bookstore.dto.UserDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "password")
    private String password;

    @Column(name = "verification_code", length = 32)
    private String verificationCode;

    private boolean enabled;

    @Column(name = "token")
    private String token;

    public UserModel(UserDTO userDTO) {

        this.updateUserModelData(userDTO);
    }

    public UserModel() {

    }

    public void updateUserModelData(UserDTO userDTO) {
        this.userId = userId;
        this.firstName= userDTO.firstName;
        this.lastName=userDTO.lastName;
        this.dob=userDTO.dob;
        this.address=userDTO.address;
        this.email=userDTO.email;
        this.password=userDTO.password;
        this.verificationCode=userDTO.verificationCode;
        this.enabled=userDTO.enabled;
        this.token=userDTO.getToken();

    }

}
