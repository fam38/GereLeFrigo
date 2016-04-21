package com.example.martin.gerelefrigo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.martin.gerelefrigo.object.Produit;
import com.example.martin.gerelefrigo.object.ProduitReel;
import com.example.martin.gerelefrigo.object.Stockage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView listViewProduit;
    private ArrayAdapter<String> adapter;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, AddProduit.class);
                startActivity(addIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listViewProduit = (ListView) findViewById(R.id.content_main_listview_produit);

        adapter = new ArrayAdapter<String>(this, R.layout.listview_produit);
        listViewProduit.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    protected void onResume() {
        super.onResume();
        ArrayList<String> listNom = new ArrayList<String>();

        Produit cafe = new Produit("cafe","cac","110101","trucbon");
        Stockage frigo = new Stockage("frigo", "refregirant");
        ProduitReel cafeDhier = new ProduitReel(cafe,frigo,new Date());
        ((MyApplication) getApplication()).getStorageService().addProduit(this,cafe);
        ((MyApplication) getApplication()).getStorageService().addStockage(this, frigo);
        ((MyApplication) getApplication()).getStorageService().addProduitReel(this,cafeDhier);
        List<ProduitReel> produitList = ((MyApplication) getApplication()).getStorageService().restoreProduitReel(this);
        for(int i=0; i<produitList.size();i++){
            listNom.add(produitList.get(i).getProduit().getNomProduit());
        }
       // listNom.add(cafeDhier.getProduit().getNomProduit());
        if (listNom != null)
        updateAdapter(listNom);
    }


    protected void updateAdapter(List<String> produitList){
        adapter.clear();
        adapter.addAll(produitList);
        adapter.notifyDataSetChanged();
    }
}
