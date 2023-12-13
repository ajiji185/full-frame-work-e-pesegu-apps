package com.example.e_pesegu_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class edit_data extends AppCompatActivity {
    private Spinner jabatan;
    private EditText email;
    private Spinner kontrak;
    private Button kemaskini;
    private DatabaseReference databaseReference;

    private Button padam;

    private int backButtonPresses = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        jabatan=findViewById(R.id.spinner);
        email=findViewById(R.id.editTextText2);
        kontrak=findViewById(R.id.spinner2);

        kemaskini=findViewById(R.id.button10);
        padam=findViewById(R.id.button12);





        String Jabatan = getIntent().getStringExtra("jabatan");
        List<String> Listjabatan = new ArrayList<>();
        Listjabatan.add(Jabatan);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(edit_data.this, android.R.layout.simple_spinner_dropdown_item, Listjabatan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jabatan.setAdapter(adapter);
        jabatan.setSelection(adapter.getPosition(Jabatan));


        String ObtainEmail = getIntent().getStringExtra("email");
        email.setText(ObtainEmail);

        String Kontrak = getIntent().getStringExtra("kontrak");
        List<String> ListKontrak = new ArrayList<>();
        ListKontrak.add(Kontrak);
        ArrayAdapter<String> kont = new ArrayAdapter<>(edit_data.this, android.R.layout.simple_spinner_dropdown_item, ListKontrak);
        kont.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kontrak.setAdapter(kont);
        kontrak.setSelection(kont.getPosition(Kontrak));


        kemaskini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newKontrak=getIntent().getStringExtra("kontrak");
                String oldEmail=getIntent().getStringExtra("email");
                String key=getIntent().getStringExtra("key");
                String newEmail =  email.getText().toString().trim();
                if(newEmail.isEmpty()){
                    email.setError("please Enter new Email");
                    email.requestFocus();
                    return;
                }

                updateDataInFirebase(newKontrak, key, newEmail);

            }
        });

        padam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newKontrak=getIntent().getStringExtra("kontrak");
                String oldEmail=getIntent().getStringExtra("email");
                String key=getIntent().getStringExtra("key");

                AlertDialog.Builder builder = new AlertDialog.Builder(edit_data.this);
                builder.setTitle("E-Pesegu : Hapus Data");
                builder.setMessage("Anda Pasti Ingin Hapuskan data?")
                        .setCancelable(true)
                        .setPositiveButton("Betul", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deletedata(newKontrak,key);
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });



    }

    private void deletedata(String selectedKontrak, String key) {
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child(selectedKontrak).child(key);

        // Remove all data under the specified key
        dataRef.removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Deletion successful
                            Toast.makeText(edit_data.this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                            backtoppilihjabatan();
                        } else {
                            // Failed to delete data
                            Toast.makeText(edit_data.this, "Failed to delete data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void updateDataInFirebase(String selectedKontrak, String key, String email) {
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child(selectedKontrak).child(key).child("email");
        DatabaseReference sate=FirebaseDatabase.getInstance().getReference().child(selectedKontrak).child(key).child("state");

        // Update the email value in the Firebase Database
        dataRef.setValue(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Email updated successfully
                            Toast.makeText(edit_data.this, "Email updated successfully", Toast.LENGTH_SHORT).show();
                            backtoppilihjabatan();
                        } else {
                            // Failed to update email
                            Toast.makeText(edit_data.this, "Failed to update email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // Update the state value in the Firebase Database

        sate.setValue("Not Reminded");
    }



    private void backtoppilihjabatan() {
        Intent homeIntent = new Intent(this, pilih_jabatan.class);
        startActivity(homeIntent);
        finish();
    }


    //back Button
    @Override
    public void onBackPressed() {
        backButtonPresses++;
        handleBackButtonPress();
        super.onBackPressed();
    }
    private void handleBackButtonPress() {
        if (backButtonPresses == 1) {
            // redirect to home page
            Intent homeIntent = new Intent(this, pilih_jabatan.class);
            startActivity(homeIntent);
            finish();

        }
        super.onBackPressed();
    }
}