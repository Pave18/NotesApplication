package com.apalon.notes.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.apalon.notes.R;
import com.apalon.notes.adapters.NoteAdapter;
import com.apalon.notes.bal.ManageNotes;
import com.apalon.notes.dao.Note;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NoteAdapter.ItemClickListener {
    ManageNotes manageNotes;
    NoteAdapter noteAdapter;
    RecyclerView rvNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manageNotes = new ManageNotes();

        noteAdapter = new NoteAdapter(manageNotes.getAllNotes(), this);
        noteAdapter.setClickListener(this);

        rvNotes = findViewById(R.id.lv_notes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setAdapter(noteAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityAddNewNote();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(R.string.action_about)
                    .setMessage(R.string.message_about)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //For create
    private void startActivityAddNewNote() {
        Intent intent = new Intent(this, AddNewNote.class);
        intent.putExtra("create", true);
        startActivity(intent);
        noteAdapter.notifyItemInserted(noteAdapter.getItemCount());
    }

    //For update
    private void startActivityAddNewNote(Note note) {
        Intent intent = new Intent(this, AddNewNote.class);
        intent.putExtra("create", false);
        intent.putExtra("noteId", note.getId());
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position, long idNote) {
        showPopupMenu(view, position, idNote);
    }

    @Override
    protected void onResume() {
        super.onResume();

        noteAdapter.notifyDataSetChanged();
    }

    private void showPopupMenu(View v, final int position, final long idNote) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popupmenu_note);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        manageNotes.delNote(idNote);
                        return true;
                    case R.id.action_update:
                        startActivityAddNewNote(manageNotes.getNoteById(idNote));

                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }


}
