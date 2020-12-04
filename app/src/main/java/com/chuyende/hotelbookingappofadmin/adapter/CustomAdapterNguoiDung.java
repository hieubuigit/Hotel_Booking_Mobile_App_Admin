package com.chuyende.hotelbookingappofadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.data_model.NguoiDung;
import com.chuyende.hotelbookingappofadmin.firebase.FireStore_NguoiDung;

import java.util.ArrayList;

public class CustomAdapterNguoiDung extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<NguoiDung> data;

    public CustomAdapterNguoiDung(Context context, int resource, ArrayList<NguoiDung> data) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    private static class Holder {
        TextView tvTenNguoiDung, tvNgaySinh, tvGioiTinh, tvQuocTich;
        ImageView imgAnhDaiDien;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder holder = null;
        if (view == null) {
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(resource, null);
            holder.tvTenNguoiDung = view.findViewById(R.id.tvTenNguoiDung);
            holder.tvNgaySinh = view.findViewById(R.id.tvNgaySinh);
            holder.tvGioiTinh = view.findViewById(R.id.tvGioiTinh);
            holder.tvQuocTich = view.findViewById(R.id.tvQuocTich);
            holder.imgAnhDaiDien = view.findViewById(R.id.imgHinhDaiDien);
            view.setTag(holder);
        } else
            holder = (Holder) view.getTag();

        final NguoiDung nguoiDung = data.get(position);

        holder.tvTenNguoiDung.setText(nguoiDung.getTenNguoiDung());
        holder.tvNgaySinh.setText(nguoiDung.getNgaySinh());
        holder.tvGioiTinh.setText(nguoiDung.getGioiTinh());
        holder.tvQuocTich.setText(nguoiDung.getQuocTich());
        if (nguoiDung.getUrlAnhDaiDien() == null) {
            holder.imgAnhDaiDien.setImageResource(R.drawable.men);
        }
//        else {
//            Bitmap bmHinhDaiDien = BitmapFactory.decodeByteArray(nhanVien.getImage(), 0, nhanVien.getImage().length);
//            bmHinhDaiDien = Bitmap.createScaledBitmap(bmHinhDaiDien, 80, 80, true);
//            holder.imgAnhDaiDien.setImageBitmap(bmHinhDaiDien);
//        }
        return view;
    }
}
