package com.example.easycontacts.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.easycontacts.db.AppDatabase;
import com.example.easycontacts.db.ContactDao;
import com.example.easycontacts.model.Contact;

import java.util.concurrent.Executors;

public class ContactDetailViewModel extends AndroidViewModel {

    private final ContactDao contactDao;
    private final MutableLiveData<Contact> contactLiveData = new MutableLiveData<>();

    public ContactDetailViewModel(@NonNull Application application) {
        super(application);
        contactDao = AppDatabase.getDatabase(application).contactDao();
    }

    public LiveData<Contact> getContact() {
        return contactLiveData;
    }

    public void loadContactById(int contactId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            Contact contact = contactDao.getContactById(contactId);
            contactLiveData.postValue(contact);
        });
    }

    public void deleteContact(Contact contact) {
        Executors.newSingleThreadExecutor().execute(() -> {
            contactDao.delete(contact);
        });
    }

    public void updateContact(Contact contact) {
        Executors.newSingleThreadExecutor().execute(() -> {
            contactDao.update(contact);
        });
    }
}
