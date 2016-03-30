package com.example.martin.gerelefrigo;


import android.content.Context;

import java.util.List;

public interface StorageService {
    /**
     * Enregistre la liste des articles passés en paramètre.
     * @param context contexte de l'activité
     * @param articles liste des articles
     * @return liste des articles sauvegardés par ordre alphabétique
     */
    public List<String> store(Context context, List<String> articles);

    /**
     * Récupère la liste des articles sauvegardés.
     * @param context contexte de l'activité
     * @return liste des articles sauvegardés par ordre alphabétique
     */
    public List<String> restore(Context context);

    /**
     * Vide la liste des articles.
     * @param context contexte de l'activité
     * @return liste des articles vide.
     */
    public List<String> clear(Context context);

    /**
     * Enregistre un nouvel article passé en paramètre.
     * @param context contexte de l'activité
     * @param article article
     */
    public void add(Context context, String article);
}
