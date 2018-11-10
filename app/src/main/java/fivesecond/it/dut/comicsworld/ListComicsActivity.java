package fivesecond.it.dut.comicsworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fivesecond.it.dut.comicsworld.async.MyAsyncTask;


public class ListComicsActivity extends AppCompatActivity {


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
        new MyAsyncTask(this, 1).execute();
    }

    private void getWidgets() {

    }

    private void setWidgets() {

    }

    private void addListener() {

    }





}
