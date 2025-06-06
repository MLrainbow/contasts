package com.example.easycontacts.db;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.easycontacts.model.Contact;
import java.util.List;

@Dao
public interface ContactDao {
    @Insert
    void insert(Contact contact);

    @Insert
    void insertAll(Contact... contacts);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("DELETE FROM Contact")
    void deleteAll();

    @Query("SELECT * FROM Contact ORDER BY name ASC")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * FROM Contact WHERE id = :contactId LIMIT 1")
    Contact getContactById(int contactId);

}
