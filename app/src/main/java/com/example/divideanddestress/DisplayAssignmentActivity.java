package com.example.divideanddestress;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class DisplayAssignmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_assignment);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String fName = intent.getStringExtra(CreateAssignmentActivity.EXTRA_NAME);
        this.display(fName);
    }
    public void display(String fileName) {
        // Read saved Assignment file
        Assignment assignment = null;
        try {
            FileInputStream inputStream = openFileInput(fileName);
            ObjectInputStream in = new ObjectInputStream(inputStream);
            assignment = (Assignment) in.readObject();
            in.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        short unitsRemaining = (short) (assignment.unitsTotal - assignment.unitsCompleted);
        float perDay = (float) unitsRemaining / assignment.daysRemaining;

        // Fill info from file to TextViews
        TextView textViewName = findViewById(R.id.displayName);
        textViewName.setText(getString(R.string.display_name, assignment.name));
        TextView textViewPerDay = findViewById(R.id.displayPerDay);
        textViewPerDay.setText(getString(R.string.display_units_per_day, perDay));
        TextView textViewCompleted = findViewById(R.id.displayCompleted);
        textViewCompleted.setText(getString(R.string.display_completed, assignment.unitsCompleted, assignment.unitsTotal));
        TextView textViewRemaining = findViewById(R.id.displayRemaining);
        textViewRemaining.setText(getString(R.string.display_units_remaining, unitsRemaining));
        TextView textViewDue = findViewById(R.id.displayDue);
        textViewDue.setText(getString(R.string.list_item_due, assignment.daysRemaining));
    }
}
