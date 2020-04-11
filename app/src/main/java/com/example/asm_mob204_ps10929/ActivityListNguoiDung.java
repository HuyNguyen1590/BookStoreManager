package com.example.asm_mob204_ps10929;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.asm_mob204_ps10929.Adapter.NguoiDungAdapter;
import com.example.asm_mob204_ps10929.dao.NguoiDungDAO;
import com.example.asm_mob204_ps10929.data_model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class ActivityListNguoiDung extends AppCompatActivity {
    public static List<NguoiDung> dsNguoiDung = new ArrayList<>();
    ListView lvNguoiDung;
    NguoiDungAdapter adapter = null;
    NguoiDungDAO nguoiDungDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nguoi_dung);
        setTitle("Người dùng");
        lvNguoiDung = (ListView) findViewById(R.id.lvNguoiDung);

        nguoiDungDAO = new NguoiDungDAO(ActivityListNguoiDung.this);
        dsNguoiDung = nguoiDungDAO.getAllNguoiDung();

        adapter = new NguoiDungAdapter(this, dsNguoiDung);
        lvNguoiDung.setAdapter(adapter);
        lvNguoiDung.setOnItemClickListener(new AdapterView.OnItemClickListener() { @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent = new Intent(ActivityListNguoiDung.this,ActivityNguoiDungDetail.class);
            Bundle b = new Bundle();
            b.putString("USERNAME", dsNguoiDung.get(position).getUserName());
            b.putString("PHONE", dsNguoiDung.get(position).getPhone());
            b.putString("FULLNAME", dsNguoiDung.get(position).getHoTen());
            intent.putExtras(b);
            startActivity(intent);

        }
        });
        lvNguoiDung.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int
                    position, long id) {
                return false;
            }
        });
    }

    @Override
    protected void onResume() { super.onResume(); dsNguoiDung.clear();
        dsNguoiDung = nguoiDungDAO.getAllNguoiDung(); adapter.changeDataset(nguoiDungDAO.getAllNguoiDung());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { getMenuInflater().inflate(R.menu.menu_user, menu); return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { Intent intent;
        switch(item.getItemId()) {
            case R.id.add:
                intent = new Intent(ActivityListNguoiDung.this,ActivityNguoiDung.class);
                startActivity(intent);
                return(true);
            case R.id.changePass:
                intent = new Intent(ActivityListNguoiDung.this,ActivityChangePassword.class);
                startActivity(intent);
                return(true);
                case R.id.logOut:
                SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
//xoa tinh trang luu tru truoc do
                edit.clear();
                edit.commit();
                intent = new Intent(ActivityListNguoiDung.this,MainActivity.class);
                startActivity(intent);
            break;
}
return super.onOptionsItemSelected(item);
        }
        }

