package com.example.asm_mob204_ps10929;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_mob204_ps10929.Adapter.CartAdapter;
import com.example.asm_mob204_ps10929.dao.HoaDonChiTietDAO;
import com.example.asm_mob204_ps10929.dao.SachDAO;
import com.example.asm_mob204_ps10929.data_model.HoaDon;
import com.example.asm_mob204_ps10929.data_model.HoaDonChiTiet;
import com.example.asm_mob204_ps10929.data_model.Sach;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityHoaDonChiTiet extends AppCompatActivity {
    EditText edMaSach, edMaHoaDon, edSoLuong;
    TextView tvThanhTien;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    SachDAO sachDAO;
    public List<HoaDonChiTiet> dsHDCT = new ArrayList<>();
    ListView lvCart;
    CartAdapter adapter = null;
    double thanhTien=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Chi tiết hóa đơn");
        setContentView(R.layout.activity_hoa_don_chi_tiet);
        edMaSach = findViewById(R.id.edMaSach);
        edMaHoaDon = findViewById(R.id.edMaHoaDon);
        edSoLuong = findViewById(R.id.edSoLuongMua);
        lvCart = findViewById(R.id.lvCart);
        tvThanhTien = findViewById(R.id.tvThanhTien);

        adapter = new CartAdapter(this,dsHDCT);
        lvCart.setAdapter(adapter);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            edMaHoaDon.setText(b.getString("MAHOADON"));
        }
    }
    public void ADDHoaDonCHITIET(View view) {
        hoaDonChiTietDAO = new HoaDonChiTietDAO(ActivityHoaDonChiTiet.this);
        sachDAO = new SachDAO(ActivityHoaDonChiTiet.this);
        try {
            if (validation() < 0) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                Sach sach = sachDAO.getSachByID(edMaSach.getText().toString());
                if (sach!=null){
                    int pos = checkMaSach(dsHDCT,edMaSach.getText().toString());
                    HoaDon hoaDon = new HoaDon(edMaHoaDon.getText().toString(),new Date());
                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(hoaDon,sach,Integer.parseInt(edSoLuong.getText().toString()));
                    if (pos>=0){
                        int soluong = dsHDCT.get(pos).getSoLuongMua();
                        hoaDonChiTiet.setSoLuongMua(soluong + Integer.parseInt(edSoLuong.getText().toString()));
                        dsHDCT.set(pos,hoaDonChiTiet);
                    }else {
                        dsHDCT.add(hoaDonChiTiet);
                    }
                    adapter.changeDataset(dsHDCT);
                }else {
                    Toast.makeText(getApplicationContext(),"Mã sách không tồn tại",Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) { Log.e("Error", ex.toString());
        }
    }
    public void thanhToanHoaDon(View view) {
        hoaDonChiTietDAO = new HoaDonChiTietDAO(ActivityHoaDonChiTiet.this);
//tinh tien
         thanhTien = 0;
         try {
        for (HoaDonChiTiet hd: dsHDCT)
        { hoaDonChiTietDAO.inserHoaDonChiTiet(hd);
        thanhTien = thanhTien + hd.getSoLuongMua()*hd.getSach().getGiaBia();
        }
        tvThanhTien.setText("Tổng tiền: " +thanhTien);
             Toast.makeText(this, "Cảm ơn đã thanh toán", Toast.LENGTH_SHORT).show();
             Intent intent = new Intent(ActivityHoaDonChiTiet.this,ActivityListHoaDon.class);
             startActivity(intent);
             finish();
         } catch (Exception ex) { Log.e("Error", ex.toString());
    }
}

    public int checkMaSach(List<HoaDonChiTiet> lsHD, String maSach){
        int pos = -1;
        for (int i = 0; i < lsHD.size(); i++){ HoaDonChiTiet hd = lsHD.get(i);
            if (hd.getSach().getMaSach().equalsIgnoreCase(maSach)){ pos = i;
                break;
            }
        }
        return pos;
    }
    public int validation(){
        if (edMaSach.getText().toString().isEmpty()||edSoLuong.getText().toString().isEmpty()|| edMaHoaDon.getText().toString().isEmpty()){
            return -1;
        }
        return 1;
    }
}
