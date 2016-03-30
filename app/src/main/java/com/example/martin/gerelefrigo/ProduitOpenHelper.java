package com.example.martin.gerelefrigo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Florent on 30/03/2016.
 */
public class ProduitOpenHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "ARTICLE_DB";

    public static final int DATABASE_VERSION = 1;

    public static final String ARTICLE_TABLE_NAME = "articles";

    public static final String ARTICLE_COL_NAME = "name";

    private static final String TABLES_CREATE = "CREATE TABLE " + ARTICLE_TABLE_NAME +

            " (" + ARTICLE_COL_NAME +" TEXT);";

    public ProduitOpenHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLES_CREATE);

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion) {


        }

    }
}
