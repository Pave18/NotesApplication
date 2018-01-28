package com.apalon.notes.bal;

import com.apalon.notes.AppController;
import com.apalon.notes.dao.DaoSession;
import com.apalon.notes.dao.Note;
import com.apalon.notes.dao.NoteDao;

import java.util.Date;
import java.util.List;

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

    public void addNote(String title, String mainText, boolean create, Date date, int background) {
        Note tempNote = new Note();
        tempNote.setTitle(title);
        tempNote.setMainText(mainText);
        tempNote.setCreateOrUpdate(create);
        tempNote.setDate(date);
        tempNote.setBackground(background);
        noteDao.insert(tempNote);
    }

    public void updateNote(Note note, String title, String mainText, Boolean create, Date date,
                           int background) {
        note.setTitle(title);
        note.setMainText(mainText);
        note.setCreateOrUpdate(create);
        note.setDate(date);
        note.setBackground(background);
        noteDao.update(note);
    }

    public void delNote(Note note) {
        noteDao.delete(note);
    }
}
