// TermsDetailActivity.java - term administration / course listings
// version 1.0b
// Christopher D. Norris (cnorris@wgu.edu)
// Western Governors University
// Student ID: 000493268
//
// 2/10/2023 - initial development

package android.chrisnorris.UI;

import android.chrisnorris.Database.AppDatabase;
import android.chrisnorris.Database.Course;
import android.chrisnorris.Database.Term;
import android.chrisnorris.R;
import android.chrisnorris.Utilities;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Objects;

public class TermsDetailActivity extends AppCompatActivity implements View.OnClickListener {
    // cl_ = class wide scope
    private String cl_ACTIVITY_NAME;
    private LinearLayout cl_MAIN_LAYOUT;
    private AppDatabase cl_db;
    private int cl_term_id;
    private String cl_form_mode;
    private int screen_width;
    private EditText et_term_name;
    private EditText et_term_start;
    private EditText et_term_end;
    private String cl_form_mode_add;

    // c_ = CONSTANTS
    private String c_APP_TITLE = "Scheduler";
    private String c_NOT_USED = "NOT USED";
    private String c_HEADER_TITLE = "TERM DETAILS";

    // handles Activity startup / configures action bar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        // get db instance
        cl_db = AppDatabase.getInstance(this);

        // get activity name
        cl_ACTIVITY_NAME = this.getClass().getSimpleName();

        // get form mode add
        cl_form_mode_add = getResources().getString(R.string.form_mode_add);

