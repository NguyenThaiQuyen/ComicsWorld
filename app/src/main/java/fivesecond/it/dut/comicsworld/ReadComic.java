package fivesecond.it.dut.comicsworld;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.adapters.ListViewContentAdapter;
import fivesecond.it.dut.comicsworld.async.LoadingContentAsyncTask;

public class ReadComic extends AppCompatActivity {
    ListView lvtest;
    ArrayList<String> mList;
    ListViewContentAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comic);

        init();


    }


    public void notify(String url)
    {
        mList.add(url);
        mAdapter.notifyDataSetChanged();
    }

    private void init() {

        lvtest = findViewById(R.id.lvComic);
        mList = new ArrayList<>();
        mAdapter = new ListViewContentAdapter(getApplicationContext(), R.layout.item_content_comic, mList);
        lvtest.setAdapter(mAdapter);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        int chap = intent.getIntExtra("chap", 1);
        new LoadingContentAsyncTask(this, url, chap).execute();

    }

}
