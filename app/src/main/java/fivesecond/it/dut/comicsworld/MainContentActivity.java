package fivesecond.it.dut.comicsworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.models.Comic;

public class MainContentActivity extends AppCompatActivity {
    ListView lvChap ;
    TextView txtNameComic;
    TextView txtAuthor;
    TextView txtChap;
    TextView txtMain;
    ImageView imgCover;
    RatingBar raBar;
    Button btnRead ;
    ArrayAdapter<String> adapterChap ;
    ArrayList<String> chap;
    Comic comic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        init();
        setWidgets();
        getWidgets();
        addListener();



    }

    private void init() {
        Intent intent = getIntent();
        comic = (Comic)intent.getSerializableExtra("comic");
        chap = new ArrayList<>();
        for(int i = 1; i <= comic.getChap(); i++)
        {
            chap.add("Chapter " + String.valueOf(i));
        }

        adapterChap  = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1,chap);
    }


    private void setWidgets() {
        lvChap=findViewById(R.id.lvChap);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtNameComic = findViewById(R.id.txtNameComic);
        txtMain = findViewById(R.id.txt_main);
        txtChap = findViewById(R.id.txt_chap);
        raBar = findViewById(R.id.raBar);
        imgCover = findViewById(R.id.imvCover);
        btnRead = findViewById(R.id.btnRead);
    }

    private void getWidgets() {
        txtChap.setText("Chapter (" + comic.getChap() + ")");
        txtNameComic.setText(comic.getName());
        txtAuthor.setText(comic.getAuthor());
        txtMain.setText(comic.getDesc());
        raBar.setRating(comic.getRating());
        lvChap.setAdapter(adapterChap);
        Picasso.get().load(comic.getThumb()).into(imgCover);
    }

    private void addListener() {
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainContentActivity.this ,ReadComic.class);
                intent.putExtra("url", comic.getUrl());
                intent.putExtra("chap", 1);
                startActivity(intent);
            }
        });

        lvChap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainContentActivity.this ,ReadComic.class);
                intent.putExtra("url", comic.getUrl());
                intent.putExtra("chap", position + 1);
                startActivity(intent);
            }
        });
    }



}
