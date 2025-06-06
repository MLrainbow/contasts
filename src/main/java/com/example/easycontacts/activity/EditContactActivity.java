package com.example.easycontacts.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.easycontacts.R;
import com.example.easycontacts.db.AppDatabase;
import com.example.easycontacts.model.Contact;

public class EditContactActivity extends AppCompatActivity {
    private EditText etName, etPhone, etEmail, etBirthday, etAddress, etNotes;
    private Contact contactToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("编辑联系人");
            toolbar.setTitleTextColor(Color.WHITE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 显示返回按钮
            getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_revert); // 返回图标
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etBirthday = findViewById(R.id.et_birthday);
        etAddress = findViewById(R.id.et_address);
        etNotes = findViewById(R.id.et_notes);
        Button btnSave = findViewById(R.id.btn_save);

        // 接收联系人对象（编辑时传递）
        contactToEdit = (Contact) getIntent().getSerializableExtra("contact");
        if (contactToEdit != null) {
            // 回显数据
            etName.setText(contactToEdit.name);
            etPhone.setText(contactToEdit.phone);
            etEmail.setText(contactToEdit.email);
            etBirthday.setText(contactToEdit.birthday);
            etAddress.setText(contactToEdit.address);
            etNotes.setText(contactToEdit.notes);
        }

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String birthday = etBirthday.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String notes = etNotes.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
                Toast.makeText(this, "姓名和电话不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            if (contactToEdit == null) {
                contactToEdit = new Contact();
            }

            contactToEdit.name = name;
            contactToEdit.phone = phone;
            contactToEdit.email = email;
            contactToEdit.birthday = birthday;
            contactToEdit.address = address;
            contactToEdit.notes = notes;

            // 使用子线程保存数据
            new Thread(() -> {
                AppDatabase db = AppDatabase.getDatabase(this);
                if (contactToEdit.id > 0) {
                    db.contactDao().update(contactToEdit);
                } else {
                    db.contactDao().insert(contactToEdit);
                }

                // 保存成功后回到主线程提示并结束页面，回传 RESULT_OK
                runOnUiThread(() -> {
                    Toast.makeText(EditContactActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                });
            }).start();
        });
    }
}
