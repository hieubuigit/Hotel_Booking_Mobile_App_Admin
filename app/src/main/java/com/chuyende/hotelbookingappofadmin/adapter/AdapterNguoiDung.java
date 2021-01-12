package com.chuyende.hotelbookingappofadmin.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.data_model.NguoiDung;

import java.util.ArrayList;
import java.util.Collection;

public class AdapterNguoiDung extends RecyclerView.Adapter<AdapterNguoiDung.ViewHolder> implements Filterable {
    Context context;
    ArrayList<NguoiDung> listNguoiDung;
    ArrayList<NguoiDung> listNguoiDungAll;
    private ItemClickListener listener;

    public AdapterNguoiDung(Context context, ArrayList<NguoiDung> listNguoiDung, ItemClickListener listener) {
        this.context = context;
        this.listNguoiDung = listNguoiDung;
        this.listener = listener;
        this.listNguoiDungAll = new ArrayList<>(listNguoiDung);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.custom_item_nguoidung, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NguoiDung nguoiDung = listNguoiDung.get(position);
        holder.tvTenNguoiDung.setText(nguoiDung.getTenNguoiDung());
        holder.tvNgaySinh.setText(nguoiDung.getNgaySinh());
        holder.tvGioiTinh.setText(nguoiDung.getGioiTinh());
        holder.tvQuocTich.setText(nguoiDung.getQuocTich());
        holder.imgAnhDaiDien.setImageResource(R.drawable.men);
    }

    @Override
    public int getItemCount() {
        return listNguoiDung.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<NguoiDung> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(listNguoiDungAll);
            } else {
                for (NguoiDung nguoiDung : listNguoiDungAll) {
                    if (nguoiDung.getTenNguoiDung().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(nguoiDung);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            listNguoiDung.clear();
            listNguoiDung.addAll((Collection<? extends NguoiDung>) results.values);
            notifyDataSetChanged();
        }
    };


    //ánh xạ
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        TextView tvTenNguoiDung, tvNgaySinh, tvGioiTinh, tvQuocTich;
        ImageView imgAnhDaiDien;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTenNguoiDung = itemView.findViewById(R.id.tvTenNguoiDung);
            tvNgaySinh = itemView.findViewById(R.id.tvNgaySinh);
            tvGioiTinh = itemView.findViewById(R.id.tvGioiTinh);
            tvQuocTich = itemView.findViewById(R.id.tvQuocTich);
            imgAnhDaiDien = itemView.findViewById(R.id.imgHinhDaiDien);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        }

    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }
}
