package com.example.martin.gerelefrigo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 30/03/2016.
 */
public class StockageStorageServiceImpl implements StorageService {

    private final StockageOpenHelper stockageOpenHelper;

    public StockageStorageServiceImpl(Context context) {
        stockageOpenHelper = new StockageOpenHelper(context);
    }

    @Override
    public List<String> store(Context context, List<String> articles) {
        clear(context);
        SQLiteDatabase db = null;
        try {
            db = stockageOpenHelper.getWritableDatabase();
            ContentValues insertValues = null;
            for (String article : articles) {
                insertValues = new ContentValues();
                insertValues.put(StockageOpenHelper.ARTICLE_COL_NAME, article);
                db.insert(StockageOpenHelper.ARTICLE_TABLE_NAME, null, insertValues);
            }
            return restore(context);
        } finally {
            closeDB(db);
        }
    }

    @Override
    public List<String> restore(Context context) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = stockageOpenHelper.getReadableDatabase();
            cursor = db.query(stockageOpenHelper.ARTICLE_TABLE_NAME, new String[]{StockageOpenHelper.ARTICLE_COL_NAME}, null, null, null, null, StockageOpenHelper.ARTICLE_COL_NAME);
            List<String> elements = new ArrayList<>();
            while (cursor.moveToNext()) {
                elements.add(cursor.getString(0));
            }
            cursor.close();
            return elements;
        } finally {
            closeDB(db, cursor);
        }
    }

    @Override
    public List<String> clear(Context context) {
        SQLiteDatabase db = null;
        try {
            db = stockageOpenHelper.getWritableDatabase();
            db.delete(ProduitOpenHelper.ARTICLE_TABLE_NAME, null, null);
            return restore(context);
        } finally {
            closeDB(db);
        }
    }

    @Override
    public void add(Context context, String article) {
        SQLiteDatabase db = null;
        try {
            db = stockageOpenHelper.getWritableDatabase();
            ContentValues insertValues = new ContentValues();
            insertValues.put(StockageOpenHelper.ARTICLE_COL_NAME, article);
            long rowId = db.insert(StockageOpenHelper.ARTICLE_TABLE_NAME, null, insertValues);
        } finally {
            closeDB(db);
        }
    }

    private void closeDB(Closeable... elementsToClose) {
        for (Closeable elementToClose : elementsToClose) {
            if (null != elementToClose) {
                try {
                    elementToClose.close();
                } catch (IOException e) {
                    Log.e("maListe", "Impossible to close element ", e);
                }
            }
        }
    }
}
