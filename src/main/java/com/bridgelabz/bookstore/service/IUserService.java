package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.dto.UserLoginDTO;
import com.bridgelabz.bookstore.model.UserModel;

import javax.mail.MessagingException;
import java.util.List;

public interface IUserService {

        List<UserModel> getUserModelData();

        UserModel getUserModelDataById(int userId);


        String createUserModelData(UserDTO userDTO) throws MessagingException;


        void deleteUserModelData(int userId);

        List<UserModel> getUserModelDataByEmailId(String email);


        UserModel updateUserModelData(int userId, UserDTO userDTO);

        String login(UserLoginDTO userLoginDTO) throws Exception;

        boolean verify(String code);
}
