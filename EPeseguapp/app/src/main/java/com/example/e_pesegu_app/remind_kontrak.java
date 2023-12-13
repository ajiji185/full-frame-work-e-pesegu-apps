package com.example.e_pesegu_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class remind_kontrak extends AppCompatActivity {
    private Button kontrak_05;
    private Button kontrak_29;
    private Button kontrak_41;
    private int backButtonPresses = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_kontrak);

        kontrak_05=findViewById(R.id.button13);
        kontrak_29=findViewById(R.id.button14);
        kontrak_41=findViewById(R.id.button15);


        kontrak_05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kontrak_05="JPKN/2021/AKNS/01";
                pilihjabatan_05(kontrak_05);
            }
        });
        kontrak_29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kontrak_29="03 JPKN/2021/P41/01";
                pilihjabatan_29(kontrak_29);
            }
        });

        kontrak_41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kontrak_41="JPKN/2022/AKNS/01";
                pilihjabatan_41(kontrak_41);
            }
        });


    }


    private void pilihjabatan_41(String kontrak_41) {
        Intent intent=new Intent(this, remind_list.class);
        intent.putExtra("kontrak_41", kontrak_41);
        startActivity(intent);
        finish();
    }

    private void pilihjabatan_29(String kontrak_29) {
        Intent intent=new Intent(this, remind_list.class);
        intent.putExtra("kontrak_29", kontrak_29);
        startActivity(intent);
        finish();
    }

    private void pilihjabatan_05(String kontrak) {
        Intent intent=new Intent(this, remind_list.class);
        intent.putExtra("kontrak_05", kontrak);
        startActivity(intent);
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
            Intent homeIntent = new Intent(this, hompepage.class);
            startActivity(homeIntent);
            finish();

        }
        super.onBackPressed();
    }

}