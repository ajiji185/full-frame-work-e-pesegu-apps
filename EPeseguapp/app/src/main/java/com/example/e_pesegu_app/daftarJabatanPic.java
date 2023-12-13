package com.example.e_pesegu_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class daftarJabatanPic extends AppCompatActivity implements OnItemSelectedListener {
    private EditText emailPIC;
    private Spinner Namakontrak;
    private DatabaseReference databaseReference;
    private Spinner jabatan;
    private Button daftar;
    private int backButtonPresses = 0;

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
            Intent homeIntent = new Intent(this, hompepage.class);
            startActivity(homeIntent);
            finish();

        }
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_jabatan_pic);

        jabatan = findViewById(R.id.jabatan);
        ArrayAdapter<CharSequence> jabAdapter = ArrayAdapter.createFromResource(this, R.array.jabatan, android.R.layout.simple_spinner_dropdown_item);
        jabAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jabatan.setAdapter(jabAdapter);
        jabatan.setOnItemSelectedListener(this);

        emailPIC=findViewById(R.id.editTextTextEmailAddress);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Namakontrak=findViewById(R.id.kontrak);
        ArrayAdapter<CharSequence> konAdapter = ArrayAdapter.createFromResource(this, R.array.Kontrak, android.R.layout.simple_spinner_dropdown_item);
        konAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Namakontrak.setAdapter(konAdapter);
        Namakontrak.setOnItemSelectedListener(this);


        daftar=findViewById(R.id.button5);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                earlystageDaftar();

            }
        });

       // EmailSender.sendEmail("recipient@example.com", "Hello", "This is the email body.");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }
    private void earlystageDaftar(){
        // Retrieve the selected values
        String selectedJabatan = jabatan.getSelectedItem().toString().trim();
        String email = emailPIC.getText().toString();
        String selectedKontrak = Namakontrak.getSelectedItem().toString();
        String status="Not Reminded";
        String NamapanjangKontrak;
        String noKontrak;
        String tamatTempoh;
        String pembekal;


        if(email.isEmpty()){
            emailPIC.setError("Provide Email PIC");
            emailPIC.requestFocus();
            return;
        }

        if(selectedKontrak.equals("JPKN/2021/AKNS/01")){
            NamapanjangKontrak="Perkhidmatan Sewa Guna Mikrokomputer dan Komputer Riba Untuk Agensi Kerajaan Negeri Sabah Wilayah Kota Kinabalu - JPKN/2021/05";
            noKontrak="JPKN/2021/AKNS/01";
            tamatTempoh="03 Mac 2025";
            pembekal="BorneoSys Sdn Bhd";
            saveDataToFirebase(selectedJabatan, email, noKontrak,status,NamapanjangKontrak,tamatTempoh,pembekal);


        }
        if(selectedKontrak.equals("JPKN/2021/29")){
            NamapanjangKontrak="Perkhidmatan Sewa Guna Komputer Riba Untuk Pegawai Gred 41 Kerajaan Negeri Sabah - JPKN/2021/29";
            noKontrak="03 JPKN/2021/P41/01";
            tamatTempoh="30 Julai 2024";
            pembekal="Isro Trading";
            saveDataToFirebase(selectedJabatan, email, noKontrak,status,NamapanjangKontrak,tamatTempoh,pembekal);

        }
        if(selectedKontrak.equals("JPKN/2022/41")){
            NamapanjangKontrak="Senarai Agihan Komputer Desktop Pegawai Senarai Agihan Komputer Desktop Pegawai";
            noKontrak="JPKN/2022/AKNS/01";
            tamatTempoh="03 Disember 2025";
            pembekal="Fizrix System Sdn Bhd";
            saveDataToFirebase(selectedJabatan, email, noKontrak,status,NamapanjangKontrak,tamatTempoh,pembekal);

        }







    }

    private void saveDataToFirebase(String selectedJabatan, String email, String selectedKontrak, String status,String Namapanjang_kontrak, String tamat_tempo,String pembekal) {
        DatabaseReference dataRef = databaseReference.child(selectedKontrak);

        // Check if the selected jabatan already exists in the Firebase Database
        dataRef.orderByChild("jabatan").equalTo(selectedJabatan).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Jabatan already exists, show error message
                    Toast.makeText(daftarJabatanPic.this, "Jabatan already assigned", Toast.LENGTH_SHORT).show();
                } else {
                    // Jabatan is available, save the data to the Firebase Database
                    String key = databaseReference.child(selectedKontrak).push().getKey();
                    DataSewaGuna dataObject = new DataSewaGuna(selectedJabatan, email, selectedKontrak, status, key,Namapanjang_kontrak,tamat_tempo, pembekal);
                    databaseReference.child(selectedKontrak).child(key).setValue(dataObject);
                    Toast.makeText(daftarJabatanPic.this, "Berjaya diDaftar", Toast.LENGTH_SHORT).show();
                    balikhomepage();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any database error here
            }
        });
    }


    private void balikhomepage() {
        Intent homeIntent = new Intent(this, hompepage.class);
        startActivity(homeIntent);
        finish();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Handle case when no item is selected

    }
}
