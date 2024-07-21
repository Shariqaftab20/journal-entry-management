package com.shariq.doomed.service;

import com.shariq.doomed.journalEntry.JournalEntry;
import com.shariq.doomed.journalEntry.User;
import com.shariq.doomed.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryServices {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserServices userServices;

    @Transactional
    public void save_entry(JournalEntry journalEntry, String name)
    {
        try {
            User user = userServices.findByUserName(name);
            JournalEntry save = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(save);
            userServices.saveUser(user);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("Gand Phatt gae bhai ");
        }
    }

    public void save_entry(JournalEntry journalEntry)
    {
        journalEntryRepository.save(journalEntry); 
    }

    public List<JournalEntry> get_entry()
    {
        return journalEntryRepository.findAll();
    }

    @Transactional
    public boolean delete_entry(ObjectId myID, String name)
    {
        boolean remove = false;
        try {
            User user = userServices.findByUserName(name);
            remove = user.getJournalEntries().removeIf( x -> x.getId().equals(myID));
            if(remove){
                userServices.saveUser(user);
                journalEntryRepository.deleteById(myID);
            }
        }
        catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("An Error Occured  while deleting entry:",e);
        }
        return remove;
    }

    public Optional<JournalEntry> get_entry_id(ObjectId myID)
    {
        return journalEntryRepository.findById(myID);
    }

}
