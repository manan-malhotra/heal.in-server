package in.app.heal.controller;

import in.app.heal.aux.AuxJournalDTO;
import in.app.heal.aux.AuxUserDTO;
import in.app.heal.entities.JournalEntry;
import in.app.heal.entities.User;
import in.app.heal.entities.UserCredentials;
import in.app.heal.service.JournalEntryService;
import in.app.heal.service.UserCredentialsService;
import in.app.heal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping(path = "api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserCredentialsService userCredentialsService;

    @Autowired
    private JournalEntryService journalEntryService;
    @PostMapping(path="/register")
    public ResponseEntity<?> registerUser(@RequestBody AuxUserDTO auxUserDTO){
        User newUser = new User();
        System.out.println("TESTING");
        newUser.setFirst_name(auxUserDTO.getFirstName());
        newUser.setLast_name(auxUserDTO.getLastName());
        newUser.setContact_number(auxUserDTO.getContact());
        newUser = userService.addUser(newUser);
        UserCredentials newUserCredentials = new UserCredentials();
        newUserCredentials.setEmail(auxUserDTO.getEmail());
        newUserCredentials.setUser_id(newUser);
        newUserCredentials.setPassword(auxUserDTO.getPassword()); // to be hashed
        newUserCredentials.setRole(auxUserDTO.getRole());
        userCredentialsService.addUser(newUserCredentials);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/journal")
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

}
