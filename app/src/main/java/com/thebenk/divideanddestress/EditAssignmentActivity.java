package com.thebenk.divideanddestress;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditAssignmentActivity extends AppCompatActivity {

    private static final String TAG = "EditAssignmentActivity";
    public static final String EXTRA_NAME = "com.thebenk.divideanddestress.NAME";
    private static String oldName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String fName = intent.getStringExtra(DisplayAssignmentActivity.EXTRA_NAME);
        this.autofill(fName);
    }

    private void autofill(String fileName) {
        Log.d(TAG, "autofill: started Autofill method");
        // Read saved Assignment file
        Log.d(TAG, "autofill: Trying to read assignment");
        Assignment assignment = new Assignment().getAssignment(this, fileName);

        if (assignment == null) {
            Log.d(TAG, "autofill: Assignment file not found");
            throw new AssertionError();
        } else {
            Log.d(TAG, "autofill: Filling fields.");
            // save name to check for changes when save button pressed
            oldName = assignment.name;
            EditText editName = findViewById(R.id.editName);
            editName.setText(assignment.name);

            EditText editCompleted = findViewById(R.id.editCompleted);
            editCompleted.setText(String.valueOf(assignment.unitsCompleted));
//
            EditText editTotalUnits = findViewById(R.id.editTotal);
            editTotalUnits.setText(String.valueOf(assignment.unitsTotal));

            EditText editDaysRemaining = findViewById(R.id.editDue);
            editDaysRemaining.setText(String.valueOf(assignment.daysRemaining));

            EditText editTotalDays = findViewById(R.id.editStart);
            editTotalDays.setText(String.valueOf(assignment.daysTotal));
        }
    }

    public void saveEditedAssignment(View view) {
        // Convert user entries to usable data
        EditText editName = findViewById(R.id.editName);
        String name = editName.getText().toString().trim();

        EditText editCompleted = findViewById(R.id.editCompleted);
        String unitsCompletedText = editCompleted.getText().toString();
        short unitsCompleted = Short.parseShort(unitsCompletedText);

        EditText editTotalUnits = findViewById(R.id.editTotal);
        String unitsTotalText = editTotalUnits.getText().toString();
        short unitsTotal = Short.parseShort(unitsTotalText);

        EditText editDue = findViewById(R.id.editDue);
        String dueText = editDue.getText().toString();
        short due = Short.parseShort(dueText);

        EditText editStart = findViewById(R.id.editStart);
        String startText = editStart.getText().toString();
        short start = Short.parseShort(startText);

        // Build Assignment object to save
        Assignment assignment = new Assignment();
        assignment.name = name;
        assignment.unitsCompleted = unitsCompleted;
        assignment.unitsTotal = unitsTotal;
        assignment.daysRemaining = due;
        assignment.daysTotal = start;

        // if name is changed, delete old file by name.
        if (!oldName.equals(assignment.name)) {
            deleteFile(getString(R.string.prefix) + assignment.name);
        }
        // Add prefix for easy identification
        String fileName = getString(R.string.prefix) + name;
        // Save Assignment to file
        assignment.saveAssignment(this, fileName, assignment);

        //Open Display Activity with the new Assignment's info
        Intent intent = new Intent(this, DisplayAssignmentActivity.class);
        intent.putExtra(EXTRA_NAME, fileName);
        startActivity(intent);
    }


}
