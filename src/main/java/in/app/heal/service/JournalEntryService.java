package in.app.heal.service;

import in.app.heal.aux.AuxJournalDTO;
import in.app.heal.entities.JournalEntry;
import in.app.heal.entities.User;
import in.app.heal.entities.UserCredentials;
import in.app.heal.error.ApiError;
import in.app.heal.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserService userService;
    @Autowired
    UserCredentialsService userCredentialsService;
    @Autowired
    JournalEntryRepository repository;
    public ResponseEntity<?> addJournalEntry(AuxJournalDTO auxJournalDTO,String auth){
        String token = tokenService.getToken(auth);
        JournalEntry journalEntry = this.populateJournalEntry(auxJournalDTO);
        if(!token.isEmpty()){
            try{
                String email = tokenService.getEmailFromToken(token);
                Optional<UserCredentials> userCredentialsOptional = userCredentialsService.findByEmail(email);
                if(userCredentialsOptional.isPresent()){
                    this.populateJournalEntry(journalEntry, userCredentialsOptional.get());
                    this.addJournalEntry(journalEntry);
                    return new ResponseEntity<JournalEntry>(journalEntry,HttpStatus.OK);
                }
                ApiError apiError = new ApiError();
                apiError.setMessage("User not found");
                apiError.setStatus(HttpStatus.CONFLICT);
                return new ResponseEntity<Object>(apiError,HttpStatus.CONFLICT);
            }catch (Exception e){
                ApiError apiError = new ApiError();
                apiError.setMessage("Unauthorized Access");
                apiError.setStatus(HttpStatus.UNAUTHORIZED);
                return new ResponseEntity<Object>(apiError,HttpStatus.UNAUTHORIZED);
            }
        }else{
            ApiError apiError = new ApiError();
            apiError.setMessage("Token Missing");
            apiError.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<Object>(apiError,HttpStatus.BAD_REQUEST);
        }
    }
    public void addJournalEntry(JournalEntry journalEntry){
        repository.save(journalEntry);
    }
    public Optional<JournalEntry> findByEntryId(Integer entryId){
        return repository.findById(entryId);
    }

    public void deleteById(Integer entryId){
        repository.deleteById(entryId);
    }

    public ResponseEntity<?> findAllByUserId(Integer userId){
        Optional<User> user = userService.findById(userId);
        if(!user.isPresent()){
            ApiError apiError = new ApiError();
            apiError.setStatus(HttpStatus.NOT_FOUND);
            apiError.setMessage("User not found");
            return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
        }
        Optional<List<JournalEntry>> entriesFound = repository.findAllByUserId(userId);
        return new ResponseEntity<Optional<List<JournalEntry>>>(entriesFound,HttpStatus.OK);
    }

    public JournalEntry populateJournalEntry(AuxJournalDTO auxJournalDTO){
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setTitle(auxJournalDTO.getTitle());
        journalEntry.setDescription(auxJournalDTO.getDescription());
        journalEntry.setEntry_date(new Date());
        return journalEntry;
    }
    public JournalEntry populateJournalEntry(JournalEntry journalEntry, UserCredentials userCredentials){
        journalEntry.setUser_id(userCredentials.getUser_id());
        return journalEntry;
    }
    public JournalEntry ediJournalEntry(JournalEntry journalEntry, AuxJournalDTO auxJournalEditDTO){
        journalEntry.setDescription(auxJournalEditDTO.getDescription());
        journalEntry.setTitle(auxJournalEditDTO.getTitle());
        return repository.save(journalEntry);
    }
}
