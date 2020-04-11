package com.example.asm_mob204_ps10929;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asm_mob204_ps10929.Adapter.BookAdapter;
import com.example.asm_mob204_ps10929.dao.SachDAO;
import com.example.asm_mob204_ps10929.data_model.Sach;

import java.util.ArrayList;
import java.util.List;

public class ActivityListBook extends AppCompatActivity {
    public static List<Sach> dsSach = new ArrayList<>();
    ListView lvBook;
    BookAdapter adapter = null;
    SachDAO sachDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sach);
        setTitle("Sách");
        lvBook = findViewById(R.id.lvBook);

        sachDAO=new SachDAO(this);
        dsSach = sachDAO.getAllSach();

        adapter = new BookAdapter(this,dsSach);
        lvBook.setAdapter(adapter);
        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sach sach =(Sach) parent.getItemAtPosition(position);
                Intent intent = new Intent(ActivityListBook.this,ActivityThemSach.class);
                Bundle bundle = new Bundle();
                bundle.putString("MASACH",sach.getMaSach());
                bundle.putString("MATHELOAI",sach.getMaTheLoai());
                bundle.putString("TENSACH",sach.getTenSach());
                bundle.putString("TACGIA",sach.getTacGia());
                bundle.putString("NXB",sach.getNXB());
                bundle.putString("GIABIA",String.valueOf(sach.getGiaBia()));
                bundle.putString("SOLUONG",String.valueOf(sach.getSoLuong()));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        lvBook.setTextFilterEnabled(true);
        final EditText edSearch = (EditText) findViewById(R.id.edSearchBook);
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text [" + s + "] - Start [" + start + "] - Before [" + before + "] - Count [" + count + "]");
                if (count < before) {
                    adapter.resetData();
                }
                    adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_book:
                Intent intent = new Intent(ActivityListBook.this,ActivityThemSach.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dsSach.clear();
        dsSach = sachDAO.getAllSach();
        adapter.changeDataset(dsSach);
    }
}
