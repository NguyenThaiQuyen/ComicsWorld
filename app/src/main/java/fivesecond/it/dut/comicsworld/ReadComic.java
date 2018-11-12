package fivesecond.it.dut.comicsworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fivesecond.it.dut.comicsworld.async.LoadingContentAsyncTask;

public class ReadComic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comic);

        init();
    }

    private void init() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        int chap = intent.getIntExtra("chap", 1);
        new LoadingContentAsyncTask(this, url, chap).execute();
    }

}
