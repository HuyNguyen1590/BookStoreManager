package com.example.asm_mob204_ps10929;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.asm_mob204_ps10929.dao.HoaDonChiTietDAO;
import com.example.asm_mob204_ps10929.dao.HoaDonDAO;
import com.example.asm_mob204_ps10929.data_model.HoaDon;
import com.example.asm_mob204_ps10929.data_model.HoaDonChiTiet;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void viewNguoiDung(View v){
        Intent intent = new Intent(MainActivity.this,ActivityListNguoiDung.class);
        startActivity(intent);
    }
    public void viewTheLoai(View v){
        Intent intent = new Intent(MainActivity.this,ActivityListTheLoai.class);
        startActivity(intent);
    }
    public void viewListBookActivity(View v){
        Intent intent = new Intent(MainActivity.this,ActivityListBook.class);
        startActivity(intent);
    }
    public void viewListHoaDonActivity(View v){
        Intent intent = new Intent(MainActivity.this,ActivityListHoaDon.class);
        startActivity(intent);
    }
    public void ViewTopSach(View v){
        Intent intent = new Intent(MainActivity.this,ActivityListSachBanChay.class);
        startActivity(intent);
    }
    public void ViewThongKeActivity(View v){
        Intent intent = new Intent(MainActivity.this,ActivityThongKeDoanhThu.class);
        startActivity(intent);
    }
}
