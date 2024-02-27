package in.app.heal.controller;

import in.app.heal.aux.AuxJournalDTO;
import in.app.heal.aux.AuxJournalEditDTO;
import in.app.heal.entities.JournalEntry;
import in.app.heal.entities.User;
import in.app.heal.service.JournalEntryService;
import in.app.heal.service.UserCredentialsService;
import in.app.heal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "api/journal")
public class JournalController {

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryService journalEntryService;
    @PostMapping(path = "")
    public ResponseEntity<?> addJournalEntry(@RequestBody AuxJournalDTO auxJournalDTO){
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setTitle(auxJournalDTO.getTitle());
        journalEntry.setDescription(auxJournalDTO.getDescription());
        journalEntry.setTags(auxJournalDTO.getTags());
        journalEntry.setEntry_date(new Date());
        Optional<User> user = userService.fetchByName(auxJournalDTO.getFirstName());
        if(user.isPresent()) {
            journalEntry.setUser_id(user.get());
            journalEntryService.addJournalEntry(journalEntry);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<?> editJournalEntry(@RequestBody AuxJournalEditDTO auxJournalEditDTO){
        Optional<JournalEntry> entryFound = journalEntryService.findByEntryId(auxJournalEditDTO.getEntryId());
        if(entryFound.isPresent()){
            JournalEntry entry = entryFound.get();
            entry.setDescription(auxJournalEditDTO.getDescription());
            entry.setEntry_date(new Date());
            journalEntryService.addJournalEntry(entry);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/delete/{entryId}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable Integer entryId){
        Optional<JournalEntry> entryFound = journalEntryService.findByEntryId(entryId);
        if(entryFound.isPresent()) {
            journalEntryService.deleteById(entryId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/findAll/{userId}")
    public ResponseEntity<?> findAllJournalEntries(@PathVariable Integer userId){
        Optional<List<JournalEntry>> entriesFound = journalEntryService.findAllByUserId(userId);
        return new ResponseEntity<Optional<List<JournalEntry>>>(entriesFound,HttpStatus.OK);
    }
}
