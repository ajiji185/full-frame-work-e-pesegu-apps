package com.example.e_pesegu_app;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Html;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class send_email extends AppCompatActivity {
    private TextView Namapejabat;
    private TextView emailPIC;
    private TextView state;
    private Button hantar;
    private int backButtonPresses = 0;
    private static final int REQUEST_PDF_PICKER = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        Namapejabat=findViewById(R.id.textView10);
        emailPIC=findViewById(R.id.textView12);
        state=findViewById(R.id.textView14);
        hantar=findViewById(R.id.button17);
        String newKontrak=getIntent().getStringExtra("jabatan");
        String oldEmail=getIntent().getStringExtra("email");
        String status=getIntent().getStringExtra("state");

        
        Namapejabat.setText(newKontrak);
        emailPIC.setText(oldEmail);
        state.setText(status);


        hantar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                selectFile();



            }
        });

    }

    private void TukarState(String key,String namakontrak){
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child(namakontrak).child(key).child("state");

        dataRef.setValue("Reminded").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(send_email.this, "PIC have been Reminded", Toast.LENGTH_SHORT).show();

            }
        });

    }



    private void sendMail(String jab, String kontrak, String email, String nama_full_kontrak, String tamat_tempoh, String pembekal, Uri filePath) {
        String subject = "Notis Peringatan Tamat Tempoh Sewa Guna - " + nama_full_kontrak;

        String message = "Sukacita dimaklumkan bahawa " + nama_full_kontrak + ", " + kontrak + " akan tamat tempoh pada " + tamat_tempoh + ". Sehubungan dengan itu, mohon agar pihak tuan/puan menghubungi penama seperti dalam Lampiran A dan memastikan penama melaksanakan sokongan (back up) semua data sebelum mikrokomputer/komputer riba dikembalikan kepada pihak pembekal - " + pembekal + ".\n\n" +
                "Kerjasama dari semua pihak amatlah dihargai.\n\n" +
                "Sekian dan terima kasih.\n\n" +
                "'MALAYSIA MADANI'\n" +
                "'BERKHIDMAT UNTUK NEGARA'\n" +
                "'Profesionalism Teras Keunggulan'\n\n" +
                "Saya yang menjalankan amanah,\n\n" +
                "Ketua Penolong Pengarah\n" +
                "Bahagian Transformasi Digital\n" +
                "Jabatan Perkhidmatan Komputer Negeri";

        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, email, subject, message, filePath);
        javaMailAPI.execute();

        String Key = getIntent().getStringExtra("key");
        String namaKontrak = getIntent().getStringExtra("kontrak");
        TukarState(Key, namaKontrak);
    }


    private void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf"); // Only allow PDF files
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select PDF File"), REQUEST_PDF_PICKER);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "File picker not available", Toast.LENGTH_SHORT).show();
        }
    }





    private String getFileNameFromUri(Uri uri) {
        String fileName = "";
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (index != -1) {
                fileName = cursor.getString(index);
            }
            cursor.close();
        }
        return fileName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PDF_PICKER && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                // Get the selected file URI
                Uri fileUri = data.getData();

                // Now you can directly pass the Uri to the sendMail method
                String newjab = getIntent().getStringExtra("jabatan");
                String oldEmail = getIntent().getStringExtra("email");
                String Key = getIntent().getStringExtra("key");
                String nama_full_kontrak = getIntent().getStringExtra("nama_kontrak");
                String tamat_tempoh = getIntent().getStringExtra("tamat_tempoh");
                String pembekal = getIntent().getStringExtra("pembekal");
                String namaKontrak = getIntent().getStringExtra("kontrak");
                sendMail(newjab, namaKontrak, oldEmail, nama_full_kontrak, tamat_tempoh, pembekal, fileUri);

                // You can also display the file name or other details to the user
                String fileName = getFileNameFromUri(fileUri);
                Toast.makeText(this, "Selected file: " + fileName, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "fail to send reminder", Toast.LENGTH_SHORT).show();
        }
    }





    private void backhome(){
        Intent homeIntent = new Intent(this, hompepage.class);
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
            Intent homeIntent = new Intent(this, remind_kontrak.class);
            startActivity(homeIntent);
            finish();

        }
        super.onBackPressed();
    }

}