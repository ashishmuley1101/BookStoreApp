package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.dto.UserLoginDTO;
import com.bridgelabz.bookstore.model.UserModel;

import java.util.List;

public interface IUserService {

        List<UserModel> getUserModelData();

        UserModel getUserModelDataById(int userId);


        UserModel createUserModelData(UserDTO userDTO);


        void deleteUserModelData(int userId);

        List<UserModel> getUserModelDataByEmailId(String email);



        UserModel updateUserModelData(int userId, UserDTO userDTO);


        String login(UserLoginDTO userLoginDTO) throws Exception;
}
