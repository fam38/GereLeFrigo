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
        import java.util.ArrayList;
        import java.util.List;

public class StorageServiceImpl implements StorageService {

    private final ProduitOpenHelper produitOpenHelper;

    public StorageServiceImpl(Context context) {
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
        return null;
    }

    @Override
    public List<Stockage> storeStockage(Context context, List<Stockage> articles) {
        return null;
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
        return null;
    }

    @Override
    public List<Stockage> restoreStockage(Context context) {
        return null;
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
        return null;
    }

    @Override
    public List<Stockage> clearStockage(Context context) {
        return null;
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


    }

    @Override
    public void addStockage(Context context, Stockage article) {

    }
}
