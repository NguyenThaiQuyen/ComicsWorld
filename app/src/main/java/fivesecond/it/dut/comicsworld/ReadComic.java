package fivesecond.it.dut.comicsworld;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ReadComic extends AppCompatActivity {
    boolean checkLoading = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comic);

        init();
    }

    private void init() {
//        Intent intent = getIntent();
//        String url = intent.getStringExtra("url");
//        int chap = intent.getIntExtra("chap", 1);
        //new LoadingContentAsyncTask(this, url, chap).execute();

        String url = "2";
        int chap = 1;
        int j = 1;
        FirebaseStorage mStore = FirebaseStorage.getInstance();
        StorageReference storageRef = mStore.getReference();

        storageRef.child("comics/"+2+"/"+1+"/"+1+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
            }

        });
    }

}
