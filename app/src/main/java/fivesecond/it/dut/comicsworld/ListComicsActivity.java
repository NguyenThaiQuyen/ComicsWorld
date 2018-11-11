package fivesecond.it.dut.comicsworld;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fivesecond.it.dut.comicsworld.async.LoadComicContent;
import fivesecond.it.dut.comicsworld.async.MyAsyncTask;


public class ListComicsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comics);
new MyAsyncTask(this, 1).execute();

        //init();
        setWidgets();
        getWidgets();
        addListener();
    }

    private void init() {
//        Intent intent = getIntent();
//        mList = (ArrayList<Comic>)intent.getSerializableExtra("type");
        //new MyAsyncTask(this, 1).execute();


        FirebaseStorage mStore = FirebaseStorage.getInstance();
        StorageReference storageRef = mStore.getReference();




    }

    private void getWidgets() {

    }

    private void setWidgets() {

    }

    private void addListener() {

    }





}
