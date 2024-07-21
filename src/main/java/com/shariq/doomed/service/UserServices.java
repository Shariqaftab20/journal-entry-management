package com.shariq.doomed.service;

import com.shariq.doomed.journalEntry.User;
import com.shariq.doomed.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(User userEntry)
    {
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("USER") );
        userRepository.save(userEntry);
    }

    public void saveAdmin(User userEntry)
    {
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("USER","ADMIN") );
        userRepository.save(userEntry);
    }

    public void saveUser(User userEntry){
        userRepository.save(userEntry);
    }

    public List<User> get_entry()
    {
        return userRepository.findAll();
    }

    public List<User> delete_entry(ObjectId myID)
    {
        userRepository.deleteById(myID);
        return get_entry();
    }

    public Optional<User> get_entry_id(ObjectId myID)
    {
        return userRepository.findById(myID);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
