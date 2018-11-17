package fivesecond.it.dut.comicsworld;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


import fivesecond.it.dut.comicsworld.adapters.ContentRecyclerAdapter;
import fivesecond.it.dut.comicsworld.models.ContentComic;

public class ReadComic extends AppCompatActivity {
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRV;
    private ContentRecyclerAdapter mAdapter;
    private ProgressBar mProBar;
    private TextView tvCurrentChap;

    private int mTotalItemCount;
    private int mLastVisibleItemPosition;
    private boolean mIsLoading;
    private int mPostsPerPage;
    private String url;
    private int chap;
    private int totalChap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comic);

        init();
        setWidgets();
        getWidgets();
        getImageComic(null);
        addListeners();
    }


    private void init() {
        mTotalItemCount = 0;
        mPostsPerPage = 5;
        mIsLoading = false;

        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ContentRecyclerAdapter();

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        chap = intent.getIntExtra("chap", 1);
        totalChap = intent.getIntExtra("totalChap", 1);
    }

    private void setWidgets() {
        mProBar = findViewById(R.id.prBar);
        mRV = findViewById(R.id.rv);
        tvCurrentChap = findViewById(R.id.tvCurrentChap);
    }

    private void getWidgets() {
        mRV.setLayoutManager(mLayoutManager);
        mRV.setHasFixedSize(true);
        mRV.setAdapter(mAdapter);
        setCurrentChap();
    }

    private void addListeners() {
        mRV.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mTotalItemCount = mLayoutManager.getItemCount();
                mLastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();

                if (!mIsLoading && mTotalItemCount <= (mLastVisibleItemPosition + mPostsPerPage)) {
                    getImageComic(mAdapter.getLastItemId());
                    mIsLoading = true;
                }
            }
        });
    }

    private void setCurrentChap()
    {
        tvCurrentChap.setText(chap + " / " + totalChap);
    }
    private void getImageComic(String nodeId) {
        mProBar.setVisibility(View.VISIBLE);
        Query query;

        if (nodeId == null) {
            query = FirebaseDatabase.getInstance().getReference()
                    .child("urlComic")
                    .child(url)
                    .child(String.valueOf(chap))
                    .orderByKey()
                    .limitToFirst(mPostsPerPage);
        }
        else {
            query = FirebaseDatabase.getInstance().getReference()
                    .child("urlComic")
                    .child(url)
                    .child(String.valueOf(chap))
                    .orderByKey()
                    .startAt(String.valueOf(Integer.parseInt(nodeId) + 1))
                    .limitToFirst(mPostsPerPage);
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<ContentComic> contentComics = new ArrayList<>();
                for (DataSnapshot urlSnapshot : dataSnapshot.getChildren()) {
                    ContentComic url = new ContentComic(urlSnapshot.getKey(), urlSnapshot.getValue().toString());
                    contentComics.add(url);
                }

                mAdapter.addAll(contentComics);
                mIsLoading = false;
                mProBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mIsLoading = false;
            }
        });
    }
}
