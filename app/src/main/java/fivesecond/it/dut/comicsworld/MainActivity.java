package fivesecond.it.dut.comicsworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.controllers.ComicController;
import fivesecond.it.dut.comicsworld.controllers.TypeController;
import fivesecond.it.dut.comicsworld.models.Comic;
import fivesecond.it.dut.comicsworld.models.Type;


public class MainActivity extends AppCompatActivity {

    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inits();
        setWidgets();
        getWidgets();
        addListenners();
    }

    private void inits() {

    }

    private void setWidgets() {
        btnStart = findViewById(R.id.btnStart);

    }

    private void getWidgets() {

    }

    private void addListenners() {

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });
    }






}
