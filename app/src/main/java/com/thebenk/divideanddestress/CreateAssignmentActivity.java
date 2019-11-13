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

import org.threeten.bp.LocalDate;
import org.threeten.bp.temporal.ChronoUnit;

public class CreateAssignmentActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "com.thebenk.divideanddestress.NAME";
//  Text view to choose due date, and on click listener
    private TextView mDisplayDate;
    private EditText mDaysRemaining;
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
        mDaysRemaining = findViewById(R.id.createDue);

//      Listener for click to edit Date TextView
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year;
                int month;
                int dayOfMonth;
                if (chosenYear == 0) {
//                    Calendar c = Calendar.getInstance();
//                    year = c.get(Calendar.YEAR);
//                    month = c.get(Calendar.MONTH);
//                    dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

                    // Attempting to use ThreeTen instead of Calendar
                    LocalDate now = LocalDate.now();
                    year = now.getYear();
                    month = now.getMonthValue();
                    dayOfMonth = now.getDayOfMonth();
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
                        // Date picker starts from 0 for January, -1 to convert from LocalDate format.
                        year, month-1, dayOfMonth
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

//      Listener for finish choosing date, display in text area
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int pickedYear, int pickedMonth, int pickedDayOfMonth) {
                // Date picker starts from 0 for January, +1 for human readability, and for ThreeTen compatibility.
                pickedMonth = pickedMonth+1;
                mDisplayDate.setText(getString(
                        R.string.display_date_output, pickedYear, pickedMonth, pickedDayOfMonth
                ));
                chosenYear = pickedYear;
                chosenMonth = pickedMonth;
                chosenDay = pickedDayOfMonth;
                long days = ChronoUnit.DAYS.between(
                        LocalDate.now(), LocalDate.of(pickedYear, pickedMonth, pickedDayOfMonth)
                );
                mDaysRemaining.setText(String.valueOf(days));
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

        // Build due date and get today's date as start.
        // Start date will not be editable at this time.
        LocalDate dueDate = LocalDate.of(chosenYear, chosenMonth, chosenDay);
        LocalDate startDate = LocalDate.now();

        // Build Assignment object to save
        Assignment assignment = new Assignment();
        assignment.name = name;
        assignment.unitsTotal = numUnits;
        assignment.dueDate = dueDate;
        assignment.startDate = startDate;
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
