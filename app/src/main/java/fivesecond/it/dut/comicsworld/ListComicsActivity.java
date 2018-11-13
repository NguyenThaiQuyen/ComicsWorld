package fivesecond.it.dut.comicsworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fivesecond.it.dut.comicsworld.async.LoadComicCondition;

public class ListComicsActivity extends AppCompatActivity {

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comics);

        init();
    }

    private void init() {
        Intent intent = getIntent();
        String idType = intent.getStringExtra("idType");
        new LoadComicCondition(this, "idType", idType).execute();
    }

}
//
//    private void init() {
////        Intent intent = getIntent();
////        mList = (ArrayList<Comic>)intent.getSerializableExtra("type");
//        lvtest = findViewById(R.id.lvListComic);
//        mList = new ArrayList<>();
//        pullback = new ArrayList<>();
//        mAdapter = new ListViewAdapder(this, R.layout.item_list_comics, mList);
//
//        lvtest.setAdapter(mAdapter);
//
//        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference().child("comics");
//
//        Query query = databaseReference.orderByChild("idType").equalTo("2");
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSniapshot: dataSnapshot.getChildren()) {
//                    Comic comic = postSniapshot.getValue(Comic.class);
//                    mList.add(comic);
//                    mAdapter.notifyDataSetChanged();
//                }
//                pullback.addAll(mList);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void getWidgets() {
//
//    }
//
//    private void setWidgets() {
//        searchView = findViewById(R.id.search_view);
//    }
//
//    private void addListener() {
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                ArrayList<Comic> arrayList = new ArrayList<>();
//                for (Comic c: mList) {
//                    if(c.getName().toLowerCase().contains(query.toLowerCase())) arrayList.add(c);
//                }
//                mList.clear();
//                mList.addAll(arrayList);
//                mAdapter.notifyDataSetChanged();
//                System.out.println(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                ArrayList<Comic> arrayList = new ArrayList<>();
//                mList.clear();
//                mList.addAll(pullback);
//                for (Comic c: mList) {
//                    if(c.getName().toLowerCase().contains(newText.toLowerCase())) arrayList.add(c);
//                }
//                mList.clear();
//                mList.addAll(arrayList);
//                mAdapter.notifyDataSetChanged();
//                return false;
//            }
//        });
//
//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                mList.clear();
//                mList.addAll(pullback);
//                return false;
//            }
//        });
//    }
//

//}