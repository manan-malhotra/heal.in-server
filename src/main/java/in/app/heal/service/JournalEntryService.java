package in.app.heal.service;

import in.app.heal.entities.JournalEntry;
import in.app.heal.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    JournalEntryRepository repository;

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
}
