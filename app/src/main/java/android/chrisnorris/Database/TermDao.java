package android.chrisnorris.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TermDao {
    @Query("SELECT * FROM terms ORDER BY start_date")
    public List<Term> getTerms();

    @Query("SELECT * FROM terms ORDER BY start_date DESC")
    public List<Term> getTermsNewerFirst();

    @Query("SELECT * FROM terms ORDER BY start_date ASC")
    public List<Term> getTermsOlderFirst();

    @Query("SELECT * FROM terms where term_name = :term_name limit 1")
    public Term getTermByName(String term_name);

    @Query("SELECT * FROM terms where id = :term_id limit 1")
    public Term getTermById(int term_id);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public void insertTerm(Term term);

    @Update
    public void updateTerm(Term term);

    @Delete
    public void deleteTerm(Term term);
}
