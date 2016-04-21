package com.example.martin.gerelefrigo;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.util.Log;

        import com.example.martin.gerelefrigo.object.Produit;
        import com.example.martin.gerelefrigo.object.ProduitReel;
        import com.example.martin.gerelefrigo.object.Stockage;

        import java.io.Closeable;
        import java.io.IOException;
        import java.text.DateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;

public class StorageServiceImpl implements StorageService {

    private final ProduitOpenHelper produitOpenHelper;

    public StorageServiceImpl(Context context) {
        context.deleteDatabase ("FRIGO_DB") ;
        produitOpenHelper = new ProduitOpenHelper(context);
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

    @Override
    public List<Produit> storeProduit(Context context, List<Produit> articles) {
        clearProduit(context);
        SQLiteDatabase db = null;
        try{
            db = produitOpenHelper.getWritableDatabase();
            ContentValues insertValue = null;
            for(Produit article : articles){
                insertValue.put(ProduitOpenHelper.PRODUIT_COL_NOM, article.getNomProduit());
                insertValue.put(ProduitOpenHelper.PRODUIT_COL_LIBELLE, article.getLibelle());
                insertValue.put(ProduitOpenHelper.PRODUIT_COL_CODE, article.getCodeBarre());
                insertValue.put(ProduitOpenHelper.PRODUIT_COL_CATEGORIE, article.getCategorie());
                db.insert(ProduitOpenHelper.PRODUIT_TABLE_NAME, null, insertValue);
            }
            return restoreProduit(context);
        }finally {
            db.close();
        }

    }

    @Override
    public List<ProduitReel> storeProduitReel(Context context, List<ProduitReel> articles) {
        clearProduit(context);
        SQLiteDatabase db = null;
        try{
            db = produitOpenHelper.getWritableDatabase();
            ContentValues insertValue = null;
            for(ProduitReel article : articles){
                insertValue.put(ProduitOpenHelper.REEL_COL_PRODUIT, article.getProduit().getNomProduit());
                insertValue.put(ProduitOpenHelper.REEL_COL_STOCKAGE, article.getStockage().getNomStockage());
                insertValue.put(ProduitOpenHelper.REEL_COL_DATE, String.valueOf(article.getDateExpiration()));
                db.insert(ProduitOpenHelper.REEL_TABLE_NAME, null, insertValue);
            }
            return restoreProduitReel(context);
        }finally {
            db.close();
        }
    }

    @Override
    public List<Stockage> storeStockage(Context context, List<Stockage> articles) {
        clearProduit(context);
        SQLiteDatabase db = null;
        try{
            db = produitOpenHelper.getWritableDatabase();
            ContentValues insertValue = null;
            for(Stockage article : articles){
                insertValue.put(ProduitOpenHelper.STOCKAGE_COL_NOM, article.getNomStockage());
                insertValue.put(ProduitOpenHelper.STOCKAGE_COL_TYPE, article.getType());
                db.insert(ProduitOpenHelper.STOCKAGE_TABLE_NAME, null, insertValue);
            }
            return restoreStockage(context);
        }finally {
            db.close();
        }
    }

    @Override
    public List<Produit> restoreProduit(Context context) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{
            db = produitOpenHelper.getWritableDatabase();
            cursor = db.query(ProduitOpenHelper.PRODUIT_TABLE_NAME, new String[]{produitOpenHelper.PRODUIT_COL_NOM}, null, null, null, null, null);
            ArrayList elements = new ArrayList<Produit>() {};
            while(cursor.moveToNext()){
                elements.add(new Produit(cursor.getString(0), cursor.getString(0), cursor.getString(0), cursor.getString(0))); // récuperer toutes les variables...
            }
            cursor.close();
            return elements;
        }finally {
            closeDB(db);
        }
    }

