package com.example.nicole.smartcoming;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Iniciar_sesion extends Activity implements View.OnClickListener, AppCompatCallback {

    private AppCompatDelegate delegate;
    public int IdUsuarioGlobal;

    public static final String LOGIN_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyLoginUsers.php?";
    public static final String VALIDAR_LOGIN_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyValidacionLogin.php";

    public static final String KEY_CORREO = "correo";
    public static final String KEY_CONTRASENA = "contrasena";

    private EditText editTextCorreo;
    private EditText editTextContrasena;

    private TextView textViewIdUsuario;
    private TextView textViewTipoUsuario;
    private TextView textViewValidador;

    private Button buttonLogin;

    private String validador;

    private String correoURL;
    private String contrasenaURL;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_iniciar_sesion);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar_iniciar_sesion);
        delegate.setSupportActionBar(toolbar);

        textViewIdUsuario = (TextView) findViewById(R.id.TxtIdUsuario);
        textViewTipoUsuario = (TextView) findViewById(R.id.TxtTipoUsuario);
        textViewValidador = (TextView)findViewById(R.id.TxtValidador);

        buttonLogin = (Button) findViewById(R.id.btn_ingresar);
        buttonLogin.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    private boolean comprobarUsuario(){

        final String correo = editTextCorreo.getText().toString().trim();
        final String contrasena = editTextContrasena.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, VALIDAR_LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Correo o contrasena incorrecta")) {
                            Toast.makeText(Iniciar_sesion.this, response, Toast.LENGTH_LONG).show();
                            textViewValidador.setText("login bad");
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Iniciar_sesion.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_CORREO, correo);
                params.put(KEY_CONTRASENA, contrasena);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        validador = textViewValidador.getText().toString();
        if(validador.equals("login bad")){
            return true;
        }else{
            return false;
        }

    }


    private void userLogin() {

        editTextCorreo = (EditText) findViewById(R.id.txt_correo2);
        correoURL = editTextCorreo.getText().toString();

        editTextContrasena = (EditText) findViewById(R.id.txt_contrasena2);
        contrasenaURL = editTextContrasena.getText().toString();

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.text_input_layout1);
        final TextInputLayout til2 = (TextInputLayout) findViewById(R.id.text_input_layout2);


        if (editTextCorreo.getText().length() == 0) {
            til1.setError("Correo es requerido");

            editTextCorreo.addTextChangedListener(new TextWatcher() {

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
        }

        if (editTextContrasena.getText().length() == 0) {
            til2.setError("Contraseña es requerida");

            editTextContrasena.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    til2.setError(null);
                }
            });

        }

        if(comprobarUsuario()==true) {
            textViewValidador.setText("");

        }else {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, LOGIN_URL + "correo=" + correoURL + "&contrasena=" + contrasenaURL,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                                try {
                                    JSONArray usuarios = response.getJSONArray("usuarios");

                                    for (int i = 0; i < usuarios.length(); i++) {
                                        JSONObject usuario = usuarios.getJSONObject(i);

                                        String id_usuario = usuario.optString("id_usuario");
                                        String tipo_usuario = usuario.optString("tipo_usuario");

                                        textViewIdUsuario.setText(id_usuario);
                                        textViewTipoUsuario.setText(tipo_usuario);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                String tipoUser = textViewTipoUsuario.getText().toString();
                                if (tipoUser.equals("user")) {
                                    Intent intent = new Intent(Iniciar_sesion.this, Menu_usuario.class);
                                    startActivity(intent);
                                }
                                if (tipoUser.equals("admin")) {
                                    Intent intent = new Intent(Iniciar_sesion.this, Menu_administrador.class);
                                    startActivity(intent);
                                }

                                IdUsuarioGlobal = Integer.valueOf(textViewIdUsuario.getText().toString());
                                final ClaseGlobal globalVariable = (ClaseGlobal) getApplicationContext();
                                globalVariable.setId_usuario(IdUsuarioGlobal);
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(Iniciar_sesion.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            int MY_SOCKET_TIMEOUT_MS = 30000;

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonObjectRequest);
        }
    }

    @Override
    public void onClick(View v) {
        textViewTipoUsuario.setText("");
        textViewValidador.setText("");
        userLogin();
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