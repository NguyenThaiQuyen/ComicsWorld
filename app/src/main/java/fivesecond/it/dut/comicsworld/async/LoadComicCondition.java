package fivesecond.it.dut.comicsworld.async;



import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

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
    String con;
    String attr;

    ListView lvtest;
    ArrayList<Comic> mList;
    ListViewAdapder mAdapter;

    public LoadComicCondition(Activity ctx, String _attr, String _con)
    {
        parContext = ctx;
        con = _con;
        attr = _attr;
        lvtest = parContext.findViewById(R.id.lvListComic);
        mList = new ArrayList<>();
        mAdapter = new ListViewAdapder(parContext, R.layout.item_list_comics, mList);

        lvtest.setAdapter(mAdapter);
    }

    protected Void doInBackground(Void... voids) {
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference().child("comics");

        Query query = databaseReference.orderByChild(attr).equalTo(con);
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

        mList.add(0, comics[0]);
        mAdapter.notifyDataSetChanged();

    }
}


