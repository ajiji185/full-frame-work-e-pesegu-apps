package com.example.e_pesegu_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class jabatan_viewAdapter  extends RecyclerView.Adapter<jabatan_viewAdapter.MyviewHolder>{

    private static ArrayList<DataSewaGuna> datalist;
    private static Context context;
    public jabatan_viewAdapter(ArrayList<DataSewaGuna> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }

    public void filterList(ArrayList<DataSewaGuna> filteredList) {
        datalist=filteredList;
        notifyDataSetChanged();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        private Button jabatan;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

           jabatan=itemView.findViewById(R.id.button9);

           jabatan.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   String jabatan=datalist.get(getAdapterPosition()).getJabatan();
                   String email=datalist.get(getAdapterPosition()).getEmail();
                   String kontrak=datalist.get(getAdapterPosition()).getKontrak();
                   String key=datalist.get(getAdapterPosition()).getPrimaryKey();
                   // create an Intent
                   Intent updateIntent = new Intent(itemView.getContext(),edit_data.class);

                   // add the data as extras to the Intent
                   updateIntent.putExtra("jabatan", jabatan);
                   updateIntent.putExtra("email", email);
                   updateIntent.putExtra("kontrak", kontrak);
                   updateIntent.putExtra("key",key);



                   // start the update activity
                   itemView.getContext().startActivity(updateIntent);
                   ((Activity)context).finish();

               }
           });

        }
    }


    @NonNull
    @Override
    public jabatan_viewAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.jabatan_view_list,parent,false);
        return new jabatan_viewAdapter.MyviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull jabatan_viewAdapter.MyviewHolder holder, int position) {

        String namajabatan=datalist.get(position).getJabatan();
        holder.jabatan.setText(namajabatan);

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }


}
