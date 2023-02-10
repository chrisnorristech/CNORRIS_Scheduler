package android.chrisnorris.UI;

import android.chrisnorris.Database.AppDatabase;
import android.chrisnorris.Database.Course;
import android.chrisnorris.Database.Term;
import android.chrisnorris.R;
import android.chrisnorris.Utilities;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.List;

public class TermsActivity extends AppCompatActivity implements View.OnClickListener {
    String ActivityName;
    TableLayout table;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_viewer);
        db = AppDatabase.getInstance(this);
        ActivityName = this.getClass().getSimpleName();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" Scheduler");
        actionBar.setIcon(R.drawable.school);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        table = (TableLayout) findViewById(R.id.term_view_table);
        buildTermTable();
    }

    /**
     * buildTermTable - create the table layout for terms
     */
    private void buildTermTable() {
        List<Term> terms = db.termDao().getTerms();

        TableRow.LayoutParams termHeaderRowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow headerRow = new TableRow(this);
        headerRow.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_700));
        TextView headerText = new TextView(this);
        headerText.setTextSize(25);
        LinearLayout.LayoutParams headerTextLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        headerText.setText("Terms Details");
        headerText.setTextColor(ContextCompat.getColor(this, R.color.white));
        headerText.setWidth(width);
        headerText.setGravity(Gravity.CENTER_HORIZONTAL);
        headerText.setLayoutParams(headerTextLayout);
        headerRow.addView(headerText, termHeaderRowLayout);
        table.addView(headerRow);

        LinearLayout add_term_wrapper = new LinearLayout(this);
        add_term_wrapper.setGravity(Gravity.CENTER);
        Button btnAddTerm = new Button(this);
        btnAddTerm.setText("Add Term");
        btnAddTerm.setTag(R.id.button_name, "button_add_term");
        btnAddTerm.setWidth(width/3);
        btnAddTerm.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnAddTerm.setOnClickListener(this);
        add_term_wrapper.addView(btnAddTerm);
        table.addView(add_term_wrapper);

        if (terms.size() == 0) {
            LinearLayout noTerm = new LinearLayout(this);
            TextView noTermText = new TextView(this);
            noTermText.setTextSize(20);
            noTermText.setText("No terms found.");
            noTermText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            noTerm.addView(noTermText);
            table.addView(noTerm);
        }

        for (Term term : terms) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);

            String termDetails = term.asString();
            Log.d(ActivityName + "==>", "Retrieved: " + termDetails);
            TextView termText = new TextView(this);
            termText.setTextColor(ContextCompat.getColor(this, R.color.purple_700));
            TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            rowLayout.setMargins(0, 50, 0, 0);
            termText.setTextSize(20);
            termText.setText(termDetails);
            termText.setGravity(Gravity.CENTER);
            linearLayout.addView(termText);

            // Term Add/Edit/Delete Buttons
            LinearLayout buttonBar = new LinearLayout(this);
            buttonBar.setGravity(Gravity.CENTER);
            Button btnEditTerm = new Button(this);
            btnEditTerm.setText("Edit Term");
            btnEditTerm.setTag(R.id.button_name, "button_edit_term");
            btnEditTerm.setTag(R.id.term_id, term.getId());
            btnEditTerm.setTag(R.id.course_id, -1);
            btnEditTerm.setWidth(width/3);
            btnEditTerm.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btnEditTerm.setOnClickListener(this);
            buttonBar.addView(btnEditTerm);

            Button btnDeleteTerm = new Button(this);
            btnDeleteTerm.setText("Delete Term");
            btnDeleteTerm.setTag(R.id.button_name, "button_delete_term");
            btnDeleteTerm.setTag(R.id.term_id, term.getId());
            btnDeleteTerm.setTag(R.id.course_id, -1);
            btnDeleteTerm.setWidth(width/3);
            btnDeleteTerm.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btnDeleteTerm.setOnClickListener(this);
            buttonBar.addView(btnDeleteTerm);

            linearLayout.addView(buttonBar);

            table.addView(linearLayout);

            LinearLayout coursesHeader = new LinearLayout(this);

            TextView coursesHeaderText = new TextView(this);
            coursesHeaderText.setTextSize(20);
            coursesHeaderText.setText("Course Details:");
            coursesHeader.addView(coursesHeaderText);
            coursesHeader.setBackgroundColor(ContextCompat.getColor(this, R.color.teal_200));
            coursesHeader.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            table.addView(coursesHeader);

            List<Course> courses = db.courseDao().getCoursesByTermId(term.getId());

            LinearLayout add_course_wrapper = new LinearLayout(this);
            add_course_wrapper.setGravity(Gravity.CENTER);
            Button btnAddCourse = new Button(this);
            btnAddCourse.setText("Add Course");
            btnAddCourse.setTag(R.id.button_name, "button_add_course");
            btnAddCourse.setWidth(width/3);
            btnAddCourse.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btnAddCourse.setOnClickListener(this);
            add_course_wrapper.addView(btnAddCourse);
            table.addView(add_course_wrapper);

            if (courses.size() == 0) {
                LinearLayout noCourse = new LinearLayout(this);
                TextView noCourseText = new TextView(this);
                noCourseText.setTextSize(20);
                noCourseText.setText("No courses found.");
                noCourseText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                noCourse.addView(noCourseText);
                table.addView(noCourse);
            }

            LinearLayout courseWrapper;

            for (Course course : courses) {
                String courseDetails = course.asString();

                Log.d(ActivityName + "==>", "Retrieved: " + termDetails);
                courseWrapper = new LinearLayout(this);
                TextView courseText = new TextView(this);
                courseText.setTextColor(ContextCompat.getColor(this, R.color.black));
                courseText.setTextSize(15);
                courseText.setText(courseDetails);
                courseWrapper.setOrientation(LinearLayout.VERTICAL);
                courseText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                courseWrapper.addView(courseText);

                // Term Add/Edit/Delete Buttons
                LinearLayout courseButtonBar = new LinearLayout(this);
                buttonBar.setGravity(Gravity.CENTER);
                Button btnEditCourse = new Button(this);
                btnEditCourse.setText("Edit Course");
                btnEditCourse.setTag(R.id.button_name, "button_edit_course");
                btnEditCourse.setTag(R.id.term_id, term.getId());
                btnEditCourse.setTag(R.id.course_id, course.getId());
                btnEditCourse.setWidth(width/3);
                btnEditCourse.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                btnEditCourse.setOnClickListener(this);
                courseButtonBar.addView(btnEditCourse);

                Button btnDeleteCourse = new Button(this);
                btnDeleteCourse.setText("Delete Course");
                btnDeleteCourse.setTag(R.id.button_name, "button_delete_course");
                btnDeleteCourse.setTag(R.id.term_id, term.getId());
                btnDeleteCourse.setTag(R.id.course_id, course.getId());
                btnDeleteCourse.setWidth(width/3);
                btnDeleteCourse.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                btnDeleteCourse.setOnClickListener(this);
                courseButtonBar.addView(btnDeleteCourse);

                courseWrapper.addView(courseButtonBar);
                table.addView(courseWrapper);
            }
        }
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag(R.id.button_name);
        int term_id = (int) v.getTag(R.id.term_id);
        int course_id = (int) v.getTag(R.id.course_id);
        switch (tag) {
            case "button_add_term":
                Toast.makeText(v.getContext(), "clicked new term", Toast.LENGTH_SHORT).show();
                break;
            case "button_edit_term":
                Toast.makeText(v.getContext(), "clicked " + tag + term_id, Toast.LENGTH_SHORT).show();
                break;
            case "button_delete_term":
                Term termToBeDeleted = db.termDao().getTermById(term_id);
                if(!doesTermHaveCourses(termToBeDeleted)) {
                    db.termDao().deleteTerm(termToBeDeleted);
                    table.removeAllViews();
                    buildTermTable();
                } else {
                    Toast.makeText(this, "Can't remove a Term with courses.", Toast.LENGTH_SHORT).show();
                }
                break;
            case "button_delete_course":
                Course courseToBeDeleted = db.courseDao().getCourseById(course_id);
                db.courseDao().deleteCourse(courseToBeDeleted);
                table.removeAllViews();
                buildTermTable();
                break;
        }
    }

    /*
        doesTermHaveCourses - checks to see if this term has courses assigned to it.
        term : Term - the term to check
     */
    private boolean doesTermHaveCourses(Term term) {
        List<Course> coursesForTerm = db.courseDao().getCoursesByTermId(term.getId());
        if(coursesForTerm.size()==0) {
            return false;
        } else {
            return true;
        }
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
            case android.R.id.home:
                finish();
                break;
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
}