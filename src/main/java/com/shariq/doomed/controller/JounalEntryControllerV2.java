package com.shariq.doomed.controller;

import com.shariq.doomed.journalEntry.JournalEntry;
import com.shariq.doomed.journalEntry.User;
import com.shariq.doomed.service.JournalEntryServices;
import com.shariq.doomed.service.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JounalEntryControllerV2 {


    @Autowired
    private JournalEntryServices journalEntryServices;

    @Autowired
    private UserServices userServices;

    @GetMapping()
    public ResponseEntity<?> getAllByUserName()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userServices.findByUserName(name);
        List<JournalEntry> all = user.getJournalEntries();
        if(all!=null && !all.isEmpty())
            return new ResponseEntity<>(all,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("id/{myID}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userServices.findByUserName(name);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myID)).toList();
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryServices.get_entry_id(myID);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<JournalEntry> newEntryData(@RequestBody JournalEntry myEntry)
    {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            myEntry.setTime(LocalDateTime.now());
            journalEntryServices.save_entry(myEntry,name);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/id/{myID}")
    public ResponseEntity<?> removeData(@PathVariable ObjectId myID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        boolean remove = journalEntryServices.delete_entry(myID,name);
        if(remove) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/id/{myID}")
    public ResponseEntity<?> updateData(
            @RequestBody JournalEntry myEntry,
            @PathVariable ObjectId myID
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userServices.findByUserName(name);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myID)).toList();
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryServices.get_entry_id(myID);
            if(journalEntry.isPresent()){
                JournalEntry oldEntry = journalEntry.get();
                oldEntry.setName(myEntry.getName() != null && myEntry.getName().equals("") ? myEntry.getName(): oldEntry.getName());
                oldEntry.setContent(myEntry.getContent() != null && myEntry.getContent().equals("") ? myEntry.getContent(): oldEntry.getContent());
                journalEntryServices.save_entry(oldEntry);
                return new ResponseEntity<>(oldEntry,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{myID}")
    public JournalEntry getEntry(@PathVariable ObjectId myID){
        return journalEntryServices.get_entry_id(myID).orElse(null);
    }

}
