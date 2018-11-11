package fivesecond.it.dut.comicsworld;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.adapters.ListViewContentAdapter;


public class ListComicsActivity extends AppCompatActivity {

    ListView lvtest;
    ArrayList<String> mList;
    ListViewContentAdapter mAdapter;
    public boolean checkLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comics);

        lvtest = findViewById(R.id.lvListComic);
        mList = new ArrayList<>();
       mAdapter = new ListViewContentAdapter(this, R.layout.item_content_comic, mList);
       lvtest.setAdapter(mAdapter);
       checkLoading = true;
        Toast.makeText(getApplicationContext(), String.valueOf(4), Toast.LENGTH_SHORT).show();

        FirebaseStorage mStore = FirebaseStorage.getInstance();
        StorageReference storageRef = mStore.getReference();

       int i = 1;
        while (i<2)
        {       // Toast.makeText(getApplicationContext(), String.valueOf(4), Toast.LENGTH_SHORT).show();

           // final int j = i;
            storageRef.child("comics/1/1/" + String.valueOf(44) + ".jpg").getDownloadUrl().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                   // checkLoading = false;
                }

            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
//                    mList.add(uri.toString());
//                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), String.valueOf(44), Toast.LENGTH_SHORT).show();

                }
            });


            i++;
        }
        Toast.makeText(getApplicationContext(), "finish", Toast.LENGTH_SHORT).show();

        init();
        setWidgets();
        getWidgets();
        addListener();
    }

    private void init() {
//        Intent intent = getIntent();
//        mList = (ArrayList<Comic>)intent.getSerializableExtra("type");
        //new MyAsyncTask(this, 1).execute();

    }

    private void getWidgets() {

    }

    private void setWidgets() {

    }

    private void addListener() {

    }





}
