package com.example.e_pesegu_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private Switch passw;
    private FirebaseAuth mAuth;
    private int backButtonPresses = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.editTextText);
        pass = findViewById(R.id.editTextTextPassword);
        Button login = findViewById(R.id.button2);
        passw = findViewById(R.id.switch1);
        passw.setText("Lihat kata Laluan");
        mAuth = FirebaseAuth.getInstance();

        // Set the default value of passw to true (checked)


        // Set an OnCheckedChangeListener for the passw Switch
        passw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Show or hide the password based on the state of the Switch
                if (isChecked) {
                    // If Switch is checked, show password as plain text
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passw.setText("Sembunyi Kata laluan");
                } else {
                    // If Switch is unchecked, hide password
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passw.setText("Lihat kata Laluan");
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPass = pass.getText().toString();
                if (userEmail.isEmpty()) {
                    email.setError("Provide Email");
                    email.requestFocus();
                    return;
                }
                if (userPass.isEmpty()) {
                    pass.setError("Provide Password");
                    pass.requestFocus();
                    return;
                }

                loginUser(userEmail, userPass);
            }
        });

        checkIfUserIsLoggedIn();
    }

    private void checkIfUserIsLoggedIn() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            gotohomepage();
        }
    }

    private void loginUser(String userEmail, String userPass) {
        mAuth.signInWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login successful
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            gotohomepage();
                        } else {
                            // Login failed
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void gotohomepage() {
        Intent intent = new Intent(this, hompepage.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        backButtonPresses++;
        handleBackButtonPress();
    }
    private void handleBackButtonPress() {
        if (backButtonPresses == 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit E-Pesegu");
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .show();
        } else if (backButtonPresses == 2) {
            finish();
        } else {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_LONG).show();
        }
    }

}