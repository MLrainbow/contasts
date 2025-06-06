package com.example.easycontacts.data;

import com.example.easycontacts.model.Contact;

public class ContactItem implements ListItem {
    private Contact contact;

    public ContactItem(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    @Override
    public int getType() {
        return TYPE_CONTACT;
    }
}
