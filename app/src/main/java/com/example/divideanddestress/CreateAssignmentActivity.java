package com.example.divideanddestress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CreateAssignmentActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "com.example.divideanddestress.NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assignment);
    }
    public void saveAssignment(View view) {
        EditText editName = findViewById(R.id.editName);
        String name = editName.getText().toString();
        Assignment assignment = new Assignment();
        assignment.name = name;
        try {
            //Saving the "assign-" prefix in the string manager and calling resulted in
            // weird number strings. So this string is written out here and in
            // DisplayAssignmentActivity
            FileOutputStream outputStream = openFileOutput("assign-" + name, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(assignment);
            out.close();
            outputStream.close();
            System.out.print("Saved!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, DisplayAssignmentActivity.class);
        intent.putExtra(EXTRA_NAME, name);
//        EditText editNumUnits = (EditText) findViewById(R.id.editNumUnits);
//        EditText editDue = (EditText) findViewById(R.id.editDue);
//        String numUnitsText = editNumUnits.getText().toString();
//        int numUnits = Integer.parseInt(numUnitsText);
//        String dueText = editDue.getText().toString();
//        int due = Integer.parseInt(dueText);
//        intent.putExtra(EXTRA_NUM_UNITS, numUnits);
//        intent.putExtra(EXTRA_DUE, due);
        startActivity(intent);
    }
}
