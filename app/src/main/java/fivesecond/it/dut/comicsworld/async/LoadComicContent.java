package fivesecond.it.dut.comicsworld.async;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.R;
import fivesecond.it.dut.comicsworld.adapters.ListViewAdapder;
import fivesecond.it.dut.comicsworld.models.Comic;

public class LoadComicContent extends AsyncTask<Void, String, Void> {
    Activity parContext;
    ListView lvtest;
    ArrayList<String> mList;
    ArrayAdapter<String> mAdapter;
    int number;

    public LoadComicContent(Activity ctx)
    {
        parContext = ctx;
        lvtest = parContext.findViewById(R.id.lvListComic);
        mList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(parContext, android.R.layout.simple_list_item_1, mList);
        lvtest.setAdapter(mAdapter);
        number = 1;

    }
    @Override
    protected Void doInBackground(Void... voids) {

        FirebaseStorage mStore = FirebaseStorage.getInstance();
        StorageReference storageRef = mStore.getReference();

        while(number > 0) {

            storageRef.child("thumbs/" + number + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String url = uri.toString();
                    publishProgress(url);
                    number++;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    number = 0;
                }
            });;
        }

        return null;
    }

    protected void onProgressUpdate(String... strings) {

        mList.add(strings[0]);

        mAdapter.notifyDataSetChanged();
    }
}
