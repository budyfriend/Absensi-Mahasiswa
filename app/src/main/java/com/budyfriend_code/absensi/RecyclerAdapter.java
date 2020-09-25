package com.budyfriend_code.absensi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<mahasiswa> mahasiswaList;
    AppCompatActivity context;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public RecyclerAdapter(List<mahasiswa> mahasiswaList, AppCompatActivity context) {
        this.mahasiswaList = mahasiswaList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        holder.viewBind(mahasiswaList.get(position));
    }

    @Override
    public int getItemCount() {
        return mahasiswaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nama,
                tv_fakultas,
                tv_jurusan,
                tv_semester;
        ImageView hapus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_fakultas = itemView.findViewById(R.id.tv_fakultas);
            tv_jurusan = itemView.findViewById(R.id.tv_jurusan);
            tv_semester = itemView.findViewById(R.id.tv_semester);
            hapus = itemView.findViewById(R.id.hapus);


        }

        @SuppressLint("SetTextI18n")
        public void viewBind(final mahasiswa mahasiswa) {
            tv_nama.setText("Nama : " + mahasiswa.getNama());
            tv_fakultas.setText("Fakultas : " + mahasiswa.getFakultas());
            tv_jurusan.setText("Jurusan : " + mahasiswa.getJurusan());
            tv_semester.setText("Semester : " + mahasiswa.getSemester());

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    FragmentManager fragmentManager = context.getSupportFragmentManager();
                    DialogForm dialogForm = new DialogForm(mahasiswa.getNama(),mahasiswa.getFakultas(),mahasiswa.getJurusan(),mahasiswa.getSemester(),mahasiswa.getKey(),"Ubah");
                    dialogForm.show(fragmentManager,"form");
                    return true;
                }
            });

            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setMessage("Apa kamu yakin ingin menghapus data ini?")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    database.child("test").child(mahasiswa.getKey()).removeValue();
                                    Toast.makeText(context,"Data berhasil dihapus",Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
            });

        }
    }
}
