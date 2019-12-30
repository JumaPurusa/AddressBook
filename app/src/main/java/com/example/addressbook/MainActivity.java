package com.example.addressbook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Contact> contacts;
    private RecyclerView recyclerView;
    private ContactsListAdapter contactsListAdapter;

    int contactCount;

    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contacts = new ArrayList<>();
        contacts = readContacts();

        contactsListAdapter = new ContactsListAdapter(MainActivity.this, contacts);
        actionBar.setTitle(contacts.size() + " Contacts");

        if(recyclerView != null)
            recyclerView.setAdapter(contactsListAdapter);

    }

    private List<Contact> readContacts(){

        String phoneNumber = null;

        contactCount = 0;

        ContentResolver cr = getContentResolver();

        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        ,null, null, null, null);

        if(cursor.getCount() > 0){

                while(cursor.moveToNext()){

                    contact = new Contact();

                    String contact_id = cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts._ID)
                    );

                    if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER))) > 0){

                        phoneNumber = cursor.getString(
                                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        );
                    }


                    if(phoneNumber.length() >= 9 && isValidPhoneNumber(phoneNumber)){

                        if(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                         == null || cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                        .trim().equalsIgnoreCase("")){

                            contact.setName(phoneNumber);
                        }else{

                            contact.setName(cursor.getString(
                                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                            ));
                        }

                        contact.setPhoneNumber(String.valueOf(phoneNumber));
                        contactCount++;

                        contacts.add(contact);
                    }


                }


        }

        cursor.close();
        return contacts;
    }

    public static final boolean isValidPhoneNumber(CharSequence target){

        if(target == null || target.toString().isEmpty()){
            return false;
        }else{

            return Patterns.PHONE.matcher(target).matches();
        }
    }

}
