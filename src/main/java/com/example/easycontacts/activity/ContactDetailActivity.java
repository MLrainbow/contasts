package com.example.easycontacts.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.easycontacts.R;
import com.example.easycontacts.model.Contact;
import com.example.easycontacts.viewmodel.ContactDetailViewModel;

public class ContactDetailActivity extends AppCompatActivity {

    private TextView tvName, tvPhone, tvEmail, tvBirthday, tvAddress, tvNote;
    private ContactDetailViewModel viewModel;

    private int contactId = -1;

    // 使用 ActivityResultLauncher 启动编辑页并处理回调
    private final ActivityResultLauncher<Intent> editContactLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // 编辑完成，重新加载联系人数据
                    if (contactId != -1) {
                        viewModel.loadContactById(contactId);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("通讯录详情");
            toolbar.setTitleTextColor(Color.WHITE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 显示返回按钮
            getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_revert); // 返回图标
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        initViews();

        contactId = getIntent().getIntExtra("contact_id", -1);
        if (contactId == -1) {
            finish();
            return;
        }

        viewModel = new ViewModelProvider(this).get(ContactDetailViewModel.class);

        // 监听联系人数据变化，刷新UI
        viewModel.getContact().observe(this, contact -> {
            if (contact != null) {
                showContactDetails(contact);
            }
        });

        // 初始加载联系人数据
        viewModel.loadContactById(contactId);
    }

    private void initViews() {
        tvName = findViewById(R.id.tv_detail_name);
        tvPhone = findViewById(R.id.tv_detail_phone);
        tvEmail = findViewById(R.id.tv_detail_email);
        tvBirthday = findViewById(R.id.tv_detail_birthday);
        tvAddress = findViewById(R.id.tv_detail_address);
        tvNote = findViewById(R.id.tv_detail_note);
    }

    private void showContactDetails(Contact contact) {
        tvName.setText(contact.name);
        tvPhone.setText("📞 电话：" + contact.phone);
        tvEmail.setText("📧 邮箱：" + contact.email);
        tvBirthday.setText("🎂 生日：" + contact.birthday);
        tvAddress.setText("🏠 地址：" + contact.address);
        tvNote.setText("📝 备注：" + contact.notes);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.menu_edit) {
            Contact contact = viewModel.getContact().getValue();
            if (contact != null) {
                Intent intent = new Intent(this, EditContactActivity.class);
                intent.putExtra("contact", contact);
                // 使用 ActivityResultLauncher 启动编辑页
                editContactLauncher.launch(intent);
            } else {
                Toast.makeText(this, "无法编辑，未加载联系人信息", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
