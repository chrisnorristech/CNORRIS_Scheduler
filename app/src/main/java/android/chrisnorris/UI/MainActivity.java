package android.chrisnorris.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;

import android.app.Activity;
import android.chrisnorris.Database.AppDatabase;
import android.chrisnorris.Database.Course;
import android.chrisnorris.Database.Term;
import android.chrisnorris.Database.TermDao;
import android.chrisnorris.R;
import android.chrisnorris.Utilities;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String ActivityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityName = this.getClass().getSimpleName();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" Scheduler");
        actionBar.setIcon(R.drawable.school);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Term testTerm = new Term("Spring 2023", "01/10/2023", "03/30/2023");
        Term testTerm2 = new Term("Summer 2023", "05/01/2023", "09/01/2023");

        AppDatabase db = AppDatabase.getInstance(this);

        Term testTermExists = db.termDao().getTermByName("Spring 2023");
        Term testTerm2Exists = db.termDao().getTermByName("Summer 2023");
        Log.d(ActivityName + "==>", testTerm.asString());
        if(testTermExists != null) {
            db.termDao().deleteTerm(testTermExists);
        }
        if(testTerm2Exists != null) {
            db.termDao().deleteTerm(testTerm2Exists);
        }
        db.termDao().insertTerm(testTerm);
        db.termDao().insertTerm(testTerm2);

        Course testCourse = db.courseDao().getCoursesByName("Test Course");
        Course testCourse2 = db.courseDao().getCoursesByName("Test Course 3");
        //Log.d(ActivityName + "==>", testCourse.asString());
        if(testCourse != null) {
            db.courseDao().deleteCourse(testCourse);
        }
        if(testCourse2 != null) {
            db.courseDao().deleteCourse(testCourse2);
        }

        Term testTermID = db.termDao().getTermByName("Spring 2023");
        int termID = testTermID.getId();

        Course course = new Course();
        course.setCourse_Name("Test Course");
        course.setStart_Date("01/01/2023");
        course.setEnd_Date("03/01/2023");
        course.setInstructor("Chris Norris");
        course.setInstructor_Email("chrisnorris070@gmail.com");
        course.setInstructor_Phone("(337) 326-1109");
        course.setNote("this is a note");
        course.setStatus("in progress");
        course.setTerm_id(termID);
        db.courseDao().insertCourse(course);

        Course course2 = new Course();
        course2.setCourse_Name("Test Course 3");
        course2.setStart_Date("01/01/2023");
        course2.setEnd_Date("03/01/2023");
        course2.setInstructor("Chris Norris");
        course2.setInstructor_Email("chrisnorris070@gmail.com");
        course2.setInstructor_Phone("(337) 326-1109");
        course2.setNote("this is a note");
        course2.setStatus("in progress");
        course2.setTerm_id(termID);
        db.courseDao().insertCourse(course2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // methods to control what happens when user
    // clicks on the action buttons
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.terms:
                Utilities.switchActivity(this, TermsActivity.class);
                break;
            case R.id.termsViewer:
                Utilities.switchActivity(this, TermViewerActivity.class);
                break;
            case R.id.coursesViewer:
                Utilities.switchActivity(this, CourseViewerActivity.class);
                break;
            case R.id.assessmentsViewer:
                Utilities.switchActivity(this, AssessmentViewerActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void EnterHere(View view) {
        Utilities.switchActivity(MainActivity.this, AssessmentsActivity.class);
    }
}