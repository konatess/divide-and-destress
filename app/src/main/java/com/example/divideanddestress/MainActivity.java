package com.example.divideanddestress;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnAssignmentListener {

    private static final String TAG = "MainActivity";
    private static final String EXTRA_NAME = "com.example.divideanddestress.NAME";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mDues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG, "onCreate: started.");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateAssignmentActivity.class);
                startActivity(intent);
            }
        });

//        deleteFile("");
        getFiles();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getFiles() {
        Log.d(TAG, "getFiles: Getting files...");
        String[] files = fileList();
        // Check for files with prefix and display only those
        for (String file : files)
        {
            if (file.startsWith(getString(R.string.prefix))) {
                Assignment assignment = new Assignment().getAssignment(this, file);

                // The info from the file to each of the ArrayLists
                if (assignment != null) {
                    mNames.add(assignment.name);
                    mDues.add(getString(R.string.list_item_due, assignment.daysRemaining));
                }
            }
        }
        Log.d("FILE_NAMES", Arrays.toString(files));

        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: initiating RecyclerView");

        RecyclerView recyclerView = findViewById(R.id.recyclerViewMain);
        RecyclerViewAdapter adapter =  new RecyclerViewAdapter(mNames, mDues, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void OnAssignmentClick(int position) {
        Log.d(TAG, "OnAssignmentClick: clicked " + position);
        Intent intent = new Intent(this, DisplayAssignmentActivity.class);
        intent.putExtra(EXTRA_NAME, getString(R.string.prefix) + mNames.get(position));
        startActivity(intent);
//        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
}
