// TermsListActivity.java - list of terms to choose from
// version 1.0b
// Christopher D. Norris (cnorris@wgu.edu)
// Western Governors University
// Student ID: 000493268
//
// 2/10/2023 - initial development

package android.chrisnorris.UI;

import android.chrisnorris.Database.AppDatabase;
import android.chrisnorris.Database.Term;
import android.chrisnorris.R;
import android.chrisnorris.Utilities;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.List;

public class TermsListActivity extends AppCompatActivity implements View.OnClickListener {
    // cl_ = class wide scope
    private String cl_ACTIVITY_NAME;
    private LinearLayout cl_MAIN_LAYOUT;
    private int cl_screen_width;

    // c_ = CONSTANTS
    private String c_APP_TITLE = "Scheduler";
    private String c_NOT_USED = "NOT USED";
    private String c_HEADER_TITLE = "TERM LIST";
    private String c_TERM_NAME = "Term Name: ";
    private String c_START_DATE = "Start Date: ";
    private String c_END_DATE = "End Date: ";

    // handles activity start up / configures action bar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        // get activity name for logging use
        cl_ACTIVITY_NAME = this.getClass().getSimpleName();

        // get screen width
        cl_screen_width = Utilities.get_screen_width(this);

        // configure action bar
        ActionBar action_bar = getSupportActionBar();
        action_bar.setTitle(" " + c_APP_TITLE);
        action_bar.setIcon(R.drawable.school);
        action_bar.setDisplayUseLogoEnabled(true);
        action_bar.setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get main layout
        cl_MAIN_LAYOUT = findViewById(R.id.main_layout);

        // build term list
        buildTermList();
    }

    // handles resume - when user deletes term, it will update
    // terms list accordingly
    @Override
    public void onResume() {
        super.onResume();

        // clear layout
        cl_MAIN_LAYOUT.removeAllViews();

        // rebuild layout
        buildTermList();
    }

    // dynamically generates term list layout
    private void buildTermList() {
        List<Term> terms = get_terms();

        // layouts
        LinearLayout.LayoutParams layout_terms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout_terms.setMargins(50,50,50,50);

        // generate header row
        LinearLayout header_row = Utilities.build_header_row(this, c_HEADER_TITLE);
        cl_MAIN_LAYOUT.addView(header_row);

        // configure term_wrapper
        LinearLayout term_wrapper = new LinearLayout(this);
        term_wrapper.setOrientation(LinearLayout.VERTICAL);
        term_wrapper.setLayoutParams(layout_terms);

        // iterate through terms and call build_term to build
        // term LinearLayout
        for (Term term : terms) {
            term_wrapper.addView(build_term(term));
        }

        // add term LinearLayout to the main layout
        cl_MAIN_LAYOUT.addView(term_wrapper);

        LinearLayout term_button_bar = build_term_button_bar();
        cl_MAIN_LAYOUT.addView(term_button_bar);
    }

    // handles options menu inflation and display
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

    // click handler for term list
    @Override
    public void onClick(View v) {
        int term_id = (int) v.getTag(R.id.term_id);
        String button_name = (String) v.getTag(R.id.button_name);

        switch (button_name) {
            case "btn_add_term":
                Utilities.switchActivityWithValue(this, TermsDetailActivity.class, getString(R.string.form_mode_add), -1);
                break;
            case "btn_select_term":
                Utilities.switchActivityWithValue(this, TermsDetailActivity.class, c_NOT_USED, term_id);
                break;
        }
    }

    // returns all terms from db
    private List<Term> get_terms() {
        AppDatabase db = AppDatabase.getInstance(this);
        List<Term> terms = db.termDao().getTerms();
        return terms;
    }

    // generates term rows
    private LinearLayout  build_term(Term term) {
        // get term text
        String term_name =  c_TERM_NAME + term.getTerm_Name();
        String term_start = c_START_DATE + term.getStart_Date();
        String term_end =   c_END_DATE + term.getEnd_Date();

        // create wrapper container for term details
        LinearLayout wrapper_term_details = new LinearLayout(this);
        wrapper_term_details.setOrientation(LinearLayout.VERTICAL);
        wrapper_term_details.setTag(R.id.term_id, term.getId());
        wrapper_term_details.setTag(R.id.button_name, "btn_select_term");
        wrapper_term_details.setOnClickListener(this);

        // term name
        TextView view_term_name = new TextView(this);
        view_term_name.setTextColor(ContextCompat.getColor(this, R.color.purple_700));
        view_term_name.setTextSize(25);
        view_term_name.setText(term_name);
        wrapper_term_details.addView(view_term_name);

        // start date
        TextView view_term_start = new TextView(this);
        view_term_start.setTextColor(ContextCompat.getColor(this, R.color.purple_700));
        view_term_start.setTextSize(25);
        view_term_start.setText(term_start);
        wrapper_term_details.addView(view_term_start);

        // end date
        TextView view_term_end = new TextView(this);
        view_term_end.setTextColor(ContextCompat.getColor(this, R.color.purple_700));
        view_term_end.setTextSize(25);
        view_term_end.setText(term_end);
        wrapper_term_details.addView(view_term_end);

        // term layout with 50 bottom margin to space each term
        LinearLayout.LayoutParams layout_term = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout_term.setMargins(0,50,0,50);
        wrapper_term_details.setLayoutParams(layout_term);

        // return Linear Layout containing term details
        return wrapper_term_details;
    }

    // build add term button bar
    private LinearLayout build_term_button_bar() {
        LinearLayout term_button_bar = new LinearLayout(this);

        // layout for term button
        LinearLayout.LayoutParams layout_term_button = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout_term_button.setMargins(50,50,50,50);

        LinearLayout add_term_wrapper = new LinearLayout(this);
        Button btn_add_term = new Button(this);
        btn_add_term.setText(R.string.btn_add_term_text);
        btn_add_term.setTag(R.id.button_name, "btn_add_term");
        btn_add_term.setTag(R.id.term_id, -1);
        btn_add_term.setTag(R.id.course_id, -1);
        btn_add_term.setWidth(cl_screen_width / 3);
        btn_add_term.setLayoutParams(layout_term_button);
        btn_add_term.setOnClickListener(this);


        add_term_wrapper.addView(btn_add_term);
        term_button_bar.addView(add_term_wrapper);
        return term_button_bar;
    }

}