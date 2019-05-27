package com.thebenk.divideanddestress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.thebenk.divideanddestress.R;

public class CreateAssignmentActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "com.thebenkthebenk.divideanddestress.NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assignment);
    }

    public void saveAssignment(View view) {
        // Convert user entries to usable data
        EditText createName = findViewById(R.id.createName);
        String name = createName.getText().toString();

        EditText createNumUnits = findViewById(R.id.createNumUnits);
        String numUnitsText = createNumUnits.getText().toString();
        short numUnits = Short.parseShort(numUnitsText);

        EditText createDue = findViewById(R.id.createDue);
        String dueText = createDue.getText().toString();
        short due = Short.parseShort(dueText);

        // Build Assignment object to save
        Assignment assignment = new Assignment();
        assignment.name = name;
        assignment.unitsTotal = numUnits;
        assignment.daysRemaining = due;
        assignment.daysTotal = due;
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
