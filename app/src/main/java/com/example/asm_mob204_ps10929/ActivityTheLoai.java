package com.example.asm_mob204_ps10929;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asm_mob204_ps10929.dao.TheLoaiDAO;
import com.example.asm_mob204_ps10929.data_model.TheLoai;

import java.util.ArrayList;
import java.util.List;

public class ActivityTheLoai extends AppCompatActivity {
    TheLoaiDAO theLoaiDAO;
    EditText edMaTheLoai, edTenTheLoai, edViTri, edMoTa;
    List<TheLoai> listTheLoai = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_the_loai);
        setTitle("Thêm thể loại");
        edMaTheLoai = findViewById(R.id.edMaTheLoai);
        edTenTheLoai = findViewById(R.id.edTenTheLoai);
        edViTri = findViewById(R.id.edViTri);
        edMoTa = findViewById(R.id.edMoTa);
    }
    public void showTheLoai(View view){
    finish();
    }
    public void addTheLoai(View view){
    theLoaiDAO = new TheLoaiDAO(ActivityTheLoai.this);
    TheLoai theLoai = new TheLoai(edMaTheLoai.getText().toString(),
            edTenTheLoai.getText().toString(),
            edMoTa.getText().toString(),
            Integer.parseInt(edViTri.getText().toString()));
        try {
                if (theLoaiDAO.inserTheLoai(theLoai) > 0) {
                    Toast.makeText(getApplicationContext(), "Thêm thành công",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }

        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }
}
