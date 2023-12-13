package com.example.e_pesegu_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class remind_list extends AppCompatActivity {
    private ArrayList<DataSewaGuna> datalist = new ArrayList<>();
    private RecyclerView recyclerView;
    private remind_listAdapter adapter;
    private int backButtonPresses = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_list);
        recyclerView=findViewById(R.id.remind);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        String kontrak_05=getIntent().getStringExtra("kontrak_05");
        String kontrak_29=getIntent().getStringExtra("kontrak_29");
        String kontrak_41=getIntent().getStringExtra("kontrak_41");

        datalist=new ArrayList<>();
        if(kontrak_05!= null){
            retrieveDataFromFirebase(kontrak_05);
        }
        if(kontrak_29!= null){
            retrieveDataFromFirebase(kontrak_29);
        }
        if(kontrak_41!= null){
            retrieveDataFromFirebase(kontrak_41);
        }

        setadapter();

        EditText editText=findViewById(R.id.editTextText3);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());

            }
        });

    }

    private void filter(String text){
        ArrayList<DataSewaGuna>filteredlist=new ArrayList<>();
        for(DataSewaGuna item: datalist){
            if(item.getJabatan().toLowerCase().contains(text.toLowerCase())){
                filteredlist.add(item);
            }
        }
        adapter.filterlist(filteredlist);
    }

    private void setadapter() {
        adapter=new remind_listAdapter(datalist,this);
        recyclerView.setAdapter(adapter);
    }

    private void retrieveDataFromFirebase(String kontrak) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(kontrak);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String userId = userSnapshot.getKey();

                        DataSewaGuna itembk = userSnapshot.getValue(DataSewaGuna.class);
                        if (itembk != null && itembk.getState().equals("Not Reminded")) {

                            datalist.add(itembk);
                        }
                    }
                } else {
                    // No data found
                    return;
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any database error here
            }
        });
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