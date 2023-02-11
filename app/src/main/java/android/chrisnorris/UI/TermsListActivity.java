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
import android.widget.EditText;
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

        if(terms.size() == 0) {
                LinearLayout no_terms_available = build_no_terms();
                cl_MAIN_LAYOUT.addView(no_terms_available);
        }

        // iterate through terms and call build_term to build
        // term LinearLayout
        for (Term term : terms) {
            build_term(term);
        }

        // add term LinearLayout to the main layout
        cl_MAIN_LAYOUT.addView(term_wrapper);

        LinearLayout term_button_bar = build_term_button_bar();
        cl_MAIN_LAYOUT.addView(term_button_bar);
    }

    // methods to control what happens when user
    // clicks on the action buttons
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
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
    private void build_term(Term term) {
        // get term text
        String term_name =  term.getTerm_Name();
        String term_start = term.getStart_Date();
        String term_end =   term.getEnd_Date();

        // create labels
        TextView tv_term_name_label = new TextView(this);
        tv_term_name_label.setWidth(300);
        tv_term_name_label.setText(R.string.term_name_label);
        tv_term_name_label.setGravity(Gravity.RIGHT);

        TextView tv_term_start_label = new TextView(this);
        tv_term_start_label.setText(R.string.start_date_label);
        tv_term_start_label.setWidth(300);
        tv_term_start_label.setGravity(Gravity.RIGHT);

        TextView tv_term_end_label = new TextView(this);
        tv_term_end_label.setText(R.string.end_date_label);
        tv_term_end_label.setWidth(300);
        tv_term_end_label.setGravity(Gravity.RIGHT);

        LinearLayout.LayoutParams margin_left_layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        margin_left_layout.setMargins(50,0,0,0);


        LinearLayout.LayoutParams margin_bottom_layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        margin_bottom_layout.setMargins(0,50,0,0);

        // create data
        TextView tv_term_name = new TextView(this);
        tv_term_name.setText(term_name);
        tv_term_name.setWidth(400);
        tv_term_name.setLayoutParams(margin_left_layout);

        TextView tv_term_start = new TextView(this);
        tv_term_start.setText(term_start);
        tv_term_start.setWidth(400);
        tv_term_start.setLayoutParams(margin_left_layout);

        TextView tv_term_end = new TextView(this);
        tv_term_end.setWidth(400);
        tv_term_end.setText(term_end);
        tv_term_end.setLayoutParams(margin_left_layout);

        tv_term_name_label.setTextSize(20);
        tv_term_start_label.setTextSize(20);
        tv_term_end_label.setTextSize(20);
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

        wrapper_term_name.addView(tv_term_name_label);
        wrapper_term_name.addView(tv_term_name);

        wrapper_term_start.addView(tv_term_start_label);
        wrapper_term_start.addView(tv_term_start);

        wrapper_term_end.addView(tv_term_end_label);
        wrapper_term_end.addView(tv_term_end);

        wrapper_input.addView(wrapper_term_name);
        wrapper_input.addView(wrapper_term_start);
        wrapper_input.addView(wrapper_term_end);

        wrapper_input.setLayoutParams(margin_bottom_layout);
        wrapper_input.setOnClickListener(this);
        wrapper_input.setTag(R.id.term_id, term.getId());
        wrapper_input.setTag(R.id.button_name, "btn_select_term");

        cl_MAIN_LAYOUT.addView(wrapper_input);
    }

    // build add term button bar
    private LinearLayout build_term_button_bar() {
        LinearLayout term_button_bar = new LinearLayout(this);
        term_button_bar.setGravity(Gravity.CENTER);

        LinearLayout add_term_wrapper = new LinearLayout(this);
        add_term_wrapper.setOrientation(LinearLayout.VERTICAL);
        Button btn_add_term = new Button(this);
        btn_add_term.setText(R.string.btn_add_term_text);
        btn_add_term.setTag(R.id.button_name, "btn_add_term");
        btn_add_term.setTag(R.id.term_id, -1);
        btn_add_term.setTag(R.id.course_id, -1);
        btn_add_term.setWidth(cl_screen_width / 3);
        btn_add_term.setOnClickListener(this);
        btn_add_term.setGravity(Gravity.CENTER);

        add_term_wrapper.addView(btn_add_term);
        term_button_bar.addView(add_term_wrapper);
        return term_button_bar;
    }

    // build response for when no terms exist
    private LinearLayout build_no_terms() {
        LinearLayout no_term = new LinearLayout(this);
        TextView no_term_text = new TextView(this);

        LinearLayout.LayoutParams term_layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        term_layout.setMargins(0,50,0,0);
        no_term.setLayoutParams(term_layout);

        no_term_text.setGravity(Gravity.CENTER_HORIZONTAL);

        no_term_text.setTextSize(20);
        no_term_text.setText(R.string.no_terms_text);
        no_term.addView(no_term_text);
        return no_term;
    }

}