package com.example.asm_mob204_ps10929;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asm_mob204_ps10929.dao.SachDAO;
import com.example.asm_mob204_ps10929.dao.TheLoaiDAO;
import com.example.asm_mob204_ps10929.data_model.Sach;
import com.example.asm_mob204_ps10929.data_model.TheLoai;

import java.util.ArrayList;
import java.util.List;

public class ActivityThemSach extends AppCompatActivity {
    SachDAO sachDAO;
    TheLoaiDAO theLoaiDAO;
    Spinner spnTheLoai;
    EditText edMaSach, edTenSach, edNXB, edTacGia, edGiaBia, edSoLuong;
    String maTheLoai;
    List<TheLoai> listTheLoai = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach);
        setTitle("Thêm sách");
        spnTheLoai = (Spinner)findViewById(R.id.spnTheLoai);
        getTheLoai();
        edMaSach = findViewById(R.id.edMaSach);
        edTenSach = findViewById(R.id.edTenSach);
        edNXB = findViewById(R.id.edNXB);
        edTacGia = findViewById(R.id.edTacGia);
        edGiaBia = findViewById(R.id.edGiaBia);
        edSoLuong = findViewById(R.id.edSoLuong);

        spnTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTheLoai=listTheLoai.get(spnTheLoai.getSelectedItemPosition()).getMaTheLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent intent = getIntent();
        Bundle bundle= intent.getExtras();
        if (bundle !=null){
            edMaSach.setText(bundle.getString("MASACH"));
            String matheloai = bundle.getString("MATHELOAI");
            edTenSach.setText(bundle.getString("TENSACH"));
            edNXB.setText(bundle.getString("NXB"));
            edTacGia.setText(bundle.getString("TACGIA"));
            edGiaBia.setText(bundle.getString("GIABIA"));
            edSoLuong.setText(bundle.getString("SOLUONG"));
            spnTheLoai.setSelection(checkPositionTheLoai(matheloai));
        }
    }
    public void showSpinner(View view){
        sachDAO = new SachDAO(ActivityThemSach.this);
        sachDAO.getAllSach();
    }

    public void getTheLoai(){
        theLoaiDAO = new TheLoaiDAO(ActivityThemSach.this);
        listTheLoai =theLoaiDAO.getAllTheLoai();
        ArrayAdapter<TheLoai>arrayAdapter= new ArrayAdapter<TheLoai>(this,android.R.layout.simple_spinner_item,listTheLoai);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTheLoai.setAdapter(arrayAdapter);
    }
    public void addBook(View v){
        sachDAO = new SachDAO(this);
        Sach sach = new Sach(
                edMaSach.getText().toString(),
                maTheLoai,
                edTenSach.getText().toString(),
                edTacGia.getText().toString(),
                edNXB.getText().toString(),
                Double.parseDouble(edGiaBia.getText().toString()),
                Integer.parseInt(edSoLuong.getText().toString())
        );
        if (sachDAO.inserSach(sach)>0){
        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
    }
    public void showBook(View view){ finish();
    }

    public int checkPositionTheLoai(String theloai){
        for (int i=0;i<listTheLoai.size();i++){
            if (theloai.equals(listTheLoai.get(i).getMaTheLoai())){
                return i;
            }
        }
        return 0;
    }
}
