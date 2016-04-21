package com.example.martin.gerelefrigo;


import android.content.Context;

import com.example.martin.gerelefrigo.object.Produit;
import com.example.martin.gerelefrigo.object.ProduitReel;
import com.example.martin.gerelefrigo.object.Stockage;

import java.util.List;

public interface StorageService {
    /**
     * Enregistre la liste des articles passés en paramètre.
     * @param context contexte de l'activité
     * @param articles liste des articles
     * @return liste des articles sauvegardés par ordre alphabétique
     */
    public List<Produit> storeProduit(Context context, List<Produit> articles);
    public List<ProduitReel> storeProduitReel(Context context, List<ProduitReel> articles);
    public List<Stockage> storeStockage(Context context, List<Stockage> articles);
    /**
     * Récupère la liste des articles sauvegardés.
     * @param context contexte de l'activité
     * @return liste des articles sauvegardés par ordre alphabétique
     */
    public List<Produit> restoreProduit(Context context);
    public List<ProduitReel> restoreProduitReel(Context context);
    public List<Stockage> restoreStockage(Context context);
    public List<String> restoreProduitReelNom(Context context);
    /**
     * Vide la liste des articles.
     * @param context contexte de l'activité
     * @return liste des articles vide.
     */
    public List<Produit> clearProduit(Context context);
    public List<ProduitReel> clearProduitReel(Context context);
    public List<Stockage> clearStockage(Context context);

    /**
     * Enregistre un nouvel article passé en paramètre.
     * @param context contexte de l'activité
     * @param article article
     */
    public void addProduit(Context context, Produit article);
    public void addProduitReel(Context context, ProduitReel article);
    public void addStockage(Context context, Stockage article);

}
