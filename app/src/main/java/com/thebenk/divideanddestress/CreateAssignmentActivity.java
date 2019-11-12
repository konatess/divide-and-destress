package com.thebenk.divideanddestress;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CreateAssignmentActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "com.thebenk.divideanddestress.NAME";
//  Text view to choose due date, and on click listener
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
//  Save year, month, day temporarily every time the calendar is picked.
    private int chosenYear;
    private int chosenMonth;
    private int chosenDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assignment);
        mDisplayDate = findViewById(R.id.createDate);

//      Listener for click to edit Date TextView
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year;
                int month;
                int dayOfMonth;
                if (chosenYear == 0) {
                    Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                }
                else {
                    year = chosenYear;
                    month = chosenMonth;
                    dayOfMonth = chosenDay;
                }

                DatePickerDialog dialog = new DatePickerDialog(
                        CreateAssignmentActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, dayOfMonth
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

//      Listener for finish choosing date, display in text area
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Calendar picker starts from 0 for January, +1 for human readability.
                mDisplayDate.setText(getString(R.string.display_date_output, year, month+1, dayOfMonth));
                chosenYear = year;
                chosenMonth = month;
                chosenDay = dayOfMonth;
            }
        };
    }

    public void saveAssignment(View view) {
        // Convert user entries to usable data
        EditText createName = findViewById(R.id.createName);
        String name = createName.getText().toString().trim();

        EditText createNumUnits = findViewById(R.id.createNumUnits);
        String numUnitsText = createNumUnits.getText().toString();
        short numUnits = Short.parseShort(numUnitsText);

        EditText createDue = findViewById(R.id.createDue);
        String dueText = createDue.getText().toString();
        short due = Short.parseShort(dueText);

        // Build year
        Calendar c = Calendar.getInstance();
        c.set(chosenYear, chosenMonth, chosenDay);

        // Build Assignment object to save
        Assignment assignment = new Assignment();
        assignment.name = name;
        assignment.unitsTotal = numUnits;
        assignment.daysRemaining = due;
        assignment.daysTotal = due;
        assignment.dueDate = c.getTimeInMillis();
        // Add prefix for easy identification
        String fileName = getString(R.string.prefix) + name;
        // Save Assignment to file
        assignment.saveAssignment(this, fileName, assignment);

        // Open Display Activity with the new Assignment's info
        Intent intent = new Intent(this, DisplayAssignmentActivity.class);
        intent.putExtra(EXTRA_NAME, fileName);
        startActivity(intent);
    }
}
