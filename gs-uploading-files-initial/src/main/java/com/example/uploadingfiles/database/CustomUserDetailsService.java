package com.example.uploadingfiles.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
@ComponentScan
@Component
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Users users = userRepository.findByEmail(email);
        if(users ==null)
        {
            throw new UsernameNotFoundException("User Not Found");

        }
        return new com.example.uploadingfiles.database.CustomUserDetails(users);
    }

}
