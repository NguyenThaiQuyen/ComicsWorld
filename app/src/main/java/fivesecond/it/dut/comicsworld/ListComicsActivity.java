package fivesecond.it.dut.comicsworld;

<<<<<<< HEAD

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
=======
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fivesecond.it.dut.comicsworld.async.LoadComicCondition;
>>>>>>> 2852a0e9e2cd287349112e54d524d56dc81c46ad


public class ListComicsActivity extends AppCompatActivity {

    ListView lvtest;
    ArrayList<String> mList;
    ListViewContentAdapter mAdapter;
    public boolean checkLoading;

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
        new LoadComicCondition(this, 2).execute();

    }

    private void getWidgets() {

    }

    private void setWidgets() {

    }

    private void addListener() {

    }





}
