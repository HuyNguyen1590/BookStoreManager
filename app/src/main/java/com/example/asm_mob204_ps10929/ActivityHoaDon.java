package com.example.asm_mob204_ps10929;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_mob204_ps10929.dao.HoaDonDAO;
import com.example.asm_mob204_ps10929.data_model.HoaDon;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ActivityHoaDon extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText edNgayMua, edMaHoaDon;
    HoaDonDAO hoaDonDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Thêm hóa đơn");
        setContentView(R.layout.activity_add_hoa_don);
        edNgayMua = findViewById(R.id.edNgayMua);
        edMaHoaDon = findViewById(R.id.edMaHoaDon);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day)
    { Calendar cal = new GregorianCalendar(year, month, day);
    setDate(cal);
    }
    private void setDate(final Calendar calendar) {
        int month = +calendar.get(Calendar.MONTH)+1;
        String string = "";
        if (month<10){
            string="0"+month;
        }
        edNgayMua.setText(calendar.get(Calendar.DAY_OF_MONTH)+"-"+(string)+"-"+calendar.get(Calendar.YEAR));
    }
    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        }
    }
    public void datePicker(View view){
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(),"date");
    }
    public void ADDHoaDon(View view) {
        hoaDonDAO = new HoaDonDAO(ActivityHoaDon.this);

        try {
            if (validation() < 0) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                HoaDon hoaDon = new HoaDon(edMaHoaDon.getText().toString(),sdf.parse(edNgayMua.getText().toString()));
                if (hoaDonDAO.inserHoaDon(hoaDon) > 0) { Toast.makeText(getApplicationContext(), "Thêm thành công",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityHoaDon.this,ActivityHoaDonChiTiet.class);
                    Bundle b = new Bundle();
                    b.putString("MAHOADON", edMaHoaDon.getText().toString());
                    intent.putExtras(b);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Exception ex) { Log.e("Error", ex.toString());
        }
    }
    public int validation(){
        if
        (edMaHoaDon.getText().toString().isEmpty()||edNgayMua.getText().toString().isEmpty()
        ){
            return -1;
        }
        return 1;
    }

}
