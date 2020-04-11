package com.example.asm_mob204_ps10929;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asm_mob204_ps10929.Adapter.BookAdapter;
import com.example.asm_mob204_ps10929.dao.SachDAO;
import com.example.asm_mob204_ps10929.data_model.Sach;

import java.util.ArrayList;
import java.util.List;

public class ActivityListSachBanChay extends AppCompatActivity {
    public static List<Sach> dsSach = new ArrayList<>();
    ListView lvBook;
    BookAdapter adapter = null;
    SachDAO sachDAO;
    EditText edThang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sach_ban_chay);
        setTitle("TOP 10 SÁCH BÁN CHẠY");
        lvBook = (ListView) findViewById(R.id.lvBookTop);
        edThang = (EditText) findViewById(R.id.edThang);

    }
    public void VIEW_SACH_TOP_10(View view){
        if (Integer.parseInt(edThang.getText().toString())>13 || Integer.parseInt(edThang.getText().toString())<0){
            Toast.makeText(getApplicationContext(),"Không đúng định dạng tháng (1- 12)", Toast.LENGTH_SHORT).show();
        }else {
            sachDAO = new SachDAO(ActivityListSachBanChay.this);
            dsSach = sachDAO.getSachTop10(edThang.getText().toString());
            adapter = new BookAdapter(this, dsSach); lvBook.setAdapter(adapter);
        }
    }
}
