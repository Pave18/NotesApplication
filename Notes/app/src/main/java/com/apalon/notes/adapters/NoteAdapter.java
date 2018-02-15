package com.apalon.notes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apalon.notes.R;
import com.apalon.notes.dao.Note;

import java.text.DateFormat;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> implements View.OnClickListener {

    private List<Note> notes;
    private OnRecycleViewItemClickListener<Note> itemClickListener;


    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        v.setOnClickListener(this);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        final Note item = notes.get(position);

        holder.itemView.setTag(item);
        holder.linearLayoutNoteItem.setBackgroundColor(item.getBackground());
        holder.textViewTitle.setText(item.getTitle());
        holder.textViewMainText.setText(item.getMainText());

        String tempString;
        if (item.getCreated()) {
            tempString = holder.context.getResources().getString(R.string.title_created);
        } else {
            tempString = holder.context.getResources().getString(R.string.title_updated);
        }
        holder.textViewCreated.setText(tempString);

        String tempDate = item.getDateToString();

        holder.textViewDate.setText(tempDate);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            Note model = (Note) v.getTag();
            itemClickListener.onItemClick(v, model);
        }
    }

    public void add(Note item, int position) {
        notes.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Note item) {
        int position = notes.indexOf(item);
        notes.remove(position);
        notifyItemRemoved(position);
    }

    public void update(Note note) {
        notifyItemChanged(findItemPosition(note));
    }

    private int findItemPosition(Note note) {
        int count = 0;
        for (; count < getItemCount(); ++count) {
            if (notes.get(count) == note) {

                break;
            }
        }

        return count;
    }


    public void setOnItemClickListener(OnRecycleViewItemClickListener<Note> listener) {
        this.itemClickListener = listener;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        Context context;

        LinearLayout linearLayoutNoteItem;
        TextView textViewTitle;
        TextView textViewMainText;
        TextView textViewCreated;
        TextView textViewDate;

        NoteViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();

            linearLayoutNoteItem = itemView.findViewById(R.id.linearLayout_item_note);
            textViewTitle = itemView.findViewById(R.id.tv_title_item_note);
            textViewMainText = itemView.findViewById(R.id.tv_main_text_item_note);
            textViewCreated = itemView.findViewById(R.id.tv_created_item_note);
            textViewDate = itemView.findViewById(R.id.tv_date_item_note);
        }
    }
}
