package android.chrisnorris.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.chrisnorris.R;
import android.chrisnorris.Utilities;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" Scheduler");
        actionBar.setIcon(R.drawable.school);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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