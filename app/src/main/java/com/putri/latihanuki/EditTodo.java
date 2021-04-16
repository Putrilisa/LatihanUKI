package com.putri.latihanuki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditTodo extends AppCompatActivity {
    EditText edtTitle,edtDesc,edtDate;
    Button btnUpdate,btnDelete;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalender;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        edtTitle = findViewById(R.id.edtTittel);
        edtDesc = findViewById(R.id.etdDesc);
        edtDate = findViewById(R.id.edtDate);
        btnUpdate = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        myCalender = Calendar.getInstance();
        myDB = new DatabaseHelper(this);

        edtTitle.setText(getIntent().getStringExtra("titletodo"));
        edtDesc.setText(getIntent().getStringExtra("desctodo"));
        edtDate.setText(getIntent().getStringExtra("datetodo"));

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalender.set(Calendar.YEAR, year);
                myCalender.set(Calendar.MONTH, month);
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }
        };
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditTodo.this, date, myCalender.get(Calendar.YEAR),
                        myCalender.get(Calendar.MONTH),myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String desc = edtDesc.getText().toString();
                String date = edtDate.getText().toString();
                String id = getIntent().getStringExtra("idtodo");
                if (title.equals("") || date.equals("") || desc.equals("")){
                    if (title.equals("")) {
                        edtTitle.setError("Judul harus di isi ");
                    }
                    if (desc.equals("")) {
                        edtDesc.setError("Judul harus di isi ");
                    }
                    if (date.equals("")) {
                        edtDate.setError("Judul harus di isi ");
                    }
                }else{
                    boolean isUpdate = myDB.updateData(title,desc,date,id);
                    if (isUpdate){
                        Toast.makeText(EditTodo.this,"Data berhsil di ubah", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(EditTodo.this,"Data gagal di ubah ", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(EditTodo.this,MainActivity.class));
                    finish();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getIntent().getStringExtra("idtodo");
                Integer deleteRows = myDB.deletData(id);

                if (deleteRows > 0){
                    Toast.makeText(EditTodo.this,"Data berhasil di hapus",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(EditTodo.this,"Data gagal di hapus",Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(EditTodo.this,MainActivity.class));
                finish();
            }
        });
    }
    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        edtDate.setText(simpleDateFormat.format(myCalender.getTime()));
    }
}