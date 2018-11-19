package fivesecond.it.dut.comicsworld.async;


import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fivesecond.it.dut.comicsworld.HomeScreenActivity;
import fivesecond.it.dut.comicsworld.controllers.TypeController;
import fivesecond.it.dut.comicsworld.models.Type;

public class LoadType extends AsyncTask<Void, Type, Void> {

    private HomeScreenActivity mActivity;

    public LoadType(HomeScreenActivity activity)
    {
        mActivity = activity;
    }

    protected Void doInBackground(Void... voids) {
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference();
        databaseReference.child("types").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Type type = dataSnapshot.getValue(Type.class);
                if(type != null) publishProgress(type);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return null;
    }

    protected void onProgressUpdate(Type... types) {
        TypeController.getInstance().addTypeToList(types[0]);
        mActivity.addToListType(types[0]);
    }
}