        // get value from previous activity (term_id)
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cl_term_id = extras.getInt("value");
            cl_form_mode = extras.getString("form_mode");
        }

        // get screen width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screen_width = displayMetrics.widthPixels;

        // configure action bar
        ActionBar action_bar = getSupportActionBar();
        action_bar.setTitle(" Scheduler");
        action_bar.setIcon(R.drawable.school);
        action_bar.setDisplayUseLogoEnabled(true);
        action_bar.setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // find main linear layout
        cl_MAIN_LAYOUT = findViewById(R.id.main_layout);

        // as long as the term_id came across build table
        if (cl_term_id > -1 || cl_form_mode == cl_form_mode_add ) {
            buildTermDetailView();
        }
    }

    // handles refreshing table if returning from another activity
    // just in case changes occurred to db.
    @Override
    public void onResume() {
        super.onResume();
        // clear view
        cl_MAIN_LAYOUT.removeAllViews();
        // rebuild view
        buildTermDetailView();
    }

    // dynamically generates term table layout with course details
    private void buildTermDetailView() {
        // get term details from db
        Term term = null;

        if(!Objects.equals(cl_form_mode, cl_form_mode_add)) {
            term = cl_db.termDao().getTermById(cl_term_id);
        }

        // define layout for full width
        TableRow.LayoutParams layout_full_width = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);

        // build header row
        LinearLayout header_row = Utilities.build_header_row(this, c_HEADER_TITLE);
        cl_MAIN_LAYOUT.addView(header_row);

        if(!Objects.equals(cl_form_mode_add, cl_form_mode)) {
            build_term_inputs(term);
        } else {
            build_term_inputs(null);
        }

        // term update and delete buttons
        LinearLayout term_button_bar = build_term_button_bar(term);
        cl_MAIN_LAYOUT.addView(term_button_bar);

        // courses header
        LinearLayout courses_header = build_courses_header();
        cl_MAIN_LAYOUT.addView(courses_header);

        List<Course> courses = null;

        // get courses by term id
        if(!Objects.equals(cl_form_mode, cl_form_mode_add)) {
            courses = cl_db.courseDao().getCoursesByTermId(term.getId());
        }

        // if there are no courses say so
        if (!Objects.equals(cl_form_mode, cl_form_mode_add))  {
            if(courses.size() == 0) {
                LinearLayout no_course_available = build_no_course();
                cl_MAIN_LAYOUT.addView(no_course_available);
            }
        } else {
            LinearLayout no_course_available = build_no_course();
            cl_MAIN_LAYOUT.addView(no_course_available);
        }

        if(!Objects.equals(cl_form_mode, cl_form_mode_add)) {
            for (Course course : courses) {
                LinearLayout course_details = build_course_details(course);
                cl_MAIN_LAYOUT.addView(course_details);
            }
        }

        if(!Objects.equals(cl_form_mode, cl_form_mode_add)) {
            LinearLayout course_button_bar = build_course_button_bar();
            cl_MAIN_LAYOUT.addView(course_button_bar);
        }
    }

    // click handler for activity
    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag(R.id.button_name);

        String term_name = et_term_name.getText().toString();
        String term_start = et_term_start.getText().toString();
        String term_end = et_term_end.getText().toString();

        switch (tag) {
            case "button_update_term":
                Term toBeUpdated = cl_db.termDao().getTermById(cl_term_id);
                toBeUpdated.setTerm_Name(term_name);
                toBeUpdated.setStart_Date(term_start);
                toBeUpdated.setEnd_Date(term_end);
                cl_db.termDao().updateTerm(toBeUpdated);
                Toast.makeText(this, "Term Updated.", Toast.LENGTH_SHORT).show();
                break;
            case "button_delete_term":
                Term termToBeDeleted = cl_db.termDao().getTermById(cl_term_id);
                if (!doesTermHaveCourses(termToBeDeleted)) {
                    cl_db.termDao().deleteTerm(termToBeDeleted);
                    finish();
                } else {
                    Toast.makeText(this, "Can't remove a Term with courses.", Toast.LENGTH_SHORT).show();
                }
                break;
            case "button_save_term":
                Term toBeAdded = new Term();
                toBeAdded.setTerm_Name(term_name);
                toBeAdded.setStart_Date(term_start);
                toBeAdded.setEnd_Date(term_end);
                cl_db.termDao().insertTerm(toBeAdded);
                finish();
                break;
        }
    }

    //  checks to see if this term has courses assigned to it.
    //  term : Term - the term to check
    //  returns boolean - true is term has course, false if no courses for term
    private boolean doesTermHaveCourses(Term term) {
        List<Course> coursesForTerm = cl_db.courseDao().getCoursesByTermId(term.getId());
        if (coursesForTerm.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    // handles creation of options menu
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
                Utilities.switchActivity(this, TermsDetailActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // build edit boxes from editing term details
    private void build_term_inputs(Term term) {
        // create edit texts for term details
        TextView tv_term_name = new TextView(this);
        tv_term_name.setText( " Term Name: ");
        TextView tv_term_start = new TextView(this);
        tv_term_start.setText("   Start Date: ");
        TextView tv_term_end = new TextView(this);
        tv_term_end.setText(  "    End Date: ");

        et_term_name = new EditText(this);
        et_term_name.setWidth(500);
        et_term_start = new EditText(this);
        et_term_start.setWidth(500);
        et_term_end = new EditText(this);
        et_term_end.setWidth(500);

        // not adding
        if(term != null) {
            et_term_name.setText(term.getTerm_Name());
            et_term_start.setText(term.getStart_Date());
            et_term_end.setText(term.getEnd_Date());
        }

        et_term_name.setTextSize(20);
        et_term_start.setTextSize(20);
        et_term_end.setTextSize(20);
        tv_term_name.setTextSize(20);
        tv_term_start.setTextSize(20);
        tv_term_end.setTextSize(20);

        LinearLayout wrapper_input = new LinearLayout(this);
        wrapper_input.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams wrapper_layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        wrapper_input.setLayoutParams(wrapper_layout);

        LinearLayout wrapper_term_name = new LinearLayout(this);
        wrapper_term_name.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout wrapper_term_start = new LinearLayout(this);
        wrapper_term_start.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout wrapper_term_end = new LinearLayout(this);
        wrapper_term_end.setOrientation(LinearLayout.HORIZONTAL);

        wrapper_term_name.addView(tv_term_name);
        wrapper_term_name.addView(et_term_name);

        wrapper_term_start.addView(tv_term_start);
        wrapper_term_start.addView(et_term_start);

        wrapper_term_end.addView(tv_term_end);
        wrapper_term_end.addView(et_term_end);

        wrapper_input.addView(wrapper_term_name);
        wrapper_input.addView(wrapper_term_start);
        wrapper_input.addView(wrapper_term_end);

        cl_MAIN_LAYOUT.addView(wrapper_input);
    }

    // build button bar for updating / deleting terms
    private LinearLayout build_term_button_bar(Term term) {
        LinearLayout button_bar = new LinearLayout(this);
        button_bar.setGravity(Gravity.CENTER);

            Button btn_update_term = new Button(this);
            btn_update_term.setText("Update Term");
            btn_update_term.setTag(R.id.button_name, "button_update_term");
            btn_update_term.setTag(R.id.term_id, -1);
            btn_update_term.setTag(R.id.course_id, -1);
            btn_update_term.setWidth(screen_width / 3);
            btn_update_term.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btn_update_term.setOnClickListener(this);
            button_bar.addView(btn_update_term);

            if(!Objects.equals(cl_form_mode, cl_form_mode_add)) {
                Button btn_delete_term = new Button(this);
                btn_delete_term.setText(R.string.btn_delete_term_text);
                btn_delete_term.setTag(R.id.button_name, "button_delete_term");
                btn_delete_term.setTag(R.id.term_id, term.getId());
                btn_delete_term.setTag(R.id.course_id, -1);
                btn_delete_term.setWidth(screen_width / 3);
                btn_delete_term.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                btn_delete_term.setOnClickListener(this);
                button_bar.addView(btn_delete_term);
            }

            if(Objects.equals(cl_form_mode, cl_form_mode_add)) {
                btn_update_term.setText("Save Term");
                btn_update_term.setTag(R.id.button_name, "button_save_term");
            }
            return button_bar;
    }

    // build header for course details
    private LinearLayout build_courses_header() {
        LinearLayout courses_header = new LinearLayout(this);
        TextView courses_header_text = new TextView(this);
        courses_header_text.setTextSize(20);
        courses_header_text.setText(R.string.course_details_header);
        courses_header.addView(courses_header_text);
        return courses_header;
    }

    // build response for when no courses are in a term
    private LinearLayout build_no_course() {
        LinearLayout no_course = new LinearLayout(this);
        TextView no_course_text = new TextView(this);

        LinearLayout.LayoutParams course_layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        course_layout.setMargins(50,0,50,0);
        no_course.setLayoutParams(course_layout);

        no_course_text.setTextSize(20);
        no_course_text.setText(R.string.no_course_text);
        no_course.addView(no_course_text);
        return no_course;
    }

    // build course details
    private LinearLayout build_course_details(Course course) {
        LinearLayout course_wrapper = new LinearLayout(this);
        course_wrapper.setOrientation(LinearLayout.VERTICAL);
        course_wrapper.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams course_layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        course_layout.setMargins(50,0,50,0);
        course_wrapper.setLayoutParams(course_layout);

        TextView course_name = new TextView(this);
        String course_name_text = String.format(getResources().getString(R.string.course_name_text), course.getCourse_Name());
        course_name.setTextColor(ContextCompat.getColor(this, R.color.black));
        course_name.setTextSize(15);
        course_name.setText(course_name_text);
        course_name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        course_wrapper.addView(course_name);

        TextView course_start_date = new TextView(this);
        String course_start_date_text = String.format(getResources().getString(R.string.course_start_date_text), course.getStart_Date());
        course_start_date.setTextColor(ContextCompat.getColor(this, R.color.black));
        course_start_date.setTextSize(15);
        course_start_date.setText(course_start_date_text);
        course_start_date.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        course_wrapper.addView(course_start_date);

        TextView course_end_date = new TextView(this);
        String course_end_date_text = String.format(getResources().getString(R.string.course_start_date_text), course.getEnd_Date());
        course_end_date.setTextColor(ContextCompat.getColor(this, R.color.black));
        course_end_date.setTextSize(15);
        course_end_date.setText(course_end_date_text);
        course_end_date.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        course_wrapper.addView(course_end_date);

        TextView course_instructor = new TextView(this);
        String course_instructor_text = String.format(getResources().getString(R.string.course_start_date_text), course.getInstructor());
        course_instructor.setTextColor(ContextCompat.getColor(this, R.color.black));
        course_instructor.setTextSize(15);
        course_instructor.setText(course_instructor_text);
        course_wrapper.addView(course_instructor);

        TextView course_instructor_email = new TextView(this);
        String course_instructor_email_text = String.format(getResources().getString(R.string.course_start_date_text), course.getInstructor_Email());
        course_instructor_email.setTextColor(ContextCompat.getColor(this, R.color.black));
        course_instructor_email.setTextSize(15);
        course_instructor_email.setText(course_instructor_email_text);
        course_wrapper.addView(course_instructor_email);

        TextView course_instructor_phone = new TextView(this);
        String course_instructor_phone_text = String.format(getResources().getString(R.string.course_start_date_text), course.getInstructor_Phone());
        course_instructor_phone.setTextColor(ContextCompat.getColor(this, R.color.black));
        course_instructor_phone.setTextSize(15);
        course_instructor_phone.setText(course_instructor_phone_text);
        course_wrapper.addView(course_instructor_phone);

        LinearLayout.LayoutParams course_bottom_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        course_bottom_layout.setMargins(0,0,0,50);
        course_instructor_phone.setLayoutParams(course_bottom_layout);

        return course_wrapper;
    }

    // build add course button bar
    private LinearLayout build_course_button_bar() {
        LinearLayout course_button_bar = new LinearLayout(this);
        course_button_bar.setGravity(Gravity.CENTER);

        LinearLayout add_course_wrapper = new LinearLayout(this);
        add_course_wrapper.setGravity(Gravity.CENTER);
        Button btn_add_course = new Button(this);
        btn_add_course.setText("Add Course");
        btn_add_course.setTag(R.id.button_name, "button_update_term");
        btn_add_course.setTag(R.id.term_id, cl_term_id);
        btn_add_course.setTag(R.id.course_id, -1);
        btn_add_course.setWidth(screen_width / 3);
        btn_add_course.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btn_add_course.setOnClickListener(this);

        add_course_wrapper.addView(btn_add_course);
        course_button_bar.addView(add_course_wrapper);
        return course_button_bar;
    }
}