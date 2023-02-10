package android.chrisnorris.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.chrisnorris.Database.AppDatabase;
import android.chrisnorris.Database.Term;
import android.chrisnorris.R;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TermAddEditActivity extends AppCompatActivity {
    AppDatabase db;
    int term_id;
    String form_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add_edit);
        db = AppDatabase.getInstance(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            form_mode = extras.getString("form_mode");
            int value = extras.getInt("value");
            term_id = value;
            Log.d("form_mode==>", "+" + form_mode + "=" + "Edit");
            if(form_mode.equals("Edit")) {
                populateForEdit(value);
            } else {
                populateForAdd();
            }
        }
    }

    private void populateForEdit(int term_id) {
        TextView add_edit_label = (TextView)findViewById(R.id.add_edit_form_label);
        EditText term_name = (EditText)findViewById(R.id.term_name);
        EditText start_date = (EditText)findViewById(R.id.start_date);
        EditText end_date = (EditText)findViewById(R.id.end_date);
        Button add_edit_term_button = (Button)findViewById(R.id.add_edit_term_button);

        // set label to Edit Term
        add_edit_label.setText("Edit Term");
        Term termForEdit = db.termDao().getTermById(term_id);
        term_name.setText(termForEdit.getTerm_Name());
        start_date.setText(termForEdit.getStart_Date());
        end_date.setText(termForEdit.getEnd_Date());
        add_edit_term_button.setText("Update Term");
    }

    private void populateForAdd() {
        TextView add_edit_label = (TextView)findViewById(R.id.add_edit_form_label);
        Button add_edit_term_button = (Button)findViewById(R.id.add_edit_term_button);

        // set label to Edit Term
        add_edit_label.setText("New Term");
        add_edit_term_button.setText("Add Term");
    }

    public void HandleAddEditTerm(View view) {
        EditText term_name = (EditText)findViewById(R.id.term_name);
        EditText start_date = (EditText)findViewById(R.id.start_date);
        EditText end_date = (EditText)findViewById(R.id.end_date);

        String term_name_string = term_name.getText().toString();
        String term_start_date = start_date.getText().toString();
        String term_end_date = end_date.getText().toString();

        int errorCount = 0;

        // validate value
        if(term_name_string == "") {
            errorCount++;
        }

        if(term_start_date == "") {
            errorCount++;
        }

        if(term_end_date == "") {
            errorCount++;
        }
        if(errorCount == 0) {
            switch(form_mode) {
                case "Add":
                    Term newTerm = new Term();
                    newTerm.setTerm_Name(term_name_string);
                    newTerm.setStart_Date(term_start_date);
                    newTerm.setEnd_Date(term_end_date);
                    db.termDao().insertTerm(newTerm);
                    finish();
                    break;
                case "Edit":
                    Term editTerm = db.termDao().getTermById(term_id);
                    editTerm.setTerm_Name(term_name_string);
                    editTerm.setStart_Date(term_start_date);
                    editTerm.setEnd_Date(term_end_date);
                    db.termDao().updateTerm(editTerm);
                    finish();
                    break;
            }
        }
    }
}