package org.learningequality.androidapp.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aron Fyodor Asor on 10/11/14.
 */
public class ContentLogDataSource {

    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;

    public ContentLogDataSource(Context context) {

    }

    public void open() throws SQLiteException {
        db = helper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public ContentLog createContentLog() {
        return null;
    }

    private class ContentLog {}
}
