package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.dto.UserLoginDTO;
import com.bridgelabz.bookstore.exception.BookStoreException;

import com.bridgelabz.bookstore.model.UserModel;
import com.bridgelabz.bookstore.repository.IUserRepository;
import com.bridgelabz.bookstore.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;


@Service
@Slf4j
public class UserService implements IUserService, UserDetailsService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    // ModelMapper class for convert the Entity Data object to DTO Object "vice versa".


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserModel> getUserModelData() {

        return userRepository.findAll();
    }

    @Override
    public UserModel getUserModelDataById(int userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new BookStoreException("Person with UserId " + userId + " does not exit..!"));
    }


//    @Override
//    public UserModel getUserModelDataByEmail(String email) {
//        return userRepository.findByEmail(email);
//
//    }

    // modelMapper use map() for conversion of object with arguments map(Source, destination).
    @Override
    public String createUserModelData(UserDTO userDTO) throws MessagingException {
        UserModel userData = modelMapper.map(userDTO, UserModel.class);
        String randomCode = RandomString.make(32);
        userData.setVerificationCode(randomCode);
        userData.setEnabled(false);
        userRepository.save(userData);

         return sendVerificationEmail(userData);

    }

    private String sendVerificationEmail(UserModel userModel)throws MessagingException {
        String toAddress = userModel.getEmail(); //"ashishmuley1101@gmail.com";
        String body= userModel.getVerificationCode();//"Hello from body";  //
        String fromAddress = "ashishmuley1101@gmail.com";
        String subject = "Please verify your registration...!";

        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(fromAddress);
            mailMessage.setTo(toAddress);
            mailMessage.setText(body);
            mailMessage.setSubject(subject);

            // Sending the mail
            mailSender.send(mailMessage);
            return "Mail Sent Successfully please check your mailbox ...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail...!";
        }

//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message,true);
//
//        helper.setFrom(fromAddress);
//        helper.setTo(toAddress);
//        helper.setSubject(subject);
//        helper.setText(body);
//
//        mailSender.send(message);
 //       System.out.println("Email Successfully send please check your mailbox..!");

    }

    @Override
    public UserModel updateUserModelData(int userId, UserDTO userDTO) {
        UserModel userData = this.getUserModelDataById(userId);
        userData.updateUserModelData(userDTO);
        return userRepository.save(userData);
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) throws Exception {
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword())
            );
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new Exception("Inavalid Email/Password...!!");
        }
        // Generate token for by name using jwtUtil.generateToken().
        return jwtUtil.generateToken(userLoginDTO.getEmail());
    }

    @Override
        public boolean verify(String verificationCode) {
            UserModel user = userRepository.findByVerificationCode(verificationCode);

            if (user == null || user.isEnabled()) {
                return false;
            } else {
                user.setVerificationCode(null);
                user.setEnabled(true);
                userRepository.save(user);

                return true;
            }


        }

//        user.updateUserModelData(userDTO);
//        return userRepository.save(user);
//    }

    @Override
    public void deleteUserModelData(int userId) {
        UserModel userData = this.getUserModelDataById(userId);
        userRepository.delete(userData);

    }

    @Override
    public List<UserModel> getUserModelDataByEmailId(String email) {
        return userRepository.findUserModelDataByEmailId(email);
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         UserModel userModelData = userRepository.findByEmail(email);

        if (userModelData == null) {
            throw new UsernameNotFoundException("Bad credentials");
        } else {

            //return new CustomUserDetails(userModelData);

            UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(userModelData.getEmail()).password(userModelData.getPassword()).authorities("USER").build();
            return userDetails;
        }
    }
}







