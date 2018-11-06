package fivesecond.it.dut.comicsworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.adapters.ListComicsAdapter;
import fivesecond.it.dut.comicsworld.models.Comic;

public class ListComicsActivity extends AppCompatActivity {

    ListView lvListComic;
    ArrayList<Comic> mList;
    ListComicsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comics);
        lvListComic = findViewById(R.id.lvListComic);

        Intent intent = getIntent();
        mList = (ArrayList<Comic>)intent.getSerializableExtra("pass_list");


        lvListComic = findViewById(R.id.lvListComic);
        mAdapter = new ListComicsAdapter(this, R.layout.item_list_comics, mList);
        lvListComic.setAdapter(mAdapter);
    }
}
