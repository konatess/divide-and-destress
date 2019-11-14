package com.thebenk.divideanddestress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.threeten.bp.LocalDate;
import org.threeten.bp.temporal.ChronoUnit;

public class DisplayAssignmentActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "com.thebenk.divideanddestress.NAME";
    public static String editFileName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_assignment);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String fName = intent.getStringExtra(CreateAssignmentActivity.EXTRA_NAME);
        DisplayAssignmentActivity.editFileName = fName;
        this.display(fName);

        FloatingActionButton fab = findViewById(R.id.fabEdit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayAssignmentActivity.this, EditAssignmentActivity.class);
                intent.putExtra(EXTRA_NAME, DisplayAssignmentActivity.editFileName);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void display(String fileName) {
        // Read saved Assignment file
        Assignment assignment = new Assignment().getAssignment(this, fileName);

        if (assignment == null) {
            throw new AssertionError();
        } else {
            // Get current date
            LocalDate today = LocalDate.now();
            // Get due date
            LocalDate dueDate = assignment.dueDate;
            // Get start date
            LocalDate startDate = assignment.startDate;
            // Calculate total days
            long totalDays = ChronoUnit.DAYS.between(startDate, dueDate);
            // Calculate remaining days
            long remainDays = ChronoUnit.DAYS.between(today, dueDate);
            // Calculate units remaining
            short unitsRemaining = (short) (assignment.unitsTotal - assignment.unitsCompleted);
            // Calculate units per day
            float perDay = (float) unitsRemaining / remainDays;


            // Fill info from file to TextViews
            TextView textViewName = findViewById(R.id.displayName);
            textViewName.setText(getString(R.string.display_name, assignment.name));

            TextView textViewPerDay = findViewById(R.id.displayPerDay);
            if (remainDays > 0) {
                textViewPerDay.setText(getString(R.string.display_units_per_day, perDay));
            }
            else {
                textViewPerDay.setText("OVERDUE");
            }

            TextView textViewCompleted = findViewById(R.id.displayCompleted);
            textViewCompleted.setText(getString(R.string.display_completed, assignment.unitsCompleted, assignment.unitsTotal));

            TextView textViewRemaining = findViewById(R.id.displayRemaining);
            textViewRemaining.setText(getString(R.string.display_units_remaining, unitsRemaining));

            TextView textViewDue = findViewById(R.id.displayDue);
            textViewDue.setText(getString(R.string.list_item_due, remainDays));

            TextView textViewDueDate = findViewById(R.id.displayDueDate);
            textViewDueDate.setText(getString(R.string.list_item_date, dueDate.toString()));
        }
    }
}