    @Override
    public List<ProduitReel> restoreProduitReel(Context context) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{
            db = produitOpenHelper.getWritableDatabase();
            cursor = db.query(ProduitOpenHelper.REEL_TABLE_NAME, new String[]{produitOpenHelper.REEL_COL_STOCKAGE}, null, null, null, null, null);
            ArrayList elements = new ArrayList<Produit>() {};
            while(cursor.moveToNext()){
                elements.add(new ProduitReel(Produit.getProduit(cursor.getString(0)), Stockage.getStockage(cursor.getString(1)), null)); // récuperer toutes les variables... problème variable
            }
            cursor.close();
            return elements;
        }finally {
            closeDB(db);
        }

    }

    @Override
    public List<Stockage> restoreStockage(Context context) {

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{
            db = produitOpenHelper.getWritableDatabase();
            cursor = db.query(ProduitOpenHelper.STOCKAGE_TABLE_NAME, new String[]{produitOpenHelper.STOCKAGE_COL_NOM}, null, null, null, null, null);
            ArrayList elements = new ArrayList<Produit>() {};
            while(cursor.moveToNext()){
                elements.add(new Produit(cursor.getString(0), cursor.getString(0), cursor.getString(0), cursor.getString(0))); // récuperer toutes les variables...
            }
            cursor.close();
            return elements;
        }finally {
            closeDB(db);
        }
    }

    @Override
    public List<Produit> clearProduit(Context context) {
        SQLiteDatabase db= null;
        try{
            db = produitOpenHelper.getWritableDatabase();
            db.delete(ProduitOpenHelper.PRODUIT_TABLE_NAME, null, null);
            return restoreProduit(context);
        }finally {
            closeDB(db);
        }
    }

    @Override
    public List<ProduitReel> clearProduitReel(Context context) {

        SQLiteDatabase db= null;
        try{
            db = produitOpenHelper.getWritableDatabase();
            db.delete(ProduitOpenHelper.REEL_TABLE_NAME, null, null);
            return restoreProduitReel(context);
        }finally {
            closeDB(db);
        }
    }

    @Override
    public List<Stockage> clearStockage(Context context) {

        SQLiteDatabase db= null;
        try{
            db = produitOpenHelper.getWritableDatabase();
            db.delete(ProduitOpenHelper.STOCKAGE_TABLE_NAME, null, null);
            return restoreStockage(context);
        }finally {
            closeDB(db);
        }
    }

    @Override
    public void addProduit(Context context, Produit article) {
        SQLiteDatabase db = null;
        try {
            db = produitOpenHelper.getWritableDatabase();
            ContentValues insertValue = new ContentValues();
            insertValue.put(ProduitOpenHelper.PRODUIT_COL_NOM, article.getNomProduit());
            insertValue.put(ProduitOpenHelper.PRODUIT_COL_LIBELLE, article.getLibelle());
            insertValue.put(ProduitOpenHelper.PRODUIT_COL_CODE, article.getCodeBarre());
            insertValue.put(ProduitOpenHelper.PRODUIT_COL_CATEGORIE, article.getCategorie());
            long rowId = db.insert(ProduitOpenHelper.PRODUIT_TABLE_NAME, null, insertValue);
        }finally {
            closeDB(db);
        }
    }

    @Override
    public void addProduitReel(Context context, ProduitReel article) {
        SQLiteDatabase db = null;
        try {
            db = produitOpenHelper.getWritableDatabase();
            ContentValues insertValue = new ContentValues();
            insertValue.put(ProduitOpenHelper.REEL_COL_PRODUIT, article.getProduit().getNomProduit());
            insertValue.put(ProduitOpenHelper.REEL_COL_STOCKAGE, article.getStockage().getNomStockage());
            insertValue.put(ProduitOpenHelper.REEL_COL_DATE, String.valueOf(article.getDateExpiration()));
            long rowId = db.insert(ProduitOpenHelper.REEL_TABLE_NAME, null, insertValue);
        }finally {
            closeDB(db);
        }

    }

    @Override
    public void addStockage(Context context, Stockage article) {
        SQLiteDatabase db = null;
        try {
            db = produitOpenHelper.getWritableDatabase();
            ContentValues insertValue = new ContentValues();
            insertValue.put(ProduitOpenHelper.STOCKAGE_COL_NOM, article.getNomStockage());
            insertValue.put(ProduitOpenHelper.STOCKAGE_COL_TYPE, article.getType());
            long rowId = db.insert(ProduitOpenHelper.STOCKAGE_TABLE_NAME, null, insertValue);
        }finally {
            closeDB(db);
        }
    }
}
