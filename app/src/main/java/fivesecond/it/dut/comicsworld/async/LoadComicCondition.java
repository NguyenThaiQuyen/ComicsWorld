package fivesecond.it.dut.comicsworld.async;


import android.os.AsyncTask;
import android.support.annotation.NonNull;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import fivesecond.it.dut.comicsworld.ListComicsActivity;

import fivesecond.it.dut.comicsworld.models.Comic;

public class LoadComicCondition extends AsyncTask<Void, Comic, Void> {

    ListComicsActivity parContext;
    String con;
    String attr;



    public LoadComicCondition(ListComicsActivity ctx, String _attr, String _con)
    {
        parContext = ctx;
        con = _con;
        attr = _attr;

    }

    protected Void doInBackground(Void... voids) {
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference().child("comics");

        Query query = databaseReference.orderByChild(attr).equalTo(con);
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


        parContext.notify(comics[0]);


    }
}


