package in.app.heal.service;

import in.app.heal.entities.JournalEntry;
import in.app.heal.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalEntryService {
    @Autowired
    JournalEntryRepository repository;

    public void addJournalEntry(JournalEntry journalEntry){
        repository.save(journalEntry);
    }
}
