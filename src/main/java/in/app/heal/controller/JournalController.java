package in.app.heal.controller;

import in.app.heal.aux.AuxJournalDTO;
import in.app.heal.aux.AuxUserDTO;
import in.app.heal.entities.JournalEntry;
import in.app.heal.entities.User;
import in.app.heal.entities.UserCredentials;
import in.app.heal.service.JournalEntryService;
import in.app.heal.service.UserCredentialsService;
import in.app.heal.service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "api/journal")
public class JournalController {
    Dotenv dotenv = Dotenv.load();
    @Autowired
    private UserService userService;

    @Autowired
    private UserCredentialsService userCredentialsService;

    @Autowired
    private JournalEntryService journalEntryService;
    @PostMapping(path = "")
    public ResponseEntity<?> addJournalEntry(@RequestBody AuxJournalDTO auxJournalDTO, @RequestHeader(required = false)HttpHeaders headers){

        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setTitle(auxJournalDTO.getTitle());
        journalEntry.setDescription(auxJournalDTO.getDescription());
        journalEntry.setEntry_date(new Date());
        String auth = headers.get("authorization").toString();
        String token = "";
        if(!auth.isEmpty()){
            token = auth.split(" ")[1];

            String secret = dotenv.get("SECRET_KEY");
            Key hmackey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                    SignatureAlgorithm.HS256.getJcaName());
            try{
                Claims jwt = Jwts.parserBuilder().setSigningKey(hmackey).build().parseClaimsJws(token).getBody();
                String email = (String) jwt.get("email");
                Optional<UserCredentials> userCredentialsOptional = userCredentialsService.findByEmail(email);
                if(userCredentialsOptional.isPresent()){
                    UserCredentials userCredentials = userCredentialsOptional.get();
                    User user = userCredentials.getUser_id();
                    journalEntry.setUser_id(user);
                    journalEntryService.addJournalEntry(journalEntry);
                    return new ResponseEntity<JournalEntry>(journalEntry,HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }catch (Exception e){
                System.out.println(e);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<?> editJournalEntry(@RequestBody AuxJournalDTO auxJournalEditDTO){
        Optional<JournalEntry> entryFound = journalEntryService.findByEntryId(auxJournalEditDTO.getEntryId());
        if(entryFound.isPresent()){
            JournalEntry entry = entryFound.get();
            entry.setDescription(auxJournalEditDTO.getDescription());
            entry.setTitle(auxJournalEditDTO.getTitle());
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
