package com.example.nicole.smartcoming;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Menu_administrador extends Activity implements AppCompatCallback {

    private AppCompatDelegate delegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_menu_administrador);
        //Finally, let's add the Toolbar
        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar_menu_administrador);
        delegate.setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_administrador, menu);
        return true;
    }

    public void listarUsuarios (View v){
        Intent act = new Intent(this, Listar_usuarios.class);
        startActivity(act);
    }

    public void menuChoferes (View v){
        Intent act = new Intent(this, Menu_choferes.class);
        startActivity(act);
    }

    public void menuVehiculos (View v){
        Intent act = new Intent(this, Menu_vehiculos.class);
        startActivity(act);
    }

    public void mostrarHorariosAdm (View v){
        Intent act = new Intent(this, Mostrar_horarios.class);
        startActivity(act);
    }

    public void publicarComentarioAdm (View v){
        Intent act = new Intent(this, Publicar_comentario.class);
        startActivity(act);
    }

    public void ayudaAppAdm (View v){
        Intent act = new Intent(this, ViewPagerFragmentAdmActivity.class);
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
