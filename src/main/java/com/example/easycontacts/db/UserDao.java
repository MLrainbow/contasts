package com.example.easycontacts.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.easycontacts.model.User;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    User login(String username, String password);

    @Query("SELECT * FROM User WHERE username = :username")
    User getUser(String username);

    @Query("SELECT * FROM User WHERE phone = :phone LIMIT 1")
    User getUserByPhone(String phone);

}
