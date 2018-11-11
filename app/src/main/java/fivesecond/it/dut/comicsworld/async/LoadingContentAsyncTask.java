package fivesecond.it.dut.comicsworld.async;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.R;
import fivesecond.it.dut.comicsworld.adapters.ListViewContentAdapter;

public class LoadingContentAsyncTask extends AsyncTask<Void, String, Void> {
    Activity parContext;
    ListView lvtest;
    ArrayList<String> mList;
    ListViewContentAdapter mAdapter;
    int id;
    boolean checkLoading;

    public LoadingContentAsyncTask(Activity ctx, int idComic)
    {
        parContext = ctx;
        lvtest = parContext.findViewById(R.id.lvListComic);
        mList = new ArrayList<>();
        mAdapter = new ListViewContentAdapter(parContext, R.layout.item_content_comic, mList);
        lvtest.setAdapter(mAdapter);
        id = idComic;
        checkLoading = true;

    }
    @Override
    protected Void doInBackground(Void... voids) {

        FirebaseStorage mStore = FirebaseStorage.getInstance();
        StorageReference storageRef = mStore.getReference();

        int i = 1;
        while (checkLoading)
        {
            final int j = i;
            storageRef.child("comics/1/1/" + String.valueOf(j) + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String url = uri.toString();
                    publishProgress(url);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                    checkLoading = false;
                }

            });
            i++;
        }
        return null;
    }

    protected void onProgressUpdate(String... strings) {

        mList.add(strings[0]);

        mAdapter.notifyDataSetChanged();
    }
}
