package fivesecond.it.dut.comicsworld;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    protected FirebaseAuth auth;
    protected FirebaseUser user ;

    EditText edtEmail, edtPass;
    Button btnRegister, btnForgot, btnLogin;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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


        email = edtEmail.getText().toString().trim();
        password = edtPass.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.input_email), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.input_password), Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.input_again_pass), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser currentUser) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin: {
                getWidgets();
                final ProgressDialog mProgressDialog = ProgressDialog.show(LoginActivity.this, getResources().getString(R.string.wait), getResources().getString(R.string.processing), true);
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mProgressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_successfully), Toast.LENGTH_SHORT).show();
                                    user = auth.getCurrentUser();
                                    updateUI(user);
                                    onBackPressed();
                                    //startActivity(new Intent(LoginActivity.this, HomeScreenActivity.class));
                                } else {
                                    Log.e("ERROR", task.getException().toString());
                                    Toast.makeText(LoginActivity.this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
                break;
            }
            case R.id.btnForgot:{
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                break;
            }
            case R.id.btnRegister:{
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            }
        }
    }
}


