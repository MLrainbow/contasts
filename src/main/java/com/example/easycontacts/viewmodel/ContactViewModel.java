package com.example.easycontacts.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.easycontacts.db.AppDatabase;
import com.example.easycontacts.db.ContactDao;
import com.example.easycontacts.model.Contact;
import java.util.List;
import java.util.concurrent.Executors;

public class ContactViewModel extends AndroidViewModel {
    private final ContactDao contactDao;
    private final LiveData<List<Contact>> allContacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        contactDao = db.contactDao();
        allContacts = contactDao.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public void insert(Contact contact) {
        Executors.newSingleThreadExecutor().execute(() -> contactDao.insert(contact));
    }

    public void update(Contact contact) {
        Executors.newSingleThreadExecutor().execute(() -> contactDao.update(contact));
    }

    public void delete(Contact contact) {
        Executors.newSingleThreadExecutor().execute(() -> contactDao.delete(contact));
    }

    public void deleteAll() {
        Executors.newSingleThreadExecutor().execute(contactDao::deleteAll);
    }
}
