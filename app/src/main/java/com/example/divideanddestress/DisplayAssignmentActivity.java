package com.example.divideanddestress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayAssignmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_assignment);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String name = intent.getStringExtra(CreateAssignmentActivity.EXTRA_NAME);

        // Capture the layout's TextView and set the string as its text
//        TextView textViewName = findViewById(R.id.displayName);
//        textViewName.setText(name);
    }
}
