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

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private ItemClickListener mClickListener;

    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout linearLayoutNoteItem;
        TextView textViewTitle;
        TextView textViewMainText;
        TextView textViewCreated;
        TextView textViewDate;

        NoteViewHolder(View itemView) {
            super(itemView);

            linearLayoutNoteItem = itemView.findViewById(R.id.linearLayout_item_note);
            textViewTitle = itemView.findViewById(R.id.tv_title_item_note);
            textViewMainText = itemView.findViewById(R.id.tv_main_text_item_note);
            textViewCreated = itemView.findViewById(R.id.tv_created_item_note);
            textViewDate = itemView.findViewById(R.id.tv_date_item_note);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (mClickListener != null)
                mClickListener.onItemClick(v, getAdapterPosition(), notes.get(getAdapterPosition()).getId());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position, long idNote);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    private List<Note> notes;
    private Context context;

    public NoteAdapter(List<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note tempNote = notes.get(position);

        holder.linearLayoutNoteItem.setBackgroundColor(tempNote.getBackground());
        holder.textViewTitle.setText(tempNote.getTitle());
        holder.textViewMainText.setText(tempNote.getMainText());

        String tempString;
        if (tempNote.getCreated()) {
            tempString = context.getResources().getString(R.string.title_created);
        } else {
            tempString = context.getResources().getString(R.string.title_updated);
        }
        holder.textViewCreated.setText(tempString);

        String tempDate = DateFormat.getTimeInstance(DateFormat.SHORT).format(tempNote.getDate()) + " "
                + DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(tempNote.getDate());

        holder.textViewDate.setText(tempDate);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
