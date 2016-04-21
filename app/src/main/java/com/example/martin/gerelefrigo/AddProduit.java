package com.example.martin.gerelefrigo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.martin.gerelefrigo.object.Produit;
import com.example.martin.gerelefrigo.object.ProduitReel;
import com.example.martin.gerelefrigo.object.Stockage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Martin on 21/04/2016.
 */

public class AddProduit extends AppCompatActivity {
    Spinner listeStockage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produit);
        listeStockage = (Spinner) findViewById(R.id.activity_add_liststock);

        List<Stockage> tlStockage = ((MyApplication) getApplication()).getStorageService().restoreStockage(AddProduit.this);
        List stockages = new ArrayList();
        for(int i=0;i<tlStockage.size();i++){
            stockages.add(tlStockage.get(i).getNomStockage());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, stockages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listeStockage.setAdapter(adapter);

        final EditText editText = (EditText) findViewById(R.id.activity_add_edittext);
        Button button = (Button) findViewById(R.id.activity_add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(AddProduit.this, R.string.mandatory_message, Toast.LENGTH_LONG).show();
                } else {
                    Produit tisane = new Produit("tisane","tsn","1101","eauchaude");
                    Stockage placard = new Stockage("placard","sec");
                    ((MyApplication) getApplication()).getStorageService().addProduitReel(AddProduit.this, new ProduitReel(tisane,placard,new Date()));
                    AddProduit.this.finish();
                }

            }
        });
    }
}

