package com.budyfriend_code.absensi;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterAbsensiCek extends RecyclerView.Adapter<AdapterAbsensiCek.CekViewHolder> {
    Context context;
    ArrayList<mahasiswa> mahasiswaArrayList;
    ArrayList<dataAbsensi> absensiArrayList;
    Button btn_simpan;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    Dialog dialog;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-yyyy",new Locale("in","ID"));

    public AdapterAbsensiCek(Context context, ArrayList<mahasiswa> mahasiswaArrayList, ArrayList<dataAbsensi> absensiArrayList, Button btn_simpan, Dialog dialog) {
        this.context = context;
        this.mahasiswaArrayList = mahasiswaArrayList;
        this.absensiArrayList = absensiArrayList;
        this.btn_simpan = btn_simpan;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public CekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_absensi, parent, false);
        return new CekViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CekViewHolder holder, final int position) {
        holder.viewBind(mahasiswaArrayList.get(position));
        dataAbsensi abs = absensiArrayList.get(position);
        holder.cek_kehadiran.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    absensiArrayList.set(position, new dataAbsensi(
                            absensiArrayList.get(position).getKey_mahasiswa(),
                            absensiArrayList.get(position).getTanggal(),
                            "Ya"
                    ));
                } else {
                    absensiArrayList.set(position, new dataAbsensi(
                            absensiArrayList.get(position).getKey_mahasiswa(),
                            absensiArrayList.get(position).getTanggal(),
                            "Tidak"
                    ));
                }
            }
        });

        if (abs.getKehadiaran().equals("Ya")) {
            holder.cek_kehadiran.setChecked(true);
        } else if (abs.getKehadiaran().equals("Tidak")) {
            holder.cek_kehadiran.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return mahasiswaArrayList.size();
    }

    public class CekViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nama;
        CheckBox cek_kehadiran;

        public CekViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            cek_kehadiran = itemView.findViewById(R.id.cek_kehadiran);
            cek_kehadiran.setEnabled(true);
        }

        @SuppressLint("SetTextI18n")
        public void viewBind(final mahasiswa mahasiswa) {
            tv_nama.setText("Nama : " + mahasiswa.getNama());
            btn_simpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < absensiArrayList.size(); i++) {
                        database.child("absen").child(absensiArrayList.get(i).getKey_mahasiswa()+"-"+simpleDateFormat.format(absensiArrayList.get(i).getTanggal())).setValue(new dataAbsensi(
                                absensiArrayList.get(i).getKey_mahasiswa(),
                                absensiArrayList.get(i).getTanggal(),
                                absensiArrayList.get(i).getKehadiaran()
                        ));
                        if (i == absensiArrayList.size() - 1) {
                            Toast.makeText(context, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                }
            });
        }
    }
}
