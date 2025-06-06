package com.example.easycontacts.db;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.example.easycontacts.model.Contact;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class ContactDao_Impl implements ContactDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Contact> __insertAdapterOfContact;

  private final EntityDeleteOrUpdateAdapter<Contact> __deleteAdapterOfContact;

  private final EntityDeleteOrUpdateAdapter<Contact> __updateAdapterOfContact;

  public ContactDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfContact = new EntityInsertAdapter<Contact>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `Contact` (`id`,`name`,`phone`,`email`,`birthday`,`address`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Contact entity) {
        statement.bindLong(1, entity.id);
        if (entity.name == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.name);
        }
        if (entity.phone == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.phone);
        }
        if (entity.email == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.email);
        }
        if (entity.birthday == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.birthday);
        }
        if (entity.address == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.address);
        }
        if (entity.notes == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.notes);
        }
      }
    };
    this.__deleteAdapterOfContact = new EntityDeleteOrUpdateAdapter<Contact>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `Contact` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Contact entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfContact = new EntityDeleteOrUpdateAdapter<Contact>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `Contact` SET `id` = ?,`name` = ?,`phone` = ?,`email` = ?,`birthday` = ?,`address` = ?,`notes` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Contact entity) {
        statement.bindLong(1, entity.id);
        if (entity.name == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.name);
        }
        if (entity.phone == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.phone);
        }
        if (entity.email == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.email);
        }
        if (entity.birthday == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.birthday);
        }
        if (entity.address == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.address);
        }
        if (entity.notes == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.notes);
        }
        statement.bindLong(8, entity.id);
      }
    };
  }

  @Override
  public void insert(final Contact contact) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __insertAdapterOfContact.insert(_connection, contact);
      return null;
    });
  }

  @Override
  public void insertAll(final Contact... contacts) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __insertAdapterOfContact.insert(_connection, contacts);
      return null;
    });
  }

  @Override
  public void delete(final Contact contact) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfContact.handle(_connection, contact);
      return null;
    });
  }

  @Override
  public void update(final Contact contact) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfContact.handle(_connection, contact);
      return null;
    });
  }

  @Override
  public LiveData<List<Contact>> getAllContacts() {
    final String _sql = "SELECT * FROM Contact ORDER BY name ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"Contact"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfPhone = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "phone");
        final int _columnIndexOfEmail = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "email");
        final int _columnIndexOfBirthday = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "birthday");
        final int _columnIndexOfAddress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "address");
        final int _columnIndexOfNotes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notes");
        final List<Contact> _result = new ArrayList<Contact>();
        while (_stmt.step()) {
          final Contact _item;
          _item = new Contact();
          _item.id = (int) (_stmt.getLong(_columnIndexOfId));
          if (_stmt.isNull(_columnIndexOfName)) {
            _item.name = null;
          } else {
            _item.name = _stmt.getText(_columnIndexOfName);
          }
          if (_stmt.isNull(_columnIndexOfPhone)) {
            _item.phone = null;
          } else {
            _item.phone = _stmt.getText(_columnIndexOfPhone);
          }
          if (_stmt.isNull(_columnIndexOfEmail)) {
            _item.email = null;
          } else {
            _item.email = _stmt.getText(_columnIndexOfEmail);
          }
          if (_stmt.isNull(_columnIndexOfBirthday)) {
            _item.birthday = null;
          } else {
            _item.birthday = _stmt.getText(_columnIndexOfBirthday);
          }
          if (_stmt.isNull(_columnIndexOfAddress)) {
            _item.address = null;
          } else {
            _item.address = _stmt.getText(_columnIndexOfAddress);
          }
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _item.notes = null;
          } else {
            _item.notes = _stmt.getText(_columnIndexOfNotes);
          }
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Contact getContactById(final int contactId) {
    final String _sql = "SELECT * FROM Contact WHERE id = ? LIMIT 1";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, contactId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfPhone = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "phone");
        final int _columnIndexOfEmail = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "email");
        final int _columnIndexOfBirthday = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "birthday");
        final int _columnIndexOfAddress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "address");
        final int _columnIndexOfNotes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notes");
        final Contact _result;
        if (_stmt.step()) {
          _result = new Contact();
          _result.id = (int) (_stmt.getLong(_columnIndexOfId));
          if (_stmt.isNull(_columnIndexOfName)) {
            _result.name = null;
          } else {
            _result.name = _stmt.getText(_columnIndexOfName);
          }
          if (_stmt.isNull(_columnIndexOfPhone)) {
            _result.phone = null;
          } else {
            _result.phone = _stmt.getText(_columnIndexOfPhone);
          }
          if (_stmt.isNull(_columnIndexOfEmail)) {
            _result.email = null;
          } else {
            _result.email = _stmt.getText(_columnIndexOfEmail);
          }
          if (_stmt.isNull(_columnIndexOfBirthday)) {
            _result.birthday = null;
          } else {
            _result.birthday = _stmt.getText(_columnIndexOfBirthday);
          }
          if (_stmt.isNull(_columnIndexOfAddress)) {
            _result.address = null;
          } else {
            _result.address = _stmt.getText(_columnIndexOfAddress);
          }
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _result.notes = null;
          } else {
            _result.notes = _stmt.getText(_columnIndexOfNotes);
          }
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void deleteAll() {
    final String _sql = "DELETE FROM Contact";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
