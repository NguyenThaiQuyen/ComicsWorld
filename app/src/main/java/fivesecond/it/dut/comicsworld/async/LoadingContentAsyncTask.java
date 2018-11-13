package fivesecond.it.dut.comicsworld.async;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.R;
import fivesecond.it.dut.comicsworld.ReadComic;
import fivesecond.it.dut.comicsworld.adapters.ListViewContentAdapter;

public class LoadingContentAsyncTask extends AsyncTask<Void, String, Void> {
    ReadComic parContext;
    String url;
    int chap;
    int i;


    boolean checkLoading;

    public LoadingContentAsyncTask(ReadComic ctx, String _url, int _chap)
    {
        parContext = ctx;
        url = _url;
        chap = _chap;

        checkLoading = true;

        i = 0;

    }
    @Override
    protected Void doInBackground(Void... voids) {

        FirebaseStorage mStore = FirebaseStorage.getInstance();
        StorageReference storageRef = mStore.getReference();

//        int i = 1;
//        while (checkLoading)
//        {
//            final int j = i;
//            storageRef.child("comics/" + url + "/" + String.valueOf(chap) + "/" + String.valueOf(j)+ ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    String url = uri.toString();
//                    publishProgress(url);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//
//                    checkLoading = false;
//                }
//
//            });
//            i++;
//        }
//        return null;


        while(checkLoading) {
            storageRef.child("comics/" + url + "/" + String.valueOf(chap) + "/" + String.valueOf(i) + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

        parContext.notify(strings[0]);
    }
}
