// AssessmentDetail.java - assessment management
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

import android.chrisnorris.R;
import android.chrisnorris.Utilities;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AssessmentDetailActivity extends AppCompatActivity {

    // handles activity start up / configures action bar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" Scheduler");
        actionBar.setIcon(R.drawable.school);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}