package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.model.CustomUserDetails;
import com.bridgelabz.bookstore.model.UserModel;
import com.bridgelabz.bookstore.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userModelData = userRepository.findByEmail(email);

        if (userModelData == null) {
            throw new UsernameNotFoundException("Bad credentials");
        } else {

            return new CustomUserDetails(userModelData);

//            UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(userModelData.getEmail()).password(userModelData.getPassword()).authorities("USER").build();
//            return userDetails;
        }
    }
}
