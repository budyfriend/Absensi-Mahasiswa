package com.budyfriend_code.absensi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab_add;
    RecyclerAdapterAbsen recyclerAdapter;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<dataAbsensi> mahasiswaArrayList;
    RecyclerView recyclerView;
    Context context;
    LoadingProgress loadingProgress;
    EditText  tanggal;
    Button btn_tanggal;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-yyyy",new Locale("in","ID"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        tanggal = findViewById(R.id.tanggal);
        btn_tanggal = findViewById(R.id.btn_tanggal);
        recyclerView = findViewById(R.id.recyclerView);
        fab_add = findViewById(R.id.fab_add);

        tanggal.setText(simpleDateFormat.format(calendar.getTime()));

        btn_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        tanggal.setText(simpleDateFormat.format(calendar.getTime()));
                        showData(calendar.getTime().getTime());
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

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

        showData(calendar.getTime().getTime());

    }

    private void showData(final long time) {
        loadingProgress.show(getSupportFragmentManager(),"loading");
        database.child("absen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mahasiswaArrayList = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren()){
                    dataAbsensi abs = item.getValue(dataAbsensi.class);
                    if (abs!= null){
                        if (simpleDateFormat.format(abs.getTanggal()).equals(simpleDateFormat.format(time))){
                            abs.setKey(item.getKey());
                            mahasiswaArrayList.add(abs);
                        }

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