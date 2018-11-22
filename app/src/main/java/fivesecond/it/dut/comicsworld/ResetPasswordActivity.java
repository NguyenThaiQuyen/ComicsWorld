package fivesecond.it.dut.comicsworld;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edtEmail;
    Button btnReset, btnBack;
    protected FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        inits();
        sets();

        addListener();
    }

    private void inits() {
    }

    private void sets() {
        edtEmail = findViewById(R.id.edt_email);
        btnReset = findViewById(R.id.btnReset);
        btnBack = findViewById(R.id.btnBack);
    }

    private void addListener() {
        btnReset.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void gets() {

        email = edtEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReset: {
                gets();
                final ProgressDialog mProgressDialog = ProgressDialog.show(ResetPasswordActivity.this, "Please wait...", "Processing...", true);
                mFirebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }
                                mProgressDialog.dismiss();
                            }
                        });
                break;
            }
            case R.id.btnBack: {
                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                break;
            }
        }
    }
}

