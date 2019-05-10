package com.example.divideanddestress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateAssignmentActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "com.example.myfirstapp.NAME";
    public static final String EXTRA_NUM_UNITS = "com.example.myfirstapp.NUM_UNITS";
    public static final String EXTRA_DUE = "com.example.myfirstapp.DUE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assignment);
    }
    public void saveAssignment(View view) {
        Intent intent = new Intent(this, DisplayAssignmentActivity.class);
        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editNumUnits = (EditText) findViewById(R.id.editNumUnits);
        EditText editDue = (EditText) findViewById(R.id.editDue);
        String name = editName.getText().toString();
        String numUnitsText = editNumUnits.getText().toString();
        int numUnits = Integer.parseInt(numUnitsText);
        String dueText = editDue.getText().toString();
        int due = Integer.parseInt(dueText);
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_NUM_UNITS, numUnits);
        intent.putExtra(EXTRA_DUE, due);
        startActivity(intent);
    }
}
