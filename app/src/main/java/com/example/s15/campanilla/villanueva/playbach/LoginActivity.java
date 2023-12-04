package com.example.s15.campanilla.villanueva.playbach;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    Button signUpButton;

    EditText editTextEmail;
    EditText editTextPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginButton = findViewById(R.id.buttonLogin);
        signUpButton = findViewById(R.id.buttonSignUp);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        mAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;

                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Login success.",
                                            Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Login failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
