package com.example.asm_mob204_ps10929.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_mob204_ps10929.R;
import com.example.asm_mob204_ps10929.dao.HoaDonChiTietDAO;
import com.example.asm_mob204_ps10929.dao.HoaDonDAO;
import com.example.asm_mob204_ps10929.data_model.HoaDon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonAdapter extends BaseAdapter implements Filterable {
    List<HoaDon> arrHoaDon;
    List<HoaDon> arrSortHoaDon;
    private Filter hoaDonFilter;
    public Activity context;
    public LayoutInflater inflater;
    HoaDonDAO hoaDonDAO;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public HoaDonAdapter(Activity context, List<HoaDon> arrHoaDon) {
        super();
        this.context = context;
        this.arrHoaDon = arrHoaDon;
        this.arrSortHoaDon = arrHoaDon;
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        hoaDonDAO = new HoaDonDAO(context);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
    }

    @Override
    public int getCount() {
        return arrHoaDon.size();
    }

    @Override
    public Object getItem(int position) {
        return arrHoaDon.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class ViewHolder {
        ImageView img;
        TextView txtMaHoaDon;
        TextView txtNgayMua;
        ImageView imgDelete;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_hoa_don, null);
            holder.img = (ImageView) convertView.findViewById(R.id.ivIcon);
            holder.txtMaHoaDon = (TextView) convertView.findViewById(R.id.tvMaHoaDon);
            holder.txtNgayMua = (TextView) convertView.findViewById(R.id.tvNgayMua);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.ivDelete);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (hoaDonChiTietDAO.checkHoaDon(arrHoaDon.get(position).getMaHoaDon())) {
                        Toast.makeText(context, "Bạn phải xóa hóa đơn chi tiết trước khi xóa hóa đơn này", Toast.LENGTH_LONG).show();
                    } else {
                        hoaDonDAO.deleteHoaDonByID(arrHoaDon.get(position).getMaHoaDon());
                        arrHoaDon.remove(position);
                        notifyDataSetChanged();
                    }
                }
            });
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }
        HoaDon _entry = (HoaDon) arrHoaDon.get(position);
        holder.img.setImageResource(R.drawable.bookicon);
        holder.txtMaHoaDon.setText("Mã hóa đơn: " + _entry.getMaHoaDon());
        holder.txtNgayMua.setText(sdf.format(_entry.getNgayMua()));
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<HoaDon> items){
        this.arrHoaDon=items;
        notifyDataSetChanged();
    }

    public void resetData(){
        arrHoaDon=arrSortHoaDon;
    }
    @Override
    public Filter getFilter() {
        if (hoaDonFilter==null){
            hoaDonFilter = new CustomFilter();
        }
        return hoaDonFilter;
    }
    private class CustomFilter extends android.widget.Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint == null || constraint.length()==0){
                filterResults.values=arrSortHoaDon;
                filterResults.count=arrSortHoaDon.size();
            }else {
                List<HoaDon> lsHoaDon = new ArrayList<HoaDon>();

                for (HoaDon p:arrHoaDon) {
                    if (p.getMaHoaDon().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        lsHoaDon.add(p);
                    }
                }
                filterResults.values = lsHoaDon;
                filterResults.count = lsHoaDon.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0){
                notifyDataSetInvalidated();
            }else {
                arrHoaDon = (List<HoaDon>) results.values;
            }
            notifyDataSetChanged();
        }
        }

    }
