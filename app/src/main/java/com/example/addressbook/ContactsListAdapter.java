package com.example.addressbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactViewHolder> {

    private Context mContext;
    private List<Contact> contacts;

    public ContactsListAdapter(Context context, List<Contact> list){

        this.mContext = context;
        this.contacts = list;

    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.contact_item_layout, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        Contact contact = contacts.get(position);

        if(contact != null){

            holder.textName.setText(contact.getName());
            holder.textPhoneNumber.setText(contact.getPhoneNumber());
        }
    }

    @Override
    public int getItemCount() {
        return contacts != null? contacts.size() : 0;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout phoneNameLayout;
        private TextView textName, textPhoneNumber;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            phoneNameLayout = itemView.findViewById(R.id.namePhoneLayout);
            textName = itemView.findViewById(R.id.textName);
            textPhoneNumber = itemView.findViewById(R.id.textPhoneNumber);
        }
    }
}
