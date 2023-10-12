package com.ondedoar.service;

import com.ondedoar.enums.UserRole;
import com.ondedoar.model.UserModel;
import com.ondedoar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public boolean findByLogin(String login) {
        UserDetails user = userRepository.findByLogin(login);

        return user != null;
    }

    public UserModel save(UserModel userModel, String encryptedPassword) {
        userModel.setRole(UserRole.USER);
        userModel.setPassword(encryptedPassword);
        return userRepository.save(userModel);
    }
}
