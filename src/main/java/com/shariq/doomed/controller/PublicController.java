package com.shariq.doomed.controller;

import com.shariq.doomed.journalEntry.User;
import com.shariq.doomed.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserServices userServices;

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user)
    {
        userServices.saveNewUser(user);
    }

}
