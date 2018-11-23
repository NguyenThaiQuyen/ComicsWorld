package fivesecond.it.dut.comicsworld;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    protected FirebaseAuth mAuth = FirebaseAuth.getInstance();
    EditText edtEmail, edtPass, edtConfirm;
    Button btnRegister, btnForgot, btnLogin;
    String email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inits();
        sets();
        addListener();

    }

    private void inits() {
    }

    private void sets() {
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

    private void gets() {

        email = edtEmail.getText().toString().trim();
        password = edtPass.getText().toString().trim();
        confirmPassword = edtConfirm.getText().toString().trim();


        if (isEmpty(email)) {
            edtEmail.setError("Email is requried !");
            edtEmail.requestFocus();
            return;
        }

        if (isEmpty(password)) {
            edtPass.setError("Password is requried !");
            edtPass.requestFocus();
            return;
        }

        if (isEmpty(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Enter Confirm password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!confirmPassword.equals(password)) {
            Toast.makeText(getApplicationContext(), "Enter password is same above", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister: {
                gets();
                final ProgressDialog mProgressDialog = ProgressDialog.show(RegisterActivity.this, "Please wait...", "Processing...", true);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mProgressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Register successfull!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                }else{
                                    //Log.e("ERROR", task.getException().toString());
                                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                                    Toast.makeText(RegisterActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
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



