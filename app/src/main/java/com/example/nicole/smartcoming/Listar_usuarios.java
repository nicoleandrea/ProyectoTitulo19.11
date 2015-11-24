package com.example.nicole.smartcoming;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import CustomsList.CustomListUsuarios;
import JSONParse.ParseUsuariosJSON;

public class Listar_usuarios extends Activity implements View.OnClickListener, AppCompatCallback {

    public static final String JSON_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyListarUsuarios.php";

    private AppCompatDelegate delegate;

    private ImageButton buttonGet;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_listar_usuarios);

        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar_listar_usuarios);
        delegate.setSupportActionBar(toolbar);

        buttonGet = (ImageButton) findViewById(R.id.img_btn_listarUsuarios);
        buttonGet.setOnClickListener(this);
    }

    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Listar_usuarios.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Listar_usuarios.this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        listView = (ListView) findViewById(R.id.listaUsuarios);

        ParseUsuariosJSON pj = new ParseUsuariosJSON(json);
        pj.parseUsuariosJSON();
        CustomListUsuarios cl = new CustomListUsuarios(this, ParseUsuariosJSON.nombre,ParseUsuariosJSON.provincia);
        listView.setAdapter(cl);
    }

    @Override
    public void onClick(View v) {
        sendRequest();
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