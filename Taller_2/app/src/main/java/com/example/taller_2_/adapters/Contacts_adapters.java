package com.example.taller_2_.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.taller_2_.Contacts;
import com.example.taller_2_.R;

public class Contacts_adapters extends CursorAdapter {

    private static final int CONTACT_ID_INDEX = 0;
    private static final int DISPLAY_NAME_INDEX = 1;

    public Contacts_adapters(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.contacts, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView id_contact = view.findViewById(R.id.index);
        TextView name_contact = view.findViewById(R.id.name);

        int idnum = cursor.getInt(CONTACT_ID_INDEX);
        String name = cursor.getString(DISPLAY_NAME_INDEX);

        id_contact.setText(String.valueOf(idnum));
        name_contact.setText(name);

    }
}
