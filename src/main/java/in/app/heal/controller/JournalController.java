package in.app.heal.controller;

import in.app.heal.aux.AuxJournalDTO;
import in.app.heal.entities.JournalEntry;
import in.app.heal.entities.UserCredentials;
import in.app.heal.service.JournalEntryService;
import in.app.heal.service.TokenService;
import in.app.heal.service.UserCredentialsService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "api/journal")
public class JournalController {
    Dotenv dotenv = Dotenv.load();
    @Autowired
    private JournalEntryService journalEntryService;
    @PostMapping(path = "")
    public ResponseEntity<?> addJournalEntry(@RequestBody AuxJournalDTO auxJournalDTO, @RequestHeader(required = false)HttpHeaders headers){
        String auth = headers.get("authorization").toString();
        return journalEntryService.addJournalEntry(auxJournalDTO, auth);
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<?> editJournalEntry(@RequestBody AuxJournalDTO auxJournalEditDTO){
        Optional<JournalEntry> entryFound = journalEntryService.findByEntryId(auxJournalEditDTO.getEntryId());
        if(entryFound.isPresent()){
            journalEntryService.ediJournalEntry(entryFound.get(), auxJournalEditDTO);
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

    @GetMapping(path ="findById/{entryId}")
    public ResponseEntity<?> findEntryById(@PathVariable Integer entryId){
        Optional<JournalEntry> entryFound = journalEntryService.findByEntryId(entryId);
        if(entryFound.isPresent()){
            return new ResponseEntity<JournalEntry>(entryFound.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
