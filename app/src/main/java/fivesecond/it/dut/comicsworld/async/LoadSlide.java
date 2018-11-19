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
import fivesecond.it.dut.comicsworld.models.Slide;

public class LoadSlide extends AsyncTask<Void, Slide, Void> {

    @SuppressLint("StaticFieldLeak")
    private HomeScreenActivity mActivity;

    public LoadSlide(HomeScreenActivity activity)
    {
        mActivity = activity;
    }

    protected Void doInBackground(Void... voids) {
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference().child("slides");

        Query query;
        query = databaseReference.orderByChild("id").limitToLast(3);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSniapshot : dataSnapshot.getChildren()) {
                    Slide slide = postSniapshot.getValue(Slide.class);
                    publishProgress(slide);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return null;
    }

    protected void onProgressUpdate(Slide... slides) {

        mActivity.addToListSlide(slides[0]);
    }
}


