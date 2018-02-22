package com.example.peter.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    String TAG = "peter";
    List<Object> objectList;
    List<Object>
            objects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        objects = new ArrayList<>();
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        objectList = new ArrayList<>();
        myRef = database.getReference("objects");

        objectList.add(new Object(1, "d1"));
        objectList.add(new Object(2, "d2"));

        boolean added = addToFirebase(myRef, objectList);
        if (added) {
            MyToast("added successfully");
        } else {
            MyToast("failed to add");
        }

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot objectsSnapshot : dataSnapshot.getChildren()) {
                    Long id = (Long) objectsSnapshot.child("id").getValue();
                    String data = (String) objectsSnapshot.child("data").getValue();
                    objects.add(new Object(id.intValue(), data));

                    Log.d("id", "" + id);
                    Log.d("data", "" + data);
                    Log.d("size", "" + objects.size());

                }
                // for test it .. should be d2

                MyToast("Data of Second Object " + objects.get(1).getData());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    public void MyToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public boolean addToFirebase(DatabaseReference reference, List<Object> objects) {
        try {
            Map<String, Object> objectHashMap = new HashMap<>();
            for (Object o : objects) {
                objectHashMap.put(String.valueOf(o.getId()), o);
            }
            reference.setValue(objectHashMap);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
