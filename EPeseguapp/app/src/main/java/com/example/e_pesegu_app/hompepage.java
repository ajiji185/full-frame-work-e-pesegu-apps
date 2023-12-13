package com.example.e_pesegu_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class hompepage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button daftarPic;

    private Button changedatapic;
    private int backButtonPresses = 0;

    private Button remind;
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
        } else if (backButtonPresses == 2 ) {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_LONG).show();

        } else if (backButtonPresses == 3 ) {

            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hompepage);

        Button logout = findViewById(R.id.button3);
        mAuth = FirebaseAuth.getInstance();
        daftarPic=findViewById(R.id.button4);
        changedatapic=findViewById(R.id.button);
        remind=findViewById(R.id.button11);

        remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remind();

            }
        });





        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign out the user
                mAuth.signOut();
                loginscreen();
            }
        });

        daftarPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               pegidaftar();

            }
        });

        changedatapic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubahdata();

            }
        });


    }

    private void remind() {
        Intent intent=new Intent(this, remind_kontrak.class);
        startActivity(intent);
        finish();
    }

    private void ubahdata() {
        Intent intent=new Intent(this, pilih_jabatan.class);
        startActivity(intent);
        finish();
    }

    private void pegidaftar() {
        Intent intent=new Intent(this, daftarJabatanPic.class);
        startActivity(intent);
        finish();
    }

    private void loginscreen() {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}