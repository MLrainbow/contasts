package com.example.easycontacts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.*;

import com.example.easycontacts.R;
import com.example.easycontacts.activity.ContactDetailActivity;
import com.example.easycontacts.activity.EditContactActivity;
import com.example.easycontacts.adapter.ContactAdapter;
import com.example.easycontacts.data.ContactItem;
import com.example.easycontacts.data.LetterItem;
import com.example.easycontacts.data.ListItem;
import com.example.easycontacts.model.Contact;
import com.example.easycontacts.viewmodel.ContactViewModel;
import com.example.easycontacts.widget.SideIndexBar;
import com.github.promeg.pinyinhelper.Pinyin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    private ContactViewModel viewModel;
    private ContactAdapter adapter;
    private RecyclerView recyclerView;
    private SideIndexBar sideIndexBar;
    private Map<String, Integer> letterPositionMap = new HashMap<>(); // 字母对应列表位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("通讯录");
            toolbar.setTitleTextColor(Color.WHITE);
        }

        recyclerView = findViewById(R.id.recyclerView);
        sideIndexBar = findViewById(R.id.sideIndexBar);
        FloatingActionButton fab = findViewById(R.id.fab_add);

        adapter = new ContactAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contact contact) {
                Intent intent = new Intent(MainActivity.this, ContactDetailActivity.class);
                intent.putExtra("contact_id", contact.id);
                startActivity(intent);
            }
        });

        adapter.setOnItemLongClickListener(new ContactAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Contact contact, int position) {
                new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle("删除联系人")
                        .setMessage("确定删除联系人 " + contact.name + " 吗？")
                        .setPositiveButton("删除", (dialog, which) -> {
                            viewModel.delete(contact);
                            Toast.makeText(MainActivity.this, "联系人已删除", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });


        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        viewModel.getAllContacts().observe(this, contacts -> {
            List<ListItem> groupedItems = buildGroupedList(contacts);
            adapter.setItems(groupedItems);
            generateLetterPositionsForGroupedList(groupedItems);
        });

        fab.setOnClickListener(v -> startActivity(new Intent(this, EditContactActivity.class)));

        sideIndexBar.setOnLetterChangeListener(new SideIndexBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                Integer pos = letterPositionMap.get(letter);
                if (pos != null && pos >= 0) {
                    ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(pos, 0);
                }
            }

            @Override
            public void onLetterTouchEnd() {
                // 触摸结束，可以做隐藏提示等操作
            }
        });
    }

    /**
     * 根据联系人列表生成带字母头的混合列表
     */
    private List<ListItem> buildGroupedList(List<Contact> contacts) {
        List<ListItem> result = new ArrayList<>();
        if (contacts == null || contacts.isEmpty()) return result;

        // 排序：根据拼音首字母
        contacts.sort((a, b) -> {
            String pinyinA = Pinyin.toPinyin(a.name, "").toUpperCase();
            String pinyinB = Pinyin.toPinyin(b.name, "").toUpperCase();
            return pinyinA.compareTo(pinyinB);
        });

        String lastLetter = "";
        for (Contact c : contacts) {
            if (c.name == null || c.name.isEmpty()) continue;

            // 获取拼音首字母（中文支持）
            String pinyin = Pinyin.toPinyin(c.name, "").toUpperCase(); // 如 ZHANGSAN
            String firstLetter = pinyin.substring(0, 1);

            // 判断是否是新分组
            if (!firstLetter.equals(lastLetter)) {
                lastLetter = firstLetter;
                result.add(new LetterItem(firstLetter));
            }

            result.add(new ContactItem(c));
        }

        return result;
    }


    /**
     * 根据带字母头的列表生成字母定位映射
     */
    private void generateLetterPositionsForGroupedList(List<ListItem> items) {
        letterPositionMap.clear();
        for (int i = 0; i < items.size(); i++) {
            ListItem item = items.get(i);
            if (item.getType() == ListItem.TYPE_HEADER) {
                LetterItem letterItem = (LetterItem) item;
                letterPositionMap.put(letterItem.getLetter(), i);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_clear_all) {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("确认清空")
                    .setMessage("确定要清空所有联系人吗？此操作无法撤销。")
                    .setPositiveButton("确认", (dialog, which) -> {
                        viewModel.deleteAll();
                        Toast.makeText(this, "已清空联系人", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("取消", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
