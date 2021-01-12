package com.chuyende.hotelbookingappofadmin.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.data_model.KhachSan;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdapterKhachSan extends RecyclerView.Adapter<AdapterKhachSan.ViewHolder> {
    Context context;
    ArrayList<KhachSan> listKhachSan = new ArrayList<>();
    ArrayList<KhachSan> listKhachSanAll = new ArrayList<>();
    private ItemClickListener listener;
    private StorageReference mStorageRef;
    private static String PATH_PHONG = "/media/khachSan/";
    private static String TAT_CA = "Tỉnh/Thành phố";

    public AdapterKhachSan(ArrayList<KhachSan> listKhachSan, Context context, ItemClickListener listener) {
        this.context = context;
        this.listKhachSan = listKhachSan;
        this.listener = listener;
        this.listKhachSanAll.addAll(listKhachSan);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.custom_item_khachsan, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mStorageRef = FirebaseStorage.getInstance().getReference();
        KhachSan khachSan = listKhachSan.get(position);
        holder.tvTenKhachSan.setText(khachSan.getTenKhachSan());
        holder.tvDiaChiKS.setText(khachSan.getDiaDiemKhachSan());
        String url = PATH_PHONG + khachSan.getMaKhachSan() + "/" + khachSan.getMaKhachSan() + ".png";
        mStorageRef.child(url).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext()).load(uri).into(holder.imgAnhDaiDienKS);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listKhachSan.size();
    }

    //ánh xạ
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        TextView tvTenKhachSan, tvDiaChiKS;
        ImageView imgAnhDaiDienKS;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTenKhachSan = itemView.findViewById(R.id.tvTenKS);
            tvDiaChiKS = itemView.findViewById(R.id.tvDiaChiKS);
            imgAnhDaiDienKS = itemView.findViewById(R.id.imgAnhKhachSan);
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

    // Filter and Search Khách sạn
    public void searchAndFilter(String query, String itemKhachSanSelected) {
        listKhachSan.clear();
        //không search, không lọc
        if (query.isEmpty() && itemKhachSanSelected.equals(TAT_CA)) {
            listKhachSan.addAll(listKhachSanAll);
        } else {
            if (!query.isEmpty()) {
                if (!itemKhachSanSelected.equals(TAT_CA)) {
                    // Search + lọc Spinner Khach sạn
                    query = query.toLowerCase();
                    for (KhachSan ks : listKhachSanAll) {
                        if (ks.getTenKhachSan().toLowerCase().contains(query) && ks.getDiaDiemKhachSan().equals(itemKhachSanSelected)) {
                            listKhachSan.add(ks);
                        }
                    }
                } else {
                    // Search, không lọc spinner
                    query = query.toLowerCase();
                    for (KhachSan ks : listKhachSanAll) {
                        if (ks.getTenKhachSan().toLowerCase().contains(query)) {
                            listKhachSan.add(ks);
                        }
                    }
                }
            } else {
                if (!itemKhachSanSelected.equals(TAT_CA)) {
                    // Spinner Loai Phong changed
                    for (KhachSan ks : listKhachSanAll) {
                        if (ks.getDiaDiemKhachSan().equals(itemKhachSanSelected)) {
                            listKhachSan.add(ks);
                        }
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}


