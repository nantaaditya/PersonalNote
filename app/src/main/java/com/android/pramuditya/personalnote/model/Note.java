package com.android.pramuditya.personalnote.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by User on 21/11/2017.
 */
@DatabaseTable(tableName = "note")
public class Note implements Serializable {
    private static final String TAG_ID = "tag_id";

    @DatabaseField(dataType = DataType.UUID, generatedId = true, useGetSet = true)
    private UUID id;
    @DatabaseField(dataType = DataType.STRING, useGetSet = true)
    private String title;
    @DatabaseField(dataType = DataType.DATE_STRING, useGetSet = true)
    private Date reminder;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = TAG_ID, useGetSet = true)
    private Tag tag;
    @DatabaseField(dataType = DataType.LONG_STRING, useGetSet = true)
    private String note;

    public Note() {
    }

    public Note(String title, Date reminder, Tag tag, String note) {
//        this.id = id;
        this.title = title;
        this.reminder = reminder;
        this.tag = tag;
        this.note = note;
    }

    public static String getTagId() {
        return TAG_ID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReminder() {
        return reminder;
    }

    public void setReminder(Date reminder) {
        this.reminder = reminder;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
