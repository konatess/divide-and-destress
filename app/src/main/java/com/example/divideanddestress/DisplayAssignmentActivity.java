package com.example.divideanddestress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DisplayAssignmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_assignment);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String fName = intent.getStringExtra(CreateAssignmentActivity.EXTRA_NAME);
        try {
            this.display(fName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void display(String fileName) throws IOException, ClassNotFoundException {
        Assignment assignment;
        try {
            //Saving the "assign-" prefix in the string manager and calling resulted in
            // weird number strings. So this string is written out here and in
            // CreateAssignmentActivity
            FileInputStream inputStream = openFileInput("assign-" + fileName);
            ObjectInputStream in = new ObjectInputStream(inputStream);
            assignment = (Assignment) in.readObject();
            in.close();
            inputStream.close();
        }
        finally {
            System.out.print("Getting Name");
        }

        // Capture the layout's TextView and set the string as its text
        TextView textViewName = findViewById(R.id.displayName);
        textViewName.setText(getString(R.string.display_name, assignment.name));
    }
}
