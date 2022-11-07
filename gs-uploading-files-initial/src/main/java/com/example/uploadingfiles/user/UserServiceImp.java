package com.example.uploadingfiles.user;


import com.example.uploadingfiles.database.UserRepository;
import com.example.uploadingfiles.database.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp implements UserService {

    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    UserRepository userRepository;
    @Override
    public void saveUser(Users users){
        users.setPassword(encoder.encode(users.getPassword()));
        userRepository.save(users);
    }
    @Override
    public boolean isUserAlreadyPresent(Users users){
        return false;
    }

}
