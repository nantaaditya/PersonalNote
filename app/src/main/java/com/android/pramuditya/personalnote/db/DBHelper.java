package com.android.pramuditya.personalnote.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.pramuditya.personalnote.model.Note;
import com.android.pramuditya.personalnote.model.Tag;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by User on 21/11/2017.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {
    public static final String DB_NAME = "personal_note.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context){
        super(context,DB_NAME, null, DB_VERSION);
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Tag.class);
            TableUtils.createTable(connectionSource, Note.class);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {

    }

    public <T>List getAll(Class clazz) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);
        return dao.queryForAll();
    }

    public  <T>T getById(Class clazz, Object aId) throws SQLException {
        Dao<T, Object> dao = getDao(clazz);
        return dao.queryForId(aId);
    }

    public <T> Dao.CreateOrUpdateStatus createOrUpdate(T obj) throws SQLException {
        Dao<T, ?> dao = (Dao<T, ?>) getDao(obj.getClass());
        return dao.createOrUpdate(obj);
    }

    public  <T> int deleteById(Class clazz, Object aId) throws SQLException {
        Dao<T, Object> dao = getDao(clazz);
        return dao.deleteById(aId);
    }

    public <T> void deleteAll(Class clazz) throws SQLException{
        Dao<T, Object> dao = getDao(clazz);
        dao.deleteBuilder().delete();
    }
    public Tag findTagByName(Class clazz, String name)
        throws SQLException{
        QueryBuilder<Tag, String> queryBuilder = getDao(clazz).queryBuilder();
        Where<Tag, String> where = queryBuilder.where();
        where.eq("name", name);
        return where.queryForFirst();
    }
}
