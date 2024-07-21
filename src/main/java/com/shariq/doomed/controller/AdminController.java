package com.shariq.doomed.controller;

import com.shariq.doomed.journalEntry.User;
import com.shariq.doomed.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserServices userServices;

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> all = userServices.get_entry();
        if(all!= null && !all.isEmpty()){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/creat-admin-user")
    public void createUser(@RequestBody User user){
        userServices.saveAdmin(user);
    }
}
