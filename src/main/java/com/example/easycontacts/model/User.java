package com.example.easycontacts.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    public String phone;  // 主键，手机号，唯一登录账号

    @NonNull
    public String username; // 用户名，中文名

    @NonNull
    public String password; // 密码


}
