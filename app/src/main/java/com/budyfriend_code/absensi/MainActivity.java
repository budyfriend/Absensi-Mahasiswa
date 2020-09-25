package com.budyfriend_code.absensi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab_add;
    RecyclerAdapterAbsen recyclerAdapter;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<dataAbsensi> mahasiswaArrayList;
    RecyclerView recyclerView;
    Context context;
    LoadingProgress loadingProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        recyclerView = findViewById(R.id.recyclerView);
        fab_add = findViewById(R.id.fab_add);

        loadingProgress = new LoadingProgress();
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogForm dialogForm = new DialogForm("Tambah");
//                dialogForm.show(getSupportFragmentManager(), "form");
                DialogAbsensi absensi = new DialogAbsensi();
                absensi.show(getSupportFragmentManager(),"absensi");
            }
        });

        showData();

    }

    private void showData() {
        loadingProgress.show(getSupportFragmentManager(),"loading");
        database.child("absen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mahasiswaArrayList = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren()){
                    dataAbsensi abs = item.getValue(dataAbsensi.class);
                    if (abs!= null){
                        abs.setKey(item.getKey());
                        mahasiswaArrayList.add(abs);
                    }
                }
                recyclerAdapter = new RecyclerAdapterAbsen(mahasiswaArrayList,MainActivity.this);
                recyclerView.setAdapter(recyclerAdapter);
                loadingProgress.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}