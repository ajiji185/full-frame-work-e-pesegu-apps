package com.example.e_pesegu_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class remind_listAdapter extends RecyclerView.Adapter<remind_listAdapter.myViewHolder>{

    private static ArrayList<DataSewaGuna> datalist;
    private static Context context;

    public remind_listAdapter(ArrayList<DataSewaGuna> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }
    public void filterlist(ArrayList<DataSewaGuna> filteredlist) {
        datalist=filteredlist;
        notifyDataSetChanged();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        private Button jabatan;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            jabatan=itemView.findViewById(R.id.button16);
            jabatan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String jabatan=datalist.get(getAdapterPosition()).getJabatan();
                    String email=datalist.get(getAdapterPosition()).getEmail();
                    String kontrak=datalist.get(getAdapterPosition()).getKontrak();
                    String key=datalist.get(getAdapterPosition()).getPrimaryKey();
                    String state=datalist.get(getAdapterPosition()).getState();
                    String namaKontrak=datalist.get(getAdapterPosition()).getNama_Kontrak();
                    String Tamat_Tempoh=datalist.get(getAdapterPosition()).getTamat_tempoh();
                    String pembekal=datalist.get(getAdapterPosition()).getPembekal();
                    // create an Intent
                    Intent updateIntent = new Intent(itemView.getContext(),send_email.class);

                    // add the data as extras to the Intent
                    updateIntent.putExtra("jabatan", jabatan);
                    updateIntent.putExtra("email", email);
                    updateIntent.putExtra("kontrak", kontrak);
                    updateIntent.putExtra("key",key);
                    updateIntent.putExtra("state",state);
                    updateIntent.putExtra("nama_kontrak",namaKontrak);
                    updateIntent.putExtra("tamat_tempoh",Tamat_Tempoh);
                    updateIntent.putExtra("pembekal",pembekal);




                    // start the update activity
                    itemView.getContext().startActivity(updateIntent);
                    ((Activity)context).finish();

                }
            });





        }
    }

    @NonNull
    @Override
    public remind_listAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.remind_list_adapter_view,parent,false);
        return new remind_listAdapter.myViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull remind_listAdapter.myViewHolder holder, int position) {
        String namajabatan=datalist.get(position).getJabatan();
        holder.jabatan.setText(namajabatan);

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
}
