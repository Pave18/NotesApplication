package com.apalon.notes.bal;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.EditText;

import com.apalon.notes.AppController;
import com.apalon.notes.dao.DaoSession;
import com.apalon.notes.dao.Note;
import com.apalon.notes.dao.NoteDao;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ManageNotes {

    private DaoSession daoSession;
    private NoteDao noteDao;

    public ManageNotes() {
        daoSession = AppController.getDaoSession();

        noteDao = daoSession.getNoteDao();

        getAllNotes();
    }

    public List<Note> getAllNotes() {
        return noteDao.loadAll();
    }

    public void addNote(EditText title, EditText mainText, boolean created, Date date, int background) {
        Note tempNote = new Note();
        tempNote.setTitle(title.getText().toString());
        tempNote.setMainText(mainText.getText().toString());
        tempNote.setCreated(created);
        tempNote.setDate(date);
        tempNote.setBackground(background);
        noteDao.insert(tempNote);
    }

    public void updateNote(Note note,
                           EditText title, EditText mainText, Boolean created, Date date, int background) {
        note.setTitle(title.getText().toString());
        note.setMainText(mainText.getText().toString());
        note.setCreated(created);
        note.setDate(date);
        note.setBackground(background);
        noteDao.update(note);
    }

    public void delNote(Note note) {
        noteDao.delete(note);
    }

    public void delNote(long id) {
        noteDao.deleteByKey(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Note getNoteById(long id) {

        List<Note> notesList = getAllNotes();
        Note note = new Note();

        for (Note n : notesList) {
            if (Objects.equals(n.getId(), id)) {
                note = n;
                break;
            }
        }

        return note;
    }
}
