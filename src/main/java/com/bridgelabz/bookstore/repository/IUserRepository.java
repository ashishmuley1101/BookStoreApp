package com.bridgelabz.bookstore.repository;


import com.bridgelabz.bookstore.model.CartModel;
import com.bridgelabz.bookstore.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserModel,Integer> {

    @Query(value = "select * from user_model WHERE email =:email",nativeQuery = true)
    List<UserModel> findUserModelDataByEmailId(String email);


    UserModel findByEmail(String email);

    @Query(value = "select * from user_model WHERE verification_Code =:verificationCode",nativeQuery = true)
    UserModel findByVerificationCode(String verificationCode);



}
