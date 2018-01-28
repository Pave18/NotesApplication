package com.apalon.notes.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.apalon.notes.R;
import com.apalon.notes.adapters.NoteAdapter;
import com.apalon.notes.bal.ManageNotes;
import com.apalon.notes.dao.DaoSession;
import com.apalon.notes.dao.Note;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int CM_DELETE_ID = 1;
    private static final int CM_UPDATE_ID = 2;

    ManageNotes manageNotes;

    ArrayList<Note> noteArrayList;
    NoteAdapter noteAdapter;

    ListView lvNotes;

    private static DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manageNotes = new ManageNotes();

        noteArrayList = new ArrayList<>();
        noteArrayList.addAll(manageNotes.getAllNotes());
        noteAdapter = new NoteAdapter(this, noteArrayList);

        lvNotes = findViewById(R.id.lv_notes);
        lvNotes.setAdapter(noteAdapter);
        registerForContextMenu(lvNotes);

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

    @Override
    public void onResume() {
        super.onResume();

        fetchNoteList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, R.string.title_note_delete);
        menu.add(0, CM_UPDATE_ID, 0, R.string.title_note_updated);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == CM_DELETE_ID) {

            manageNotes.delNote(noteArrayList.get(acmi.position));
            noteArrayList.remove(acmi.position);
            noteAdapter.notifyDataSetChanged();

            return true;
        } else if (item.getItemId() == CM_UPDATE_ID) {

            startActivityAddNewNote(noteArrayList.get(acmi.position));
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void fetchNoteList() {
        noteArrayList.clear();
        noteArrayList.addAll(manageNotes.getAllNotes());
        noteAdapter.notifyDataSetChanged();
    }

    //For create
    private void startActivityAddNewNote() {
        Intent intent = new Intent(this, AddNewNote.class);
        intent.putExtra("create", true);
        startActivity(intent);
    }

    //For update
    private void startActivityAddNewNote(Note note) {
        Intent intent = new Intent(this, AddNewNote.class);
        intent.putExtra("create", false);
        intent.putExtra("noteId", note.getId());
        startActivity(intent);
    }

}
