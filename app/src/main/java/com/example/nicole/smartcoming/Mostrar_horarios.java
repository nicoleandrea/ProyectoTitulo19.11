package com.example.nicole.smartcoming;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TabHost;
import android.support.v7.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import CustomsList.CustomListHorarios;
import JSONParse.ParseHorariosJSON;

public class Mostrar_horarios extends Activity implements AppCompatCallback {

    public static final String JSON_URL_A = "http://smartcomingapp.ddns.net/aplicacion/volleyMostrarHorariosA.php";
    public static final String JSON_URL_B = "http://smartcomingapp.ddns.net/aplicacion/volleyMostrarHorariosB.php";
    public static final String JSON_URL_C = "http://smartcomingapp.ddns.net/aplicacion/volleyMostrarHorariosC.php";

    private AppCompatDelegate delegate;

    private TabHost tabRecorridos;

    private ListView listaReco1;
    private ListView listaReco2;
    private ListView listaReco3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_mostrar_horarios);

        //Finally, let's add the Toolbar
        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar_mostrar_horarios);
        delegate.setSupportActionBar(toolbar);

        tabRecorridos = (TabHost)findViewById(R.id.tabHost);

        //tabRecorridos.setOnTabChangedListener(this);

        tabRecorridos.setup();

        TabHost.TabSpec spec=tabRecorridos.newTabSpec("Recorrido1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Recorrido 1");
        tabRecorridos.addTab(spec);

        spec=tabRecorridos.newTabSpec("Recorrido 2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Recorrido 2");
        tabRecorridos.addTab(spec);

        spec=tabRecorridos.newTabSpec("Recorrido3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Recorrido 3");
        tabRecorridos.addTab(spec);

        tabRecorridos.setCurrentTab(0);

        sendRequestA();
        sendRequestB();
        sendRequestC();
    }

    private void sendRequestA(){

        StringRequest stringRequest = new StringRequest(JSON_URL_A,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSONA(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Mostrar_horarios.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Mostrar_horarios.this);
        requestQueue.add(stringRequest);
    }

    private void sendRequestB(){

        StringRequest stringRequest = new StringRequest(JSON_URL_B,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSONB(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Mostrar_horarios.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Mostrar_horarios.this);
        requestQueue.add(stringRequest);
    }

    private void sendRequestC(){

        StringRequest stringRequest = new StringRequest(JSON_URL_C,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSONC(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Mostrar_horarios.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Mostrar_horarios.this);
        requestQueue.add(stringRequest);
    }

    private void showJSONA(String json){
        listaReco1 = (ListView) findViewById(R.id.listViewHorarioA);

        ParseHorariosJSON pj = new ParseHorariosJSON(json);
        pj.parseHorariosJSON();
        CustomListHorarios cl = new CustomListHorarios(this, ParseHorariosJSON.hora);
        listaReco1.setAdapter(cl);
    }

    private void showJSONB(String json){
        listaReco2 = (ListView) findViewById(R.id.listViewHorarioB);

        ParseHorariosJSON pj = new ParseHorariosJSON(json);
        pj.parseHorariosJSON();
        CustomListHorarios cl = new CustomListHorarios(this, ParseHorariosJSON.hora);
        listaReco2.setAdapter(cl);
    }

    private void showJSONC(String json){
        listaReco3 = (ListView) findViewById(R.id.listViewHorarioC);

        ParseHorariosJSON pj = new ParseHorariosJSON(json);
        pj.parseHorariosJSON();
        CustomListHorarios cl = new CustomListHorarios(this, ParseHorariosJSON.hora);
        listaReco3.setAdapter(cl);
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