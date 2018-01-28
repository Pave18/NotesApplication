package com.apalon.notes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apalon.notes.R;
import com.apalon.notes.dao.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NoteAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Note> noteArrayList;

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        ctx = context;
        noteArrayList = notes;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return noteArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return noteArrayList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_note, parent, false);
        }

        Note tempNote = getNote(position);

        ((LinearLayout) view.findViewById(R.id.linearLayout_item_note))
                .setBackgroundColor(tempNote.getBackground());
        ((TextView) view.findViewById(R.id.tv_title_item_note)).setText(tempNote.getTitle());
        ((TextView) view.findViewById(R.id.tv_main_text_item_note)).setText(tempNote.getMainText());
        String tempString;
        if (tempNote.getCreateOrUpdate()) {
            tempString = ctx.getString(R.string.title_created);
        } else {
            tempString = ctx.getString(R.string.title_updated);
        }
        ((TextView) view.findViewById(R.id.tv_create_of_update_item_note)).setText(tempString);

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String formattedDate = df.format(tempNote.getDate());

        ((TextView) view.findViewById(R.id.tv_date_item_note))
                .setText(formattedDate);

        return view;
    }

    Note getNote(int position) {
        return (Note) getItem(position);
    }
}
