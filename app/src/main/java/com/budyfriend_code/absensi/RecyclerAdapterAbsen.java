package com.budyfriend_code.absensi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerAdapterAbsen extends RecyclerView.Adapter<RecyclerAdapterAbsen.MyViewHolder> {
    private List<dataAbsensi> dataAbsensiList;
    AppCompatActivity context;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-yyyy",new Locale("in","ID"));

    public RecyclerAdapterAbsen(List<dataAbsensi> dataAbsensiList, AppCompatActivity context) {
        this.dataAbsensiList = dataAbsensiList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapterAbsen.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_absensi, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterAbsen.MyViewHolder holder, int position) {
        holder.viewBind(dataAbsensiList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataAbsensiList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nama;
        CheckBox cek_kehadiran;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            cek_kehadiran = itemView.findViewById(R.id.cek_kehadiran);
        }

        @SuppressLint("SetTextI18n")
        public void viewBind(final dataAbsensi dataAbsensi) {
            if (dataAbsensi.getKehadiaran().equals("Ya")){
                cek_kehadiran.setChecked(true);
            }else {
                cek_kehadiran.setChecked(false);
            }
            database.child("test").child(dataAbsensi.getKey_mahasiswa()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mahasiswa mhs = dataSnapshot.getValue(mahasiswa.class);
                    if (mhs!= null){
                        tv_nama.setText("Nama : " + mhs.getNama());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
