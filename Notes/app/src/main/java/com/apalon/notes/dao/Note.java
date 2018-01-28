package com.apalon.notes.dao;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "NOTE".
 */
@Entity
public class Note {

    @Id(autoincrement = true)
    private Long id;
    private String title;
    private String mainText;
    private Integer background;
    private Boolean createOrUpdate;
    private java.util.Date date;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public Note() {
    }

    public Note(Long id) {
        this.id = id;
    }

    @Generated
    public Note(Long id, String title, String mainText, Integer background, Boolean createOrUpdate, java.util.Date date) {
        this.id = id;
        this.title = title;
        this.mainText = mainText;
        this.background = background;
        this.createOrUpdate = createOrUpdate;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public Integer getBackground() {
        return background;
    }

    public void setBackground(Integer background) {
        this.background = background;
    }

    public Boolean getCreateOrUpdate() {
        return createOrUpdate;
    }

    public void setCreateOrUpdate(Boolean createOrUpdate) {
        this.createOrUpdate = createOrUpdate;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
