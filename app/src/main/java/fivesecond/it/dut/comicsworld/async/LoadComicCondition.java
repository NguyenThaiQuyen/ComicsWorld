package fivesecond.it.dut.comicsworld.async;



import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ListView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.R;
import fivesecond.it.dut.comicsworld.adapters.ListViewAdapder;
import fivesecond.it.dut.comicsworld.models.Comic;

public class LoadComicCondition extends AsyncTask<Void, Comic, Void> {

    Activity parContext;
    int idType;
    ListView lvtest;
    ArrayList<Comic> mList;
    ListViewAdapder mAdapter;

    public LoadComicCondition(Activity ctx, int _idType)
    {
        parContext = ctx;
        idType = _idType;
        lvtest = parContext.findViewById(R.id.lvListComic);
        mList = new ArrayList<>();
        mAdapter = new ListViewAdapder(parContext, R.layout.item_list_comics, mList);

        lvtest.setAdapter(mAdapter);
    }

    protected Void doInBackground(Void... voids) {
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference().child("comics");


        Query query = databaseReference.orderByChild("idType").equalTo(idType);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSniapshot: dataSnapshot.getChildren()) {
                        Comic comic = postSniapshot.getValue(Comic.class);
                     publishProgress(comic);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return null;
    }

    protected void onProgressUpdate(Comic... comics) {

        mList.add(comics[0]);
        mAdapter.notifyDataSetChanged();

    }
}


