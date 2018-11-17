package fivesecond.it.dut.comicsworld;


import android.net.Uri;

import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class AddUrlActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_url);

        addUrl();
    }

    private void addUrl() {

        FirebaseDatabase mData = FirebaseDatabase.getInstance();
        final DatabaseReference dataRef = mData.getReference();

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();



        final String comic = "4";
        final String chap = "2";
        final int max = 11;

        for(int i = 1; i <= max; i++)
        {
            final int k = i;
            storageReference.child("comics/" + comic + "/" + chap + "/" + String.valueOf(i) + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String url = uri.toString();
                    Toast.makeText(AddUrlActivity.this, url, Toast.LENGTH_SHORT).show();

                    dataRef.child("urlComic").child(comic).child(chap).child(String.valueOf(k)).setValue(url);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {


                }

            });
        }

    }
}
