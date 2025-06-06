package com.example.easycontacts.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easycontacts.MainActivity;
import com.example.easycontacts.R;
import com.example.easycontacts.db.AppDatabase;
import com.example.easycontacts.model.User;

public class LoginActivity extends AppCompatActivity {
    private EditText etPhone, etPassword;
    private CheckBox cbRemember;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        cbRemember = findViewById(R.id.cb_remember);
        Button btnLogin = findViewById(R.id.btn_login);
        TextView tvRegister = findViewById(R.id.tv_register);

        sp = getSharedPreferences("userInfo", MODE_PRIVATE);

        // 自动填充
        if (sp.getBoolean("remember", false)) {
            etPhone.setText(sp.getString("phone", ""));
            etPassword.setText(sp.getString("password", ""));
            cbRemember.setChecked(true);
        }

        btnLogin.setOnClickListener(v -> {
            String phone = etPhone.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (phone.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "请输入手机号和密码", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!phone.matches("^1[3-9]\\d{9}$")) {
                Toast.makeText(this, "请输入有效的手机号", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                User dbUser = AppDatabase.getDatabase(this).userDao().getUserByPhone(phone);
                runOnUiThread(() -> {
                    if (dbUser != null && dbUser.password.equals(pass)) {
                        if (cbRemember.isChecked()) {
                            sp.edit()
                                    .putString("phone", phone)
                                    .putString("password", pass)
                                    .putBoolean("remember", true)
                                    .apply();
                        } else {
                            sp.edit().clear().apply();
                        }
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "手机号或密码错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });

        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
