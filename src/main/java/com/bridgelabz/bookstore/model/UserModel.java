package com.bridgelabz.bookstore.model;

import com.bridgelabz.bookstore.dto.UserDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "book_store")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    }

}
