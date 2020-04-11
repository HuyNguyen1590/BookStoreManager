package com.example.asm_mob204_ps10929;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.asm_mob204_ps10929.Adapter.CartAdapter;
import com.example.asm_mob204_ps10929.dao.HoaDonChiTietDAO;
import com.example.asm_mob204_ps10929.data_model.HoaDonChiTiet;

import java.util.ArrayList;
import java.util.List;

public class ActivityListHoaDonChiTiet extends AppCompatActivity {
    public List<HoaDonChiTiet> dsHDCT = new ArrayList<>();
    ListView lvCart;
    CartAdapter adapter = null;
    HoaDonChiTietDAO hoaDonChiTietDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hoa_don_chi_tiet);
        setTitle("HOÁ ĐƠN CHI TIẾT");
        lvCart = (ListView) findViewById(R.id.lvHoaDonChiTiet);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(this);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            dsHDCT = hoaDonChiTietDAO.getAllHoaDonChiTiet();
        }

        adapter = new CartAdapter(this, dsHDCT);
        lvCart.setAdapter(adapter);

    }
}

