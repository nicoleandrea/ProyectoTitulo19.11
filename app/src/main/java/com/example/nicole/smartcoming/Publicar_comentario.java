package com.example.nicole.smartcoming;

import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import CustomsList.CustomListComentarios;
import JSONParse.ParseComentariosJSON;

public class Publicar_comentario extends Activity implements View.OnClickListener, AppCompatCallback {

    public static final String JSON_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyListarComentarios.php";
    private static final String REGISTER_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyPublicarComentario.php?id_usuario=";

    private AppCompatDelegate delegate;

    public static final String KEY_TEXTO = "texto";
    private EditText editTextTexto;
    private Button buttonRegister;
    private ListView listView;

    String idUsuarioURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_publicar_comentario);

        editTextTexto= (EditText) findViewById(R.id.txt_comentario);

        buttonRegister = (Button) findViewById(R.id.btn_publicar);
        buttonRegister.setOnClickListener(this);

        //Finally, let's add the Toolbar
        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar_publicar_comentario);
        delegate.setSupportActionBar(toolbar);

        final ClaseGlobal globalVariable = (ClaseGlobal) getApplicationContext();
        final int id_usuario = globalVariable.getId_usuario();
        idUsuarioURL = Integer.toString(id_usuario);

        sendRequest();
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
                        Toast.makeText(Publicar_comentario.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Publicar_comentario.this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        listView = (ListView) findViewById(R.id.listaComentarios);

        ParseComentariosJSON pj = new ParseComentariosJSON(json);
        pj.parseComentariosJSON();
        CustomListComentarios cl = new CustomListComentarios(this, ParseComentariosJSON.nombre,ParseComentariosJSON.texto,ParseComentariosJSON.fecha, ParseComentariosJSON.fechaHora);
        listView.setAdapter(cl);
    }

    private void registerComentario() {
        final String texto = editTextTexto.getText().toString();

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.text_input_txt_comentario);

        if (editTextTexto.getText().length() == 0) {
            til1.setError("Debe ingresar comentario");

            editTextTexto.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    til1.setError(null);
                }
            });
        }else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL  + idUsuarioURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(Publicar_comentario.this, response, Toast.LENGTH_LONG).show();
                            sendRequest();
                            editTextTexto.setText("");
                            editTextTexto.requestFocus();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Publicar_comentario.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_TEXTO, texto);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    @Override
    public void onClick(View v) {
        registerComentario();
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