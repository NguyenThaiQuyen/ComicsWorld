package fivesecond.it.dut.comicsworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.controllers.ComicController;
import fivesecond.it.dut.comicsworld.controllers.TypeController;
import fivesecond.it.dut.comicsworld.models.Comic;
import fivesecond.it.dut.comicsworld.models.Type;


public class MainActivity extends AppCompatActivity {

    ListView lvType;
    ArrayAdapter<String> mAdapter;
    String mList[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, ListComicsActivity.class);

        TypeController.getInstance().load();
        ComicController.getInstance().load();

        lvType = findViewById(R.id.lvType);
        mList = TypeController.getInstance().getTypeName();

        mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                mList);
        lvType.setAdapter(mAdapter);

        lvType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),
                        "Selected: " + mList[i], Toast.LENGTH_SHORT).show();

                Type type = TypeController.getInstance().getTypeList().get(i);
                ArrayList<Comic> list = ComicController.getInstance().getComicList(type);

                Intent intent = new Intent(MainActivity.this, ListComicsActivity.class);
                intent.putExtra("pass_list", list);

                startActivity(intent);
            }
        });

    }
}
