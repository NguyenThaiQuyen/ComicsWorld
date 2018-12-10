package fivesecond.it.dut.comicsworld;

import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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
import fivesecond.it.dut.comicsworld.models.Comic;
import fivesecond.it.dut.comicsworld.models.ContentComic;
import fivesecond.it.dut.comicsworld.models.Type;

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
    private Comic comic;
    private ArrayList<Type> mListType;



    private SharedPreferences sharedPreferences;
    private static final String myref = "currentComic";


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comic);

        init();
        getWidgets();
        setWidgets();
        getImageComic(null);
        addListeners();
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void init() {
        isFinished = false;
        mPostsPerPage = 5;
        mIsLoading = false;

        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ContentRecyclerAdapter();

        Intent intent = getIntent();
        mChap = intent.getIntExtra("chap", 1);
        comic = (Comic)intent.getSerializableExtra("comic");
        mListType = (ArrayList<Type>) intent.getSerializableExtra("listType");

        mUrl = comic.getUrl();
        totalChap = comic.getChap();


        sharedPreferences = getSharedPreferences(myref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("url", mUrl);
        editor.putInt("chap", mChap);

        editor.apply();
    }

    private void getWidgets() {
        mRV = findViewById(R.id.rv);
        tvCurrentChap = findViewById(R.id.tvCurrentChap);
        tvNext = findViewById(R.id.tvNext);
        tvPre = findViewById(R.id.tvPre);
    }

    private void setWidgets() {
        mRV.setLayoutManager(mLayoutManager);
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

                    intent.putExtra("chap", mChap + 1);
                    intent.putExtra("comic", comic);
                    intent.putExtra("listType", mListType);

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
                    intent.putExtra("chap", mChap - 1);
                    intent.putExtra("comic", comic);
                    intent.putExtra("listType", mListType);

                    startActivity(intent);
                }
            });
        }

        tvCurrentChap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSharedPre();

                Intent intent = new Intent(getApplicationContext(), MainContentActivity.class);
                intent.putExtra("comic", comic);
                intent.putExtra("listType", mListType);
                startActivity(intent);

            }
        });
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


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onBackPressed() {
        removeSharedPre();
        super.onBackPressed();
    }

    public void removeSharedPre()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("url");
        editor.remove("chap");
        editor.apply();
    }
}
