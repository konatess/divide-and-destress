package com.thebenk.divideanddestress;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Assignment implements Serializable {
    private static final String TAG = "Assignment";

    public String name;
    short unitsTotal;
    short unitsCompleted = 0;
    short daysTotal;
    short daysRemaining;

    public Assignment getAssignment(Context context, String fileName) {
        Assignment assignment = null;
        try {
            FileInputStream inputStream = context.openFileInput(fileName);
            ObjectInputStream in = new ObjectInputStream(inputStream);
            assignment = (Assignment) in.readObject();
            in.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assignment;
    }
    public void saveAssignment(Context context, String fileName, Assignment assignment) {
        try {
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(assignment);
            out.close();
            outputStream.close();
            Log.d(TAG, "saveAssignment: saved " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
