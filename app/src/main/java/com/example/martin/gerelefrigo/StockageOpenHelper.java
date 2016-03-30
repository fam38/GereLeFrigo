package com.example.martin.gerelefrigo;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Martin on 30/03/2016.
 */
public class StockageOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FRIGO_DB";
    public static final int DATABASE_VERSION = 1;
    public static final String ARTICLE_TABLE_NAME = "Stockage";
    public static final String ARTICLE_COL_NAME = "name";
    private static final String TABLES_CREATE = "CREATE TABLE " + ARTICLE_TABLE_NAME +" (" + ARTICLE_COL_NAME +" TEXT);";

    public StockageOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLES_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            // Vous pouvez ici mettre à jour vos tables existantes, en créer d'autres, insérer des données, etc...
        }
    }
}