package com.thebenk.divideanddestress;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnAssignmentListener {

    private static final String TAG = "MainActivity";
    private static final String EXTRA_NAME = "com.thebenk.divideanddestress.NAME";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mDues = new ArrayList<>();
    private RecyclerViewAdapter mAdapter;

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

        RecyclerView mRecyclerView = findViewById(R.id.recyclerViewMain);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter =  new RecyclerViewAdapter(mNames, mDues, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void OnAssignmentClick(int position) {
        Log.d(TAG, "OnAssignmentClick: clicked " + position);
        Intent intent = new Intent(this, DisplayAssignmentActivity.class);
        intent.putExtra(EXTRA_NAME, getString(R.string.prefix) + mNames.get(position));
        startActivity(intent);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteFile("assign-" + mNames.get(viewHolder.getAdapterPosition()));
            mNames.remove(viewHolder.getAdapterPosition());
            mDues.remove(viewHolder.getAdapterPosition());
            mAdapter.notifyDataSetChanged();
        }
    };
}
