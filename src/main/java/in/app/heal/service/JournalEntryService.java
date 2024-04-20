package in.app.heal.service;

import in.app.heal.aux.AuxJournalDTO;
import in.app.heal.entities.JournalEntry;
import in.app.heal.entities.UserCredentials;
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
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }catch (Exception e){
                System.out.println(e);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    public Optional<List<JournalEntry>> findAllByUserId(Integer userId){
        return repository.findAllByUserId(userId);
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
