package fivesecond.it.dut.comicsworld;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    protected FirebaseAuth auth;
    protected FirebaseUser user ;
    UserProfileChangeRequest profileUpdates;
    Uri filePath = null;

    FirebaseStorage storage;
    StorageReference storageReference;

    EditText edtEmail, edtPass, edtConfirm;
    Button btnRegister, btnForgot, btnLogin;
    String email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inits();
        setWidgets();
        getWidgets();
        addListener();

    }

    private void inits() {
        auth = FirebaseAuth.getInstance();
    }

    private void setWidgets() {
        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_pass);
        edtConfirm = findViewById(R.id.edtConfirm);
        btnRegister = findViewById(R.id.btnRegister);
        btnForgot = findViewById(R.id.btnForgot);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void addListener() {
        btnRegister.setOnClickListener(this);
        btnForgot.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

    }

    private void getWidgets() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        email = edtEmail.getText().toString().trim();
        password = edtPass.getText().toString().trim();
        confirmPassword = edtConfirm.getText().toString().trim();


        if (isEmpty(email)) {
            edtEmail.setError(getResources().getString(R.string.email_requried));
            edtEmail.requestFocus();
            return;
        }

        if (isEmpty(password)) {
            edtPass.setError(getResources().getString(R.string.password_requried));
            edtPass.requestFocus();
            return;
        }

        if (isEmpty(confirmPassword)) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.password_confirm_requried), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!confirmPassword.equals(password)) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.password_same_requried), Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.input_again_pass), Toast.LENGTH_SHORT).show();
            return;
        }
    }


    // get image from resource
    public static final Uri getUriToResource(@NonNull Context context,
                                             @AnyRes int resId) throws Resources.NotFoundException {
        Resources res = context.getResources();
        Uri resUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId));
        return resUri;
    }

    private void setProfile(){
        int index = email.trim().indexOf('@');
        String name = email.trim().substring(0,index);

        uploadImage();
        profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.update_profile), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void uploadImage() {
        filePath = getUriToResource(RegisterActivity.this,R.drawable.avatar);

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(getResources().getString(R.string.uploading));
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ user.getUid());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, getString(R.string.uploaded), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, getString(R.string.failed)+ e.getMessage(), Toast.LENGTH_SHORT).show();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister: {
                getWidgets();
                final ProgressDialog mProgressDialog = ProgressDialog.show(RegisterActivity.this, getResources().getString(R.string.wait), getResources().getString(R.string.processing), true);
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mProgressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, getResources().getString(R.string.register), Toast.LENGTH_SHORT).show();
                                    user = auth.getCurrentUser();
                                    setProfile();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                }else{
                                    //Log.e("ERROR", task.getException().toString());
                                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                                    Toast.makeText(RegisterActivity.this, getResources().getString(R.string.register_faile) + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(RegisterActivity.this, "Register failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
            }
            case R.id.btnForgot:{
                startActivity(new Intent(RegisterActivity.this, ResetPasswordActivity.class));
                break;
            }
            case R.id.btnLogin:{
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;
            }
        }
    }
}




