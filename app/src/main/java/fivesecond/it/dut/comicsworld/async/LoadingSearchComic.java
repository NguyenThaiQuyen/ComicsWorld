package fivesecond.it.dut.comicsworld.async;

import android.os.AsyncTask;
import android.support.annotation.NonNull;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import fivesecond.it.dut.comicsworld.SearchableActivity;
import fivesecond.it.dut.comicsworld.models.Comic;

import static fivesecond.it.dut.comicsworld.functions.ConvertUnsigned.ConvertString;

public class LoadingSearchComic extends AsyncTask<Void, Comic, Void> {

    SearchableActivity parContext;
    String mQuery;

    public LoadingSearchComic(SearchableActivity ctx, String query) {
        parContext = ctx;
        mQuery = query;
    }

    protected Void doInBackground(Void... voids) {
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference().child("comics");

        Query query = databaseReference.orderByChild("name");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSniapshot: dataSnapshot.getChildren()) {
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

        if(parContext.getFirstSearch() )
        {
            if(ConvertString(comics[0].getName()).contains(ConvertString(mQuery)) || ConvertString(mQuery).contains(ConvertString(comics[0].getName())))
                parContext.firstSearch(comics[0]);

        }
        parContext.notify(comics[0]);


    }
}



