package com.example.easycontacts.activity;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.easycontacts.R;
import com.example.easycontacts.db.AppDatabase;
import com.example.easycontacts.model.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPhone, etPassword, etConfirmPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.et_reg_username);
        etPhone = findViewById(R.id.et_reg_phone);
        etPassword = findViewById(R.id.et_reg_password);
        etConfirmPassword = findViewById(R.id.et_reg_confirm_password);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            // 非空校验
            if (username.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "请填写所有字段", Toast.LENGTH_SHORT).show();
                return;
            }

            // 用户名只能中文，长度2~10
            if (!username.matches("^[\\u4e00-\\u9fa5]{2,10}$")) {
                Toast.makeText(this, "用户名必须是2~10个中文字符", Toast.LENGTH_SHORT).show();
                return;
            }

            // 手机号格式校验（中国手机号示例）
            if (!phone.matches("^1[3-9]\\d{9}$")) {
                Toast.makeText(this, "请输入有效的手机号", Toast.LENGTH_SHORT).show();
                return;
            }

            // 密码长度校验
            if (password.length() < 6) {
                Toast.makeText(this, "密码长度不能少于6位", Toast.LENGTH_SHORT).show();
                return;
            }

            // 密码确认
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                return;
            }

            // 数据库操作放后台线程
            new Thread(() -> {
                User existingUser = AppDatabase.getDatabase(this).userDao().getUserByPhone(phone);
                if (existingUser != null) {
                    runOnUiThread(() -> Toast.makeText(this, "手机号已注册", Toast.LENGTH_SHORT).show());
                    return;
                }

                User user = new User();
                user.username = username;
                user.phone = phone;
                user.password = password;
                AppDatabase.getDatabase(this).userDao().insert(user);

                runOnUiThread(() -> {
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        });
    }
}
