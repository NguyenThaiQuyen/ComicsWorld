package fivesecond.it.dut.comicsworld;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.text.TextUtils.isEmpty;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int PICK_IMAGE_REQUEST = 71;

    private FirebaseAuth auth;
    private FirebaseUser user ;
    UserProfileChangeRequest profileUpdates;

    TextView txtUser;
    Button btnSave,btnChoose ,btnBack;
    EditText edtName;
    CircleImageView imgAvatar;

    String nameUpdate;
    Uri filePath = null;
    Bitmap bitmap;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        inits();
        setWidgets();
        getWidgets();
    }

    private void inits() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    private void setWidgets() {
        txtUser = findViewById(R.id.txtUser);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        btnChoose = findViewById(R.id.btnChoose);
        edtName = findViewById(R.id.edtUseName);
        imgAvatar = findViewById(R.id.imgAvatar);

    }

    private void getWidgets() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnChoose.setOnClickListener(this);


        if(user.getDisplayName() == null)
        {
            txtUser.setText("(username)");
        }else {
            String name = user.getDisplayName();
            txtUser.setText(name);
        }

        storageReference.child("images/" + user.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(imgAvatar);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }

        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack: {
                startActivity(new Intent(UserActivity.this, HomeScreenActivity.class));
                break;
            }
            case R.id.btnSave: {
                setProfile();
                break;
            }
            case R.id.btnChoose: {
                chooseImage();
                break;
            }
        }
    }

    private void setProfile()
    {
        nameUpdate = edtName.getText().toString();
        // update user
        if(filePath == null && isEmpty(nameUpdate) == true) {
            return ;

        }else if(filePath == null && isEmpty(nameUpdate) != true) {
            txtUser.setText(nameUpdate);
            profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nameUpdate)
                    .build();
        }else if(filePath != null && isEmpty(nameUpdate) == true) {
            uploadImage();
            profileUpdates = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(bitmap.toString()))
                    .build();
        }else{
            uploadImage();
            txtUser.setText(nameUpdate);
            profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nameUpdate)
                    .setPhotoUri(Uri.parse(bitmap.toString()))
                    .build();
        }

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserActivity.this, getResources().getString(R.string.update_profile), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_img)), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgAvatar.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(getResources().getString(R.string.uploading));
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+user.getUid());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(UserActivity.this, getString(R.string.uploaded), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UserActivity.this, getString(R.string.failed)+ e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage(getString(R.string.uploaded) +(int)progress+"%");
                        }
                    });
        }
    }
}

