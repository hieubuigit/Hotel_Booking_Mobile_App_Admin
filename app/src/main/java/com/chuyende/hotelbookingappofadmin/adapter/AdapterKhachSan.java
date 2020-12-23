package com.chuyende.hotelbookingappofadmin.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.data_model.KhachSan;
import com.chuyende.hotelbookingappofadmin.ui.khachsan.FragmentKhachSan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collection;

public class AdapterKhachSan extends RecyclerView.Adapter<AdapterKhachSan.ViewHolder> implements Filterable {
    Context context;
    ArrayList<KhachSan> listKhachSan;
    ArrayList<KhachSan> listKhachSanAll;
    private ItemClickListener listener;
    private StorageReference mStorageRef;
    private static String PATH_PHONG = "/media/khachSan/";
    public AdapterKhachSan(Context context, ArrayList<KhachSan> listKhachSan, ItemClickListener listener) {
        this.context = context;
        this.listKhachSan = listKhachSan;
        this.listener = listener;
        this.listKhachSanAll = new ArrayList<>(listKhachSan);
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
        String url = PATH_PHONG + khachSan.getMaKhachSan()+ "/" + khachSan.getMaKhachSan() + ".png";
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

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<KhachSan> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(listKhachSanAll);
            } else {
                for (KhachSan khachsan : listKhachSanAll) {
                    if (khachsan.getTenKhachSan().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(khachsan);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            listKhachSan.clear();
            listKhachSan.addAll((Collection<? extends KhachSan>) results.values);
            notifyDataSetChanged();
        }
    };


    //ánh xạ
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{
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


}


