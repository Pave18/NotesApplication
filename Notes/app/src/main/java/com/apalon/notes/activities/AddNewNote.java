package com.apalon.notes.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.apalon.notes.R;
import com.apalon.notes.bal.ManageNotes;
import com.apalon.notes.dao.Note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AddNewNote extends AppCompatActivity implements View.OnClickListener {

    ManageNotes manageNote;

    Boolean createNew;
    Note note;

    EditText titleNote;
    EditText mainTextNote;
    int backgroundColorNote;

    List<ImageView> backgroundColors;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void readIntent(Intent intent) {
        if (intent != null) {
            createNew = intent.getBooleanExtra("create", true);
            note = manageNote.getNoteById(intent.getLongExtra("noteId", -1));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        manageNote = new ManageNotes();

        readIntent(getIntent());

        backgroundColorNote = getResources().getColor(R.color.transparent);

        Toolbar toolbar = findViewById(R.id.toolbar_add_or_update_note);
        if (createNew) {
            toolbar.setTitle(getString(R.string.toolbar_add_note));
        } else {
            toolbar.setTitle(getString(R.string.toolbar_update_note));
        }
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        titleNote = findViewById(R.id.et_title_note);
        mainTextNote = findViewById(R.id.et_main_text_note);

        //
        backgroundColors = new ArrayList<>();
        backgroundColors.add((ImageView) findViewById(R.id.circle_default));
        backgroundColors.add((ImageView) findViewById(R.id.circle_blue));
        backgroundColors.add((ImageView) findViewById(R.id.circle_red));
        backgroundColors.add((ImageView) findViewById(R.id.circle_green));
        backgroundColors.add((ImageView) findViewById(R.id.circle_yellow));
        backgroundColors.add((ImageView) findViewById(R.id.circle_pink));

        for (ImageView iv : backgroundColors) {
            iv.setOnClickListener(this);
        }

        updatingUI();
    }

    private void updatingUI() {
        if (!createNew) {
            titleNote.setText(note.getTitle());
            mainTextNote.setText(note.getMainText());
            backgroundColorNote = note.getBackground();
            updateBackground();
        }
    }

    private void updateBackground() {
        titleNote.setBackgroundColor(backgroundColorNote);
        mainTextNote.setBackgroundColor(backgroundColorNote);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save_note:
                saveItem();
                finish();
                break;
            case android.R.id.home:
                confirmExit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        confirmExit();
    }


    private void confirmExit() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle(R.string.title_exit)
                .setMessage(R.string.message_exit)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveItem();
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    private void saveItem() {
        String mess = "";
        if (createNew) {
            mess = getString(R.string.title_note_insert);
            manageNote.addNote(titleNote, mainTextNote, createNew, getDateNow(), backgroundColorNote);
        } else {
            mess = getString(R.string.title_note_updated);
            manageNote.updateNote(note,
                    titleNote, mainTextNote, createNew, getDateNow(), backgroundColorNote);
        }

        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
    }

    private Date getDateNow() {
        return Calendar.getInstance().getTime();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.circle_default:
                backgroundColorNote = getResources().getColor(R.color.transparent);
                break;
            case R.id.circle_red:
                backgroundColorNote = getResources().getColor(R.color.background_red);
                break;
            case R.id.circle_green:
                backgroundColorNote = getResources().getColor(R.color.background_green);
                break;
            case R.id.circle_blue:
                backgroundColorNote = getResources().getColor(R.color.background_blue);
                break;
            case R.id.circle_yellow:
                backgroundColorNote = getResources().getColor(R.color.background_yellow);
                break;
            case R.id.circle_pink:
                backgroundColorNote = getResources().getColor(R.color.background_pink);
                break;
        }
        updateBackground();
    }
}
