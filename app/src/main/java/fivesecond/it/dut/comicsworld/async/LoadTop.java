package fivesecond.it.dut.comicsworld.async;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import fivesecond.it.dut.comicsworld.HomeScreenActivity;

import fivesecond.it.dut.comicsworld.models.Comic;

public class LoadTop extends AsyncTask<Void, Comic, Void> {

    @SuppressLint("StaticFieldLeak")
    private HomeScreenActivity mActivity;

    public LoadTop(HomeScreenActivity activity)
    {
        mActivity = activity;
    }

    protected Void doInBackground(Void... voids) {
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference().child("comics");

        Query query;
        query = databaseReference.orderByChild("rating").limitToLast(6);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSniapshot : dataSnapshot.getChildren()) {
                    Comic comic = postSniapshot.getValue(Comic.class);
                    publishProgress(comic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return null;
    }

    protected void onProgressUpdate(Comic... comics) {

        mActivity.addToListTop(comics[0]);
    }
}


