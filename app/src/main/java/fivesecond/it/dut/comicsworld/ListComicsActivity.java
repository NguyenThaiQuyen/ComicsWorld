package fivesecond.it.dut.comicsworld;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fivesecond.it.dut.comicsworld.async.LoadComicContent;
import fivesecond.it.dut.comicsworld.async.MyAsyncTask;


public class ListComicsActivity extends AppCompatActivity {
//
    ListView lvtest;
    ArrayList<String> mList;
    ArrayAdapter<String> mAdapter;
    int number;
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
        //new MyAsyncTask(this, 1).execute();

        lvtest = findViewById(R.id.lvListComic);
        mList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mList);
        lvtest.setAdapter(mAdapter);
       number = 10;
        Toast.makeText(this, String.valueOf(number), Toast.LENGTH_SHORT).show();
//
//
        FirebaseStorage mStore = FirebaseStorage.getInstance();
        StorageReference storageRef = mStore.getReference();
//
        while(number>1) {
            //Toast.makeText(getApplicationContext(), String.valueOf(number), Toast.LENGTH_SHORT).show();
            storageRef.child("thumbs/" + String.valueOf(number) + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String url = uri.toString();
                    mList.add(url);
                    Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
                    mAdapter.notifyDataSetChanged();


                }
            });
                    //.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    Toast.makeText(getApplicationContext(), String.valueOf(number), Toast.LENGTH_SHORT).show();
//                    number = 0;
//                }
//            });;
        number--;
        }

        Toast.makeText(getApplicationContext(), String.valueOf(mList.size()), Toast.LENGTH_SHORT).show();

    }

    private void getWidgets() {

    }

    private void setWidgets() {

    }

    private void addListener() {

    }





}
