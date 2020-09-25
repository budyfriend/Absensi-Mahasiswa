package com.budyfriend_code.absensi;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DialogAbsensi extends DialogFragment {
    RecyclerView recyclerView;
    Context context;
    Button btn_simpan;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    AdapterAbsensiCek adapterAbsensiCek;
    ArrayList<mahasiswa> mahasiswaArrayList = new ArrayList<>();
    ArrayList<dataAbsensi> absensiArrayList = new ArrayList<>();
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.form_absensi, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        btn_simpan = v.findViewById(R.id.btn_simpan);
        dialog = getDialog();

        showData();
        return v;
    }

    private void showData() {
        database.child("test").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mahasiswaArrayList.clear();
                absensiArrayList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    mahasiswa mhs = item.getValue(mahasiswa.class);
                    if (mhs != null) {
                        mhs.setKey(item.getKey());
                        mahasiswaArrayList.add(mhs);
                        absensiArrayList.add(new dataAbsensi(item.getKey(),System.currentTimeMillis(),"Tidak"));
                    }
                }
                adapterAbsensiCek = new AdapterAbsensiCek(context, mahasiswaArrayList,absensiArrayList, btn_simpan,dialog);
                recyclerView.setAdapter(adapterAbsensiCek);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
