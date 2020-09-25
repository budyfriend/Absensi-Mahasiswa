package com.budyfriend_code.absensi;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class DialogForm extends DialogFragment {
    String nama,
            fakultas,
            jurusan,
            semester,
            key,
            pilih;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public DialogForm(String pilih) {
        this.pilih = pilih;
    }

    public DialogForm(String nama, String fakultas, String jurusan, String semester, String key, String pilih) {
        this.nama = nama;
        this.fakultas = fakultas;
        this.jurusan = jurusan;
        this.semester = semester;
        this.key = key;
        this.pilih = pilih;
    }

    EditText et_nama,
            et_fakultas,
            et_jurusan,
            et_semester;
    Button btn_simpan;
    TextView title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.input_form, container, false);
        title = v.findViewById(R.id.title);
        et_nama = v.findViewById(R.id.et_nama);
        et_fakultas = v.findViewById(R.id.et_fakultas);
        et_jurusan = v.findViewById(R.id.et_jurusan);
        et_semester = v.findViewById(R.id.et_semester);
        btn_simpan = v.findViewById(R.id.btn_simpan);

        if (pilih.equals("Ubah")){
            et_nama.setText(nama);
            et_fakultas.setText(fakultas);
            et_jurusan.setText(jurusan);
            et_semester.setText(semester);
        }

        title.setText(pilih + " Data");

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String _nama = et_nama.getText().toString();
                String _fakultas = et_fakultas.getText().toString();
                String _jurusan = et_jurusan.getText().toString();
                String _semester = et_semester.getText().toString();

                if (TextUtils.isEmpty(_nama)) {
                    input(et_nama, "nama");
                } else if (TextUtils.isEmpty(_fakultas)) {
                    input(et_nama, "fakultas");
                } else if (TextUtils.isEmpty(_jurusan)) {
                    input(et_nama, "jurusan");
                } else if (TextUtils.isEmpty(_semester)) {
                    input(et_nama, "semester");
                } else {
                    if (pilih.equals("Tambah")) {
                        database.child("test").push().setValue(new mahasiswa(_nama,
                                _fakultas,
                                _jurusan,
                                _semester)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(v.getContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(v.getContext(), "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        });
                    } else {
                        database.child("test").child(key).setValue(new mahasiswa(_nama,
                                _fakultas,
                                _jurusan,
                                _semester)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(v.getContext(), "Data berhasil diubah", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(v.getContext(), "Data gagal diubah", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        });
                    }

                }

            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public void input(EditText txt, String s) {
        txt.setError(s + " tidak boleh kosong");
        txt.requestFocus();
    }
}
