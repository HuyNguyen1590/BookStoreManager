package com.example.asm_mob204_ps10929.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asm_mob204_ps10929.R;
import com.example.asm_mob204_ps10929.dao.TheLoaiDAO;
import com.example.asm_mob204_ps10929.data_model.TheLoai;

import java.util.List;

public class TheLoaiAdapter extends BaseAdapter {
    List<TheLoai>arrTheLoai;
    public Activity context;
    public LayoutInflater inflater;
    TheLoaiDAO theLoaiDAO;

    public TheLoaiAdapter(Activity context,List<TheLoai> arrTheLoai) {
        super();
        this.arrTheLoai = arrTheLoai;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        theLoaiDAO = new TheLoaiDAO(context);
    }

    @Override
    public int getCount() {
        return arrTheLoai.size();
    }

    @Override
    public Object getItem(int position) {
        return arrTheLoai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder{

        ImageView img;
        TextView txtMaTheLoai;
        TextView txtTenTheLoai;
        ImageView imgDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_theloai,null);
            holder.img=convertView.findViewById(R.id.ivIcon);
            holder.txtMaTheLoai=convertView.findViewById(R.id.tvMaTheLoai);
            holder.txtTenTheLoai=convertView.findViewById(R.id.tvTenTheLoai);
            holder.imgDelete=convertView.findViewById(R.id.ivDelete);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    theLoaiDAO.deleteTheLoaiByID(arrTheLoai.get(position).getMaTheLoai());
                    arrTheLoai.remove(position);
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
            TheLoai _entry = arrTheLoai.get(position);
            holder.img.setImageResource(R.drawable.cateicon);
            holder.txtMaTheLoai.setText(_entry.getMaTheLoai());
            holder.txtTenTheLoai.setText(_entry.getTenTheLoai());
            return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    public void changeDataset(List<TheLoai>items){
        this.arrTheLoai = items;
        notifyDataSetChanged();
    }
}
