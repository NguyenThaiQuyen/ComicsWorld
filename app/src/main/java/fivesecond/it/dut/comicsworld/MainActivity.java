package fivesecond.it.dut.comicsworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import fivesecond.it.dut.comicsworld.async.LoadSlide;
import fivesecond.it.dut.comicsworld.async.LoadType;
import fivesecond.it.dut.comicsworld.async.LoadingSearchComic;


public class MainActivity extends AppCompatActivity {

    Button btnStart;
    Button btnContinue;
    private SharedPreferences sharedPreferences;
    private static final String myref = "currentComic";
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
        sharedPreferences = getSharedPreferences(myref, Context.MODE_PRIVATE);
    }

    private void setWidgets() {
        btnStart = findViewById(R.id.btnStart);
        btnContinue = findViewById(R.id.btnContinue);

    }

    private void getWidgets() {

    }


    private void addListenners() {

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();

                Intent intent = new Intent(MainActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });

        if(sharedPreferences.contains("urlComic"))
        {
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext() ,ReadComic.class);
                    intent.putExtra("url", sharedPreferences.getString("urlComic", "1"));
                    intent.putExtra("chap", sharedPreferences.getInt("chap", 1));
                    intent.putExtra("totalChap", sharedPreferences.getInt("totalChap", 3));

                    startActivity(intent);
                }
            });
        }
        else
        {
            btnContinue.setVisibility(View.GONE);
        }
    }






}
