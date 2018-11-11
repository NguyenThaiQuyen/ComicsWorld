package fivesecond.it.dut.comicsworld;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;


import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.adapters.ListViewContentAdapter;

import fivesecond.it.dut.comicsworld.async.LoadComicCondition;


public class ListComicsActivity extends AppCompatActivity {

    ListView lvtest;
    ArrayList<String> mList;
    ListViewContentAdapter mAdapter;
    public boolean checkLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comics);

        init();
        setWidgets();
        getWidgets();
        addListener();

    }

    private void init() {
//        Intent intent = getIntent();
//        mList = (ArrayList<Comic>)intent.getSerializableExtra("type");
        new LoadComicCondition(this, 2).execute();

    }

    private void getWidgets() {

    }

    private void setWidgets() {

    }

    private void addListener() {

    }





}
