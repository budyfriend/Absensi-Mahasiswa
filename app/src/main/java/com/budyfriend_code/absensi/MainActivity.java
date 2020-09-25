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
    RecyclerAdapter recyclerAdapter;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<mahasiswa> mahasiswaArrayList;
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
                DialogForm dialogForm = new DialogForm("Tambah");
                dialogForm.show(getSupportFragmentManager(), "form");
            }
        });

        showData();

    }

    private void showData() {
        loadingProgress.show(getSupportFragmentManager(),"loading");
        database.child("test").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mahasiswaArrayList = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren()){
                    mahasiswa mhs = item.getValue(mahasiswa.class);
                    if (mhs!= null){
                        mhs.setKey(item.getKey());
                        mahasiswaArrayList.add(mhs);
                    }
                }
                recyclerAdapter = new RecyclerAdapter(mahasiswaArrayList,MainActivity.this);
                recyclerView.setAdapter(recyclerAdapter);
                loadingProgress.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}