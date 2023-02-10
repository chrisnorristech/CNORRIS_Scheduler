package android.chrisnorris.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface AssessmentDao {
    @Query("SELECT * FROM assessments ORDER BY assessment_title")
    public List<Assessment> getAssessments();

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public void insertAssessment(Assessment assessment);

    @Update
    public void updateAssessment(Assessment assessment);

    @Delete
    public void deleteAssessment(Assessment assessment);
}
