package fivesecond.it.dut.comicsworld;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    Button btnStart, btnChange, btnContinue;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String myref = "currentComic";
    private String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(myref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        language = sharedPreferences.getString("KEY_LANGUAGE", "en");
        setLanguage(language);

        setContentView(R.layout.activity_main);

        inits();
        getWidgets();
        setWidgets();
        addListenners();
    }

    private void inits() {

    }


    private void getWidgets() {
        btnStart = findViewById(R.id.btnStart);
        btnContinue = findViewById(R.id.btnContinue);
        btnChange = findViewById(R.id.btnChange);
    }

    private void setWidgets() {


    }


    private void addListenners() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear().apply();
                Intent intent = new Intent(MainActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectLanguage();
            }
        });
        if (sharedPreferences.contains("urlComic")) {
            btnContinue.setVisibility(View.VISIBLE);
           btnContinue.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(getApplicationContext(), ReadComic.class);
                   intent.putExtra("url", sharedPreferences.getString("urlComic", "1"));
                   intent.putExtra("chap", sharedPreferences.getInt("chap", 1));
                   intent.putExtra("totalChap", sharedPreferences.getInt("totalChap", 3));

                   startActivity(intent);
               }
           });
        } else
        {
            btnContinue.setVisibility(View.GONE);
        }
    }

    public void showSelectLanguage() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_dialog_main)
                .setItems(R.array.languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 1: //VI
                                language = "vi";
                                break;
                        }
                        setLanguage(language);

                        //save
                        editor.putString("KEY_LANGUAGE", language);
                        editor.apply();

                        //refresh UI
                        setContentView(R.layout.activity_main);
                        inits();
                        getWidgets();
                        setWidgets();
                        addListenners();
                    }
                }).create().show();
    }

    public void setLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration con = resources.getConfiguration();
        con.locale = locale;
        resources.updateConfiguration(con, resources.getDisplayMetrics());
    }
}

