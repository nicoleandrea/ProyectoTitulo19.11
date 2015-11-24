package com.example.nicole.smartcoming;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Menu_usuario extends Activity implements AppCompatCallback {

    private AppCompatDelegate delegate;
    private final String IMAGE_URL = "http://smartcomingapp.ddns.net/aplicacion/getImage.php?id_usuario=";

    String idUsuarioURL;
    public String urlPhoto;

    private TextView textViewUrlPhoto;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final ClaseGlobal globalVariable = (ClaseGlobal) getApplicationContext();
        final int id_usuario = globalVariable.getId_usuario();
        idUsuarioURL = Integer.toString(id_usuario);

        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_menu_usuario);

        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar_menu_usuario);
        delegate.setSupportActionBar(toolbar);

        textViewUrlPhoto = (TextView)findViewById(R.id.TxtUrlImagen);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        buscarUrl();
    }

    public void buscarUrl(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, IMAGE_URL + idUsuarioURL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray photos = response.getJSONArray("photos");

                            for (int i = 0; i < photos.length(); i++) {
                                JSONObject photo = photos.getJSONObject(i);

                                String urlImagen = photo.optString("photo");

                                textViewUrlPhoto.setText(urlImagen);

                                urlPhoto = textViewUrlPhoto.getText().toString();
                                final ClaseGlobal globalVariable = (ClaseGlobal) getApplicationContext();
                                globalVariable.setUrlPhoto(urlPhoto);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Menu_usuario.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu_usuario, menu);
        return true;
    }

    public void miPerfil(View v) {
        Intent act = new Intent(this, Mostrar_usuario.class);
        startActivity(act);
    }

    public void mostrarHorarios(View v) {
        Intent act = new Intent(this, Mostrar_horarios.class);
        startActivity(act);
    }

    public void publicarComentario(View v) {
        Intent act = new Intent(this, Publicar_comentario.class);
        startActivity(act);
    }

    public void localizarVehiculos(View v) {
        Intent act = new Intent(this, Localizar_vehiculos.class);
        startActivity(act);

    }

    public void ayudaApp (View v){
        Intent act = new Intent(this, ViewPagerFragmentActivity.class);
        startActivity(act);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
