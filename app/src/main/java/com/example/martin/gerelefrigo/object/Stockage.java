package com.example.martin.gerelefrigo.object;

/**
 * Created by Martin on 30/03/2016.
 */
public class Stockage {
    private String nomStockage;
    private String type;

    public Stockage(String nomStockage, String type) {
        this.nomStockage = nomStockage;
        this.type = type;
    }

    public String getNomStockage() {
        return nomStockage;
    }

    public String getType() {
        return type;
    }

    public void setNomStockage(String nomStockage) {
        this.nomStockage = nomStockage;
    }

}
