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

import com.example.asm_mob204_ps10929.R;
import com.example.asm_mob204_ps10929.dao.SachDAO;
import com.example.asm_mob204_ps10929.data_model.Sach;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter  extends BaseAdapter implements Filterable {
    List<Sach> arrSach;
    List<Sach> arrSortSach;
    private Filter sachFilter;
    public Activity context;
    public LayoutInflater inflater;
    SachDAO sachDAO;

    public BookAdapter(Activity context, List<Sach> arraySach) {
        super();
        this.context = context;
        this.arrSach = arraySach;
        this.arrSortSach = arraySach;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sachDAO = new SachDAO(context);
    }

    @Override
    public int getCount() {
        return arrSach.size();
    }

    @Override
    public Object getItem(int position) {
        return arrSach.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder {
        ImageView img;
        TextView txtBookName;
        TextView txtBookPrice;
        TextView txtSoLuong;
        ImageView imgDelete;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_book, null);
            holder.img = (ImageView) convertView.findViewById(R.id.ivIcon);
            holder.txtBookName = (TextView) convertView.findViewById(R.id.tvBookName);
            holder.txtBookPrice = (TextView) convertView.findViewById(R.id.tvBookPrice);
            holder.txtSoLuong = (TextView) convertView.findViewById(R.id.tvSoLuong);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.ivDelete);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sachDAO.deleteSachByID(arrSach.get(position).getMaSach());
                    arrSach.remove(position);
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }
            Sach _entry = (Sach) arrSach.get(position);
            holder.img.setImageResource(R.drawable.bookicon);
            holder.txtBookName.setText("Mã sách: " + _entry.getMaSach());
            holder.txtSoLuong.setText("Số lượng: " + _entry.getSoLuong());
            holder.txtBookPrice.setText("Giá: " + _entry.getGiaBia() + "");
            return convertView;
        }
        public void changeDataset(List<Sach>item){
        this.arrSach=item;
        notifyDataSetChanged();
        }
        public void resetData(){
        arrSach=arrSortSach;
        }
        public Filter getFilter(){
            if (sachFilter == null){
                sachFilter = new CustomFilter();}
                return sachFilter;
            }
        private class CustomFilter extends android.widget.Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint == null || constraint.length()==0){
                filterResults.values=arrSortSach;
                filterResults.count=arrSortSach.size();
            }else {
                List<Sach> lsSach = new ArrayList<Sach>();

                for (Sach p:arrSach) {
                    if (p.getMaSach().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        lsSach.add(p);
                    }
                }
                filterResults.values = lsSach;
                filterResults.count = lsSach.size();
            }
            return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.count == 0){
                    notifyDataSetInvalidated();
                }else {
                    arrSach = (List<Sach>) results.values;
                }
                notifyDataSetChanged();
                }
        }
    }