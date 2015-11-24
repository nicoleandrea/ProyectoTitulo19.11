package com.example.nicole.smartcoming;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Menu_choferes extends Activity implements AppCompatCallback {

    private AppCompatDelegate delegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_menu_choferes);

        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar_menu_choferes);
        delegate.setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu_choferes, menu);
        return true;
    }

    public void registroChofer (View v){
        Intent act = new Intent(this, Registrar_chofer.class);
        startActivity(act);
    }

    public void modificarChofer (View v){
        Intent act = new Intent(this, Modificar_chofer.class);
        startActivity(act);
    }

    public void eliminarChofer (View v){
        Intent act = new Intent(this, Eliminar_chofer.class);
        startActivity(act);
    }

    public void reporteChofer (View v){
        Intent act = new Intent(this, Reporte_chofer.class);
        startActivity(act);
    }

    public void buscarChofer (View v){
        Intent act = new Intent(this, Buscar_chofer.class);
        startActivity(act);
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

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
}
