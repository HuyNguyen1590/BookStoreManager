package com.example.asm_mob204_ps10929;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asm_mob204_ps10929.Adapter.HoaDonAdapter;
import com.example.asm_mob204_ps10929.dao.HoaDonChiTietDAO;
import com.example.asm_mob204_ps10929.dao.HoaDonDAO;
import com.example.asm_mob204_ps10929.data_model.HoaDon;
import com.example.asm_mob204_ps10929.data_model.HoaDonChiTiet;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ActivityListHoaDon extends AppCompatActivity {
    public List<HoaDon>dsHoaDon = new ArrayList<>();
    ListView lvHoaDon;
    HoaDonAdapter adapter = null;
    HoaDonDAO hoaDonDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hoa_don);
        setTitle("Hóa đơn");
        lvHoaDon = findViewById(R.id.lvHoaDon);
        hoaDonDAO = new HoaDonDAO(ActivityListHoaDon.this);

        try {
            dsHoaDon = hoaDonDAO.getAllHoaDon();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        adapter = new HoaDonAdapter(this,dsHoaDon);
        lvHoaDon.setAdapter(adapter);
        lvHoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HoaDon hoaDon =(HoaDon) parent.getItemAtPosition(position);
                Intent intent = new Intent(ActivityListHoaDon.this,ActivityListHoaDonChiTiet.class);
                Bundle b = new Bundle();
                b.putString("MAHOADON", hoaDon.getMaHoaDon());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        lvHoaDon.setTextFilterEnabled(true);
        final EditText edSearch = findViewById(R.id.edSearchHoaDon);
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {
                System.out.println("Text [" + s + "] - Start [" + start + "] - Before [" + before + "] - Count [" + count + "]");
                if (count < before) {
                    adapter.resetData();
                }
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { getMenuInflater().inflate(R.menu.menu_hoa_don, menu); return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.addHoaDon:
                Intent intent = new
                        Intent(ActivityListHoaDon.this,ActivityHoaDon.class);
                startActivity(intent);
                return(true);
        }
        return super.onOptionsItemSelected(item);
    }
}

