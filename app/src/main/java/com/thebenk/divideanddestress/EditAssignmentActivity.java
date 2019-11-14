package com.thebenk.divideanddestress;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.threeten.bp.LocalDate;

public class EditAssignmentActivity extends AppCompatActivity {

    private static final String TAG = "EditAssignmentActivity";
    public static final String EXTRA_NAME = "com.thebenk.divideanddestress.NAME";
    private static String oldName;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    // Save year, month, day temporarily every time the calendar is picked.
    private int chosenYear;
    private int chosenMonth;
    private int chosenDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);
        mDisplayDate = findViewById(R.id.editDue);

        // Listener for click to edit Date TextView
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year;
                int month;
                int dayOfMonth;
                if (chosenYear == 0) {
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
                        EditAssignmentActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        // Date picker starts from 0 for January, -1 to convert from LocalDate format.
                        year, month-1, dayOfMonth
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // Listener for finish choosing date, display in text area
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int pickedYear, int pickedMonth, int pickedDayOfMonth) {
                // Date picker starts from 0 for January, +1 for human readability, and for ThreeTen compatibility.
                pickedMonth++;
                mDisplayDate.setText(LocalDate.of(pickedYear, pickedMonth, pickedDayOfMonth).toString());
                chosenYear = pickedYear;
                chosenMonth = pickedMonth;
                chosenDay = pickedDayOfMonth;
            }
        };

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

            TextView editDueDate = findViewById(R.id.editDue);
            editDueDate.setText(assignment.dueDate.toString());

            TextView editStartDate = findViewById(R.id.editStart);
            editStartDate.setText(assignment.startDate.toString());

            chosenYear = assignment.dueDate.getYear();
            chosenMonth = assignment.dueDate.getMonthValue();
            chosenDay = assignment.dueDate.getDayOfMonth();
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

        TextView editStart = findViewById(R.id.editStart);
        String startText = editStart.getText().toString();
        LocalDate start = LocalDate.parse(startText);

        // Build Assignment object to save
        Assignment assignment = new Assignment();
        assignment.name = name;
        assignment.unitsCompleted = unitsCompleted;
        assignment.unitsTotal = unitsTotal;
        assignment.dueDate = LocalDate.of(chosenYear, chosenMonth, chosenDay);;
        assignment.startDate = start;

        // if name is changed, delete old file by name.
        if (!oldName.equals(assignment.name)) {
            deleteFile(getString(R.string.prefix) + oldName);
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
