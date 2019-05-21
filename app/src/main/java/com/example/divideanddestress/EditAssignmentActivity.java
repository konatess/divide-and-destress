package com.example.divideanddestress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class EditAssignmentActivity extends AppCompatActivity {

    private static final String TAG = "EditAssignmentActivity";
//    public static final String EXTRA_NAME = "com.example.divideanddestress.NAME";

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
            EditText editName = findViewById(R.id.editTheNameAgain);
            editName.setText(assignment.name);

            EditText editCompleted = findViewById(R.id.editCompleted);
            editCompleted.setText(String.valueOf(assignment.unitsCompleted));
//
            EditText editTotalUnits = findViewById(R.id.editTotal);
            editTotalUnits.setText(String.valueOf(assignment.unitsTotal));

            EditText editDaysRemaining = findViewById(R.id.editDueEA);
            editDaysRemaining.setText(String.valueOf(assignment.daysRemaining));

            EditText editTotalDays = findViewById(R.id.editStart);
            editTotalDays.setText(String.valueOf(assignment.daysTotal));
        }
    }


}
