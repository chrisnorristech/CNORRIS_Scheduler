package android.chrisnorris.UI;

import android.chrisnorris.Database.AppDatabase;
import android.chrisnorris.Database.Course;
import android.chrisnorris.Database.Term;
import android.chrisnorris.R;
import android.chrisnorris.Utilities;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.List;

public class TermViewerActivity extends AppCompatActivity {
    String ActivityName;
    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_viewer);

        ActivityName = this.getClass().getSimpleName();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" Scheduler");
        actionBar.setIcon(R.drawable.school);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        table = (TableLayout) findViewById(R.id.term_view_table);
        buildTermViewTable(table);
    }

    private void buildTermViewTable(TableLayout table) {
        AppDatabase db = AppDatabase.getInstance(this);
        List<Term> terms = db.termDao().getTerms();

        TableRow.LayoutParams termHeaderRowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow headerRow = new TableRow(this);
        headerRow.setBackgroundColor(ContextCompat.getColor(this,R.color.purple_700));
        TextView headerText = new TextView(this);
        headerText.setTextSize(25);
        LinearLayout.LayoutParams headerTextLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        headerText.setText("Term Viewer");
        headerText.setTextColor(ContextCompat.getColor(this, R.color.white));
        headerText.setWidth(width);
        headerText.setGravity(Gravity.CENTER_HORIZONTAL);
        headerText.setLayoutParams(headerTextLayout);

        headerRow.addView(headerText, termHeaderRowLayout);
        table.addView(headerRow);

        for (Term term : terms) {
            String termDetails = term.asString();
            Log.d(ActivityName + "==>", "Retrieved: " + termDetails);
            TableRow row = new TableRow(this);
            TextView termText = new TextView(this);
            termText.setTextColor(ContextCompat.getColor(this, R.color.purple_700));
            termText.setTextSize(25);
            termText.setText(termDetails);

            TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            rowLayout.setMargins(0,50,0,0);

            row.addView(termText, rowLayout);
            table.addView(row);
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