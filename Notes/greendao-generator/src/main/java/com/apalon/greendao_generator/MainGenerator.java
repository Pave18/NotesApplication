package com.apalon.greendao_generator;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MainGenerator {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.apalon.notes.ff");
        schema.enableKeepSectionsByDefault();

        addNoteEntities(schema);

        try {
            new DaoGenerator().generateAll(schema, "../app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Entity addNoteEntities(final Schema schema) {
        Entity note = schema.addEntity("Note");
        note.addIdProperty().primaryKey().autoincrement();
        note.addStringProperty("title");
        note.addStringProperty("mainText");
        note.addIntProperty("background");
        note.addBooleanProperty("created");
        note.addDateProperty("date");
        return note;
    }
}
