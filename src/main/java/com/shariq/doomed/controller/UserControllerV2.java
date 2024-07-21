package com.shariq.doomed.controller;

import com.shariq.doomed.journalEntry.User;
import com.shariq.doomed.repository.UserRepository;
import com.shariq.doomed.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserControllerV2 {

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User UserInDb = userServices.findByUserName(name);
        UserInDb.setUserName(user.getUserName());
        UserInDb.setPassword(user.getPassword());
        userServices.saveNewUser(UserInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
