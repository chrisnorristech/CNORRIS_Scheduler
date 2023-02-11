// MainActivity.java - startup activity
// version 1.0b
// Christopher D. Norris (cnorris@wgu.edu)
// Western Governors University
// Student ID: 000493268
//
// 2/10/2023 - initial development

package android.chrisnorris.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.chrisnorris.Database.AppDatabase;
import android.chrisnorris.Database.Course;
import android.chrisnorris.Database.Term;
import android.chrisnorris.R;
import android.chrisnorris.Utilities;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {
    // cl_ = class wide scope
    private String cl_ACTIVITY_NAME;
    private TableLayout cl_TABLE;
    private AppDatabase cl_DB;

    // c_ = CONSTANTS
    private static final String c_APP_TITLE = " Scheduler";

    // handles activity start up / configures action bar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cl_ACTIVITY_NAME = this.getClass().getSimpleName();
        cl_DB = AppDatabase.getInstance(this);

        ActionBar action_bar = getSupportActionBar();
        action_bar.setTitle(c_APP_TITLE);
        action_bar.setIcon(R.drawable.school);
        action_bar.setDisplayUseLogoEnabled(true);
        action_bar.setDisplayShowHomeEnabled(true);

        AppDatabase cl_DB = AppDatabase.getInstance(this);

        addSampleData();
    }

    public void addSampleData() {
        Term testTerm = new Term("Spring 2023", "01/10/2023", "03/30/2023");
        Term testTerm2 = new Term("Summer 2023", "05/01/2023", "09/01/2023");

        Term testTermExists = cl_DB.termDao().getTermByName("Spring 2023");
        Term testTerm2Exists = cl_DB.termDao().getTermByName("Summer 2023");
        if(testTermExists != null) {
            cl_DB.termDao().deleteTerm(testTermExists);
        }
        if(testTerm2Exists != null) {
            cl_DB.termDao().deleteTerm(testTerm2Exists);
        }
        cl_DB.termDao().insertTerm(testTerm);
        cl_DB.termDao().insertTerm(testTerm2);

        Course testCourse = cl_DB.courseDao().getCoursesByName("Test Course");
        Course testCourse2 = cl_DB.courseDao().getCoursesByName("Test Course 3");
        //Log.d(ActivityName + "==>", testCourse.asString());
        if(testCourse != null) {
            cl_DB.courseDao().deleteCourse(testCourse);
        }
        if(testCourse2 != null) {
            cl_DB.courseDao().deleteCourse(testCourse2);
        }

        Term testTermID = cl_DB.termDao().getTermByName("Spring 2023");
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
        cl_DB.courseDao().insertCourse(course);

        Course course2 = new Course();
        course2.setCourse_Name("Test Course 3");
        course2.setStart_Date("01/01/2023");course2.setEnd_Date("03/01/2023");
        course2.setInstructor("Chris Norris");
        course2.setInstructor_Email("chrisnorris070@gmail.com");
        course2.setInstructor_Phone("(337) 326-1109");
        course2.setNote("this is a note");
        course2.setStatus("in progress");
        course2.setTerm_id(termID);
        cl_DB.courseDao().insertCourse(course2);
    }
    // inflates and displays options menu
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
                Utilities.switchActivity(this, TermsListActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}