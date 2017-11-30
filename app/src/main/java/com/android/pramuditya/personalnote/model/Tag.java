package com.android.pramuditya.personalnote.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by User on 21/11/2017.
 */

@DatabaseTable(tableName = "tag")
public class Tag implements Serializable{
    @DatabaseField(dataType = DataType.UUID, generatedId = true, useGetSet = true)
    private UUID id;
    @DatabaseField(unique = true, useGetSet = true)
    private String name;
    @ForeignCollectionField
    private ForeignCollection<Note> note;


    public Tag(){}
    public Tag(String name){
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public ForeignCollection<Note> getNote(){
        return note;
    }
}
