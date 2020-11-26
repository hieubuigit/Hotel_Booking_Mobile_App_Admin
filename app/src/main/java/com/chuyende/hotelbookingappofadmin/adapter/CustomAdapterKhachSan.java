package com.chuyende.hotelbookingappofadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.data_model.KhachSan;

import java.util.ArrayList;

public class CustomAdapterKhachSan extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<KhachSan> data;

    public CustomAdapterKhachSan(Context context, int resource, ArrayList<KhachSan> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    private static class Holder {
        TextView tvTenKhachSan, tvDiaChi;
        ImageView imgAnhKhachSan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        CustomAdapterKhachSan.Holder holder = null;
        if(view == null) {
            holder = new CustomAdapterKhachSan.Holder();
            view = LayoutInflater.from(context).inflate(resource, null);
            holder.tvTenKhachSan = view.findViewById(R.id.tvTenKS);
            holder.tvDiaChi = view.findViewById(R.id.tvDiaChi);
            holder.imgAnhKhachSan = view.findViewById(R.id.imgAnhKhachSan);
            view.setTag(holder);
        }
        else
            holder=(CustomAdapterKhachSan.Holder)view.getTag();

        final KhachSan khachSan = data.get(position);

        holder.tvTenKhachSan.setText(khachSan.getTenKhachSan());
        holder.tvDiaChi.setText(khachSan.getDiaChi());
        if(khachSan.getUrlAnhKhachSan() == null){
            holder.imgAnhKhachSan.setImageResource(R.drawable.hotel);
        }
//        else {
//            Bitmap bmHinhDaiDien = BitmapFactory.decodeByteArray(nhanVien.getImage(), 0, nhanVien.getImage().length);
//            bmHinhDaiDien = Bitmap.createScaledBitmap(bmHinhDaiDien, 80, 80, true);
//            holder.imgAnhDaiDien.setImageBitmap(bmHinhDaiDien);
//        }
        return view;
    }
}
