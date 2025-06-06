package com.example.easycontacts.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Contact implements Serializable {

    @PrimaryKey(autoGenerate = true)  // 主键，自增
    public int id;

    @ColumnInfo(name = "name")  // 姓名
    public String name;

    @ColumnInfo(name = "phone")  // 电话
    public String phone;

    @ColumnInfo(name = "email")  // 邮箱
    public String email;

    @ColumnInfo(name = "birthday")  // 生日
    public String birthday;

    @ColumnInfo(name = "address")  // 地址
    public String address;

    @ColumnInfo(name = "notes")  // 备注
    public String notes;

    public Contact() {
        super();
    }

    @Ignore  // Room 忽略此构造函数
    public Contact(String name, String phone, String email, String birthday, String address, String notes) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;
        this.address = address;
        this.notes = notes;
    }
}
