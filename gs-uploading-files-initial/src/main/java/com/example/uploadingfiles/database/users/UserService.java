package com.example.uploadingfiles.database.users;

import com.example.uploadingfiles.database.Users;

public interface UserService {

        public void saveUser(Users users);

        public boolean isUserAlreadyPresent(Users users);


}
