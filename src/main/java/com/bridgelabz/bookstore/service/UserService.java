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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.util.List;


@Service
@Slf4j
public class UserService implements IUserService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private EmailService emailService;

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

    // modelMapper use map() for conversion of object with arguments map(Source, destination).
    @Override
    public String createUserModelData(UserDTO userDTO) throws MessagingException,NullPointerException {
        UserModel userData1=this.getUserModelDataByEmailId(userDTO.getEmail());
        if (userData1 == null) {
            UserModel userData = modelMapper.map(userDTO, UserModel.class);
            String randomCode = RandomString.make(32);
            userData.setVerificationCode(randomCode);
            userData.setEnabled(false);
            userRepository.save(userData);
            return emailService.sendVerificationEmail(userData);
        }else{
            return "Email id already register with another user..! ";
        }
    }

    @Override
    public UserModel updateUserModelData(int userId, UserDTO userDTO) {
        UserModel userData = this.getUserModelDataById(userId);
        userData.updateUserModelData(userDTO);
        return userRepository.save(userData);
    }

    @Override
    public UserModel login(UserLoginDTO userLoginDTO) throws Exception {

        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword())
            );
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new Exception("Inavalid Email/Password...!!");
        }
          String str=jwtUtil.generateToken(userLoginDTO.getEmail());
          UserModel userData=this.getUserModelDataByEmailId(userLoginDTO.getEmail());
          userData.setToken(str);
          userRepository.save(userData);
          return userData;

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


    @Override
    public void deleteUserModelData(int userId) {
        UserModel userData = this.getUserModelDataById(userId);
        userRepository.delete(userData);

    }

    @Override
    public UserModel getUserModelDataByEmailId(String email) {
        return userRepository.findUserModelDataByEmailId(email);
    }

}







