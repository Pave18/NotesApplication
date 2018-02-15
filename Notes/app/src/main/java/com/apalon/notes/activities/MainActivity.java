package com.apalon.notes.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.apalon.notes.R;
import com.apalon.notes.adapters.NoteAdapter;
import com.apalon.notes.adapters.OnRecycleViewItemClickListener;
import com.apalon.notes.bal.ManageNotes;
import com.apalon.notes.dao.Note;
import com.apalon.notes.dialogs.AboutDialog;

public class MainActivity extends AppCompatActivity {

    final int REQUEST_FOR_CREATE = 1;
    final int REQUEST_FOR_UPDATE = 2;

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


        noteAdapter = new NoteAdapter(manageNotes.getAllNotes());
        noteAdapter.setOnItemClickListener(new OnRecycleViewItemClickListener<Note>() {
            @Override
            public void onItemClick(View view, Note note) {
                showPopupMenu(view, note);
            }
        });


        rvNotes = findViewById(R.id.lv_notes);
        rvNotes.setAdapter(noteAdapter);
        rvNotes.setLayoutManager(new GridLayoutManager(this, 2));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityAddNewNote();
            }
        });
    }

    // This is necessary to add or update an element to the adapter
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            long noteId = data.getLongExtra("noteId", -1);

            switch (requestCode) {
                case REQUEST_FOR_CREATE:
                    noteAdapter.add(manageNotes.getNoteById(noteId), noteAdapter.getItemCount());
                    break;
                case REQUEST_FOR_UPDATE:
                    noteAdapter.update(manageNotes.getNoteById(noteId));
                    break;
            }
        }
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
            AboutDialog aboutDialog = new AboutDialog();
            aboutDialog.show(getFragmentManager(), "AboutDialog");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //For create
    private void startActivityAddNewNote() {
        Intent intent = new Intent(this, AddNewNoteActivity.class);
        intent.putExtra("create", true);
        startActivityForResult(intent, REQUEST_FOR_CREATE);
    }

    //For update
    private void startActivityAddNewNote(Note note) {
        Intent intent = new Intent(this, AddNewNoteActivity.class);
        intent.putExtra("create", false);
        intent.putExtra("noteId", note.getId());
        startActivityForResult(intent, REQUEST_FOR_UPDATE);
    }

    private void showPopupMenu(View v, final Note note) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popupmenu_note);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        manageNotes.delNote(note);
                        noteAdapter.remove(note);
                        return true;
                    case R.id.action_update:
                        startActivityAddNewNote(note);
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }
}
