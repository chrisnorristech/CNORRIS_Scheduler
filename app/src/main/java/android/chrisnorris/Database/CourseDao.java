package android.chrisnorris.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM courses ORDER BY course_name")
    public List<Course> getCourses();

    @Query("SELECT * FROM courses WHERE term_id = :term_id")
    public List<Course> getCoursesByTermId(int term_id);

    @Query("SELECT * FROM courses WHERE course_name = :course_name limit 1")
    public Course getCoursesByName(String course_name);

    @Query("SELECT * FROM courses WHERE id = :course_id limit 1")
    public Course getCourseById(int course_id);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public void insertCourse(Course course);

    @Update
    public void updateCourse(Course course);

    @Delete
    public void deleteCourse(Course course);
}
