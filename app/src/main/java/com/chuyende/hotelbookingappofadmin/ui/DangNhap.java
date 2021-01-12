package com.chuyende.hotelbookingappofadmin.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.library.CheckConnection;
import com.chuyende.hotelbookingappofadmin.library.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class DangNhap extends AppCompatActivity {
    static DangNhap connectInstance;

    Button btnDangNhap;
    EditText edtTenDangNhap;
    EditText edtMatKhau;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    LoadingDialog loadingDialog = new LoadingDialog(DangNhap.this);

    CheckConnection checkConnection;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        connectInstance = this;
        setControl();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setEvent();
    }

    private void setEvent() {
        checkConnection = new CheckConnection();
        if (checkConnection.isConnected()) {
            btnDangNhap.setOnClickListener(v -> {
                String tenDangNhap = edtTenDangNhap.getText().toString();
                String matKhau = edtMatKhau.getText().toString();

                if (tenDangNhap.isEmpty()) {
                    edtTenDangNhap.setError("Vui lòng nhập tên đăng nhập của bạn");
                    edtTenDangNhap.requestFocus();
                } else if (matKhau.isEmpty()) {
                    edtMatKhau.setError("Vui lòng nhập mật khẩu");
                    edtMatKhau.requestFocus();
                } else if (tenDangNhap.isEmpty() && matKhau.isEmpty()) {
                    Toast.makeText(DangNhap.this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                } else if (!(tenDangNhap.isEmpty() && !matKhau.isEmpty())) {
                    try {
                        db.collection("TaiKhoanAdmin").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        String username = edtTenDangNhap.getText().toString().trim();
                                        String pass = edtMatKhau.getText().toString().trim();
                                        if (doc.getString("tenTaiKhoan").equalsIgnoreCase(username) && doc.getString("matKhau").equalsIgnoreCase(pass)) {
                                            loadingDialog.startLoadingDialog();
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    loadingDialog.dismissDialog();
                                                    Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                                    Intent intoMenu = new Intent(DangNhap.this, Menu.class);
                                                    startActivity(intoMenu);
                                                }

                                            }, 1000);

                                        } else {
                                            Toast.makeText(DangNhap.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    Toast.makeText(DangNhap.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (Exception e) {
                        Log.d(TAG, e.toString());
                    }
                } else {
                    Toast.makeText(DangNhap.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            showCustomDialog();
        }


    }

    private void setControl() {
        btnDangNhap = findViewById(R.id.btnDangNhap);
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtMatKhau = findViewById(R.id.edtMatKhau);
    }

    public static synchronized DangNhap getInstance() {
        return connectInstance;
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DangNhap.this);
        builder.setTitle("Thông báo kết nối mạng");
        builder.setMessage("Xin hãy kết nối internet để sử dụng").setCancelable(false).setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), DangNhap.class));
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}