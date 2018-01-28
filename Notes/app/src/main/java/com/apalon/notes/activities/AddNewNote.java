package com.apalon.notes.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
import java.util.Objects;


public class AddNewNote extends AppCompatActivity implements View.OnClickListener {

    Boolean createNew;
    Long noteId;

    ManageNotes manageNote;
    Note note;

    ConstraintLayout forBackgroundNote;
    EditText titleNote;
    EditText mainTextNote;
    int background;

    List<ImageView> backgroundColors;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void readIntent(Intent intent) {
        if (intent != null) {
            createNew = intent.getBooleanExtra("create", true);
            noteId = intent.getLongExtra("noteId", (long) -1);

            List<Note> notesList = manageNote.getAllNotes();

            for (Note n : notesList) {
                if (Objects.equals(n.getId(), noteId)) {
                    note = n;
                    break;
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        manageNote = new ManageNotes();
        note = new Note();

        readIntent(getIntent());

        background = getResources().getColor(R.color.transparent);

        forBackgroundNote = findViewById(R.id.constraintLayout_note);

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

        backgroundColors = new ArrayList<>();
        backgroundColors.add((ImageView) findViewById(R.id.circle_default));
        backgroundColors.add((ImageView) findViewById(R.id.circle_blue));
        backgroundColors.add((ImageView) findViewById(R.id.circle_red));
        backgroundColors.add((ImageView) findViewById(R.id.circle_green));
        backgroundColors.add((ImageView) findViewById(R.id.circle_yellow));

        for (ImageView iv : backgroundColors) {
            iv.setOnClickListener(this);
        }

        forUpdate();
    }

    private void forUpdate() {
        if (!createNew || noteId != -1) {
            titleNote.setText(note.getTitle());
            mainTextNote.setText(note.getMainText());
            background = note.getBackground();
            forBackgroundNote.setBackgroundColor(background);
        }
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
            manageNote.addNote(titleNote.getText().toString(), mainTextNote.getText().toString(),
                    createNew, getDateNow(), background);
        } else {
            mess = getString(R.string.title_note_updated);
            manageNote.updateNote(note, titleNote.getText().toString(), mainTextNote.getText().toString(),
                    createNew, getDateNow(), background);
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
                background = getResources().getColor(R.color.transparent);
                forBackgroundNote.setBackgroundResource(R.color.transparent);
                break;
            case R.id.circle_red:
                background = getResources().getColor(R.color.background_red);
                forBackgroundNote.setBackgroundResource(R.color.background_red);
                break;
            case R.id.circle_green:
                background = getResources().getColor(R.color.background_green);
                forBackgroundNote.setBackgroundResource(R.color.background_green);
                break;
            case R.id.circle_blue:
                background = getResources().getColor(R.color.background_blue);
                forBackgroundNote.setBackgroundResource(R.color.background_blue);
                break;
            case R.id.circle_yellow:
                background = getResources().getColor(R.color.background_yellow);
                forBackgroundNote.setBackgroundResource(R.color.background_yellow);
                break;
        }
    }
}
