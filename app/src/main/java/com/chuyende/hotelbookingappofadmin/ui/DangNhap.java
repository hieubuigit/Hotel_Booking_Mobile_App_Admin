package com.chuyende.hotelbookingappofadmin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chuyende.hotelbookingappofadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

public class DangNhap extends AppCompatActivity {

    Button btnDangNhap;
    EditText edtTenDangNhap;
    EditText edtMatKhau;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        setControl();
        setEvent();
    }

    private void setEvent() {
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
                db.collection("TaiKhoanAdmin").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String username = edtTenDangNhap.getText().toString().trim();
                                String pass = edtMatKhau.getText().toString().trim();
                                if (doc.getString("tenTaiKhoan").equalsIgnoreCase(username) && doc.getString("matKhau").equalsIgnoreCase(pass)) {
                                    Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    Intent intoMenu = new Intent(DangNhap.this, Menu.class);
                                    startActivity(intoMenu);
                                }
                                else {
                                    Toast.makeText(DangNhap.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else {
                            Toast.makeText(DangNhap.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(DangNhap.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setControl() {
        btnDangNhap = findViewById(R.id.btnDangNhap);
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtMatKhau = findViewById(R.id.edtMatKhau);
    }


}