package fivesecond.it.dut.comicsworld;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView tvCurrentChap;
    private TextView tvNext;
    private TextView tvPre;

    private boolean mIsLoading;
    private boolean isFinished;
    private int mPostsPerPage;
    private String mUrl;
    private int mChap;
    private int totalChap;

    private SharedPreferences sharedPreferences;
    private static final String myref = "currentComic";

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
        isFinished = false;
        mPostsPerPage = 5;
        mIsLoading = false;

        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ContentRecyclerAdapter();

        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        mChap = intent.getIntExtra("chap", 1);
        totalChap = intent.getIntExtra("totalChap", 1);

        sharedPreferences = getSharedPreferences(myref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("urlComic", mUrl);
        editor.putInt("chap", mChap);
        editor.putInt("totalChap", totalChap);
        editor.apply();
    }

    private void setWidgets() {
        mRV = findViewById(R.id.rv);
        tvCurrentChap = findViewById(R.id.tvCurrentChap);
        tvNext = findViewById(R.id.tvNext);
        tvPre = findViewById(R.id.tvPre);
    }

    private void getWidgets() {
        mRV.setLayoutManager(mLayoutManager);
        //mRV.setHasFixedSize(true);
        mRV.setNestedScrollingEnabled(false);
        mRV.setAdapter(mAdapter);
        setCurrentChap();

        if(mChap == 1) tvPre.setText("");
        if(mChap == totalChap) tvNext.setText("");
    }

    private void addListeners() {
        mRV.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (mIsLoading == false && isFinished == false) {
                    getImageComic(mAdapter.getLastItemId());
                    mIsLoading = true;
                }
            }
        });

        if(mChap  < totalChap)
        {
            tvNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext() ,ReadComic.class);
                    intent.putExtra("url", mUrl);
                    intent.putExtra("chap", mChap + 1);
                    intent.putExtra("totalChap", totalChap);

                    startActivity(intent);
                }
            });
        }

        if(mChap > 1)
        {
            tvPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext() ,ReadComic.class);
                    intent.putExtra("url", mUrl);
                    intent.putExtra("chap", mChap - 1);
                    intent.putExtra("totalChap", totalChap);

                    startActivity(intent);
                }
            });
        }
    }

    private void setCurrentChap()
    {
        tvCurrentChap.setText(mChap + " / " + totalChap);
    }
    private void getImageComic(String nodeId) {
        Query query;

        if (nodeId == null) {
            query = FirebaseDatabase.getInstance().getReference()
                    .child("urlComic")
                    .child(mUrl)
                    .child(String.valueOf(mChap))
                    .orderByKey()
                    .limitToFirst(mPostsPerPage);
        }
        else {
            query = FirebaseDatabase.getInstance().getReference()
                    .child("urlComic")
                    .child(mUrl)
                    .child(String.valueOf(mChap))
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

                if(contentComics.size() < mPostsPerPage) isFinished = true;
                mAdapter.addAll(contentComics);
                mIsLoading = false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mIsLoading = false;
            }
        });
    }


    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
        super.onBackPressed();
    }
}
