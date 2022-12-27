package com.bridgelabz.bookstore.controller;


import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.dto.UserLoginDTO;
import com.bridgelabz.bookstore.model.UserModel;
import com.bridgelabz.bookstore.service.IUserService;
import com.bridgelabz.bookstore.service.UserService;
import com.bridgelabz.bookstore.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
//@RequestMapping("/bookstore")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    @RequestMapping("/getAll")
    public ResponseEntity<ResponseDTO> getUserModelData() throws Exception{
        List<UserModel> userModelDataList;
        userModelDataList = userService.getUserModelData();
        ResponseDTO respDTO = new ResponseDTO("Get Call Successful", userModelDataList);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<ResponseDTO> getUserModelData(@PathVariable("userId") int userId) {
        UserModel userModelData;
        userModelData = userService.getUserModelDataById(userId);
        ResponseDTO respDTO = new ResponseDTO("Get Call for Id Successful ", userModelData);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ResponseDTO> getUserModelDataByEmail(@PathVariable("email") String email) {
        UserModel userModelDataList = null;
        userModelDataList = userService.getUserModelDataByEmailId(email);
        ResponseDTO respDTO = new ResponseDTO("Getting all user records successfully..!", userModelDataList);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<ResponseDTO> createUserDataData(@Valid @RequestBody UserDTO userDTO) throws MessagingException {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        String status = userService.createUserModelData(userDTO);
        ResponseDTO respDTO = new ResponseDTO("Created User Data Successfully ", status);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ResponseDTO> updateUserModelData(@PathVariable("userId") int userId, @Valid @RequestBody UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserModel userModelData = null;
        userModelData = userService.updateUserModelData(userId, userDTO);
        ResponseDTO respDTO = new ResponseDTO("Updated User Data Successfully", userDTO);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

    //Delete User data using @DeleteMapping Method
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ResponseDTO> deleteAddressBookData(@PathVariable("userId") int userId) {
        userService.deleteUserModelData(userId);
        ResponseDTO respDTO = new ResponseDTO("Deleted Successfully", "Deleted id: " + userId);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> generateToken(@RequestBody UserLoginDTO userLoginDTO) throws Exception {
        UserModel userModel =userService.login(userLoginDTO);
        ResponseDTO responseDTO = new ResponseDTO("User login Successfully..!", userModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<ResponseDTO> verifyUser(@Param("code") String code) {
        String verify;
        if (userService.verify(code)) {
            verify="Verification Successfull";
        } else {
            verify=" Failed It maybe already verified or verification code is incorrect.";

        }
        ResponseDTO respDTO = new ResponseDTO("Verification ", verify);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

}