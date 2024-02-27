package in.app.heal.repository;

import in.app.heal.entities.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry,Integer> {

    @Query(value = "SELECT * FROM journal_entry WHERE user_id = ?1 ORDER BY entry_date DESC",nativeQuery = true)
    public Optional<List<JournalEntry>> findAllByUserId(Integer userId);
}
