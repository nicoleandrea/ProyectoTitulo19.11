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
import android.widget.ListView;
import android.widget.Toast;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import CustomsList.CustomListVehiculos;
import JSONParse.ParseVehiculosJSON;

public class Buscar_vehiculo extends Activity implements View.OnClickListener, AppCompatCallback {

    public static final String JSON_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyBuscarVehiculos.php";
    public static final String KEY_PATENTE="patente";
    private AppCompatDelegate delegate;

    private EditText editTextPatente;
    private Button buttonAceptar;
    private ListView listVehiculos;

    private String patente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_buscar_vehiculo);

        //Finally, let's add the Toolbar
        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar_buscar_vehiculo);
        delegate.setSupportActionBar(toolbar);

        editTextPatente = (EditText) findViewById(R.id.txt_patenteVehiculoBuscar);
        buttonAceptar = (Button) findViewById(R.id.btn_aceptarVehiculoBuscar);
        buttonAceptar.setOnClickListener(this);

    }

    private void sendRequest(){
        patente = editTextPatente.getText().toString().trim();

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.text_input_patenteVehiculo_aceptar);

        if (editTextPatente.getText().length() == 0) {
            til1.setError("Debe ingresar patente");

            editTextPatente.addTextChangedListener(new TextWatcher() {

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

        } else {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("La patente ingresada no existe")) {
                                Toast.makeText(Buscar_vehiculo.this, response, Toast.LENGTH_LONG).show();

                            } else {
                                showJSON(response);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Buscar_vehiculo.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(KEY_PATENTE, patente);
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Buscar_vehiculo.this);
            requestQueue.add(stringRequest);
        }
    }

    private void showJSON(String json){
        listVehiculos = (ListView) findViewById(R.id.listViewVehiculos);

        ParseVehiculosJSON pj = new ParseVehiculosJSON(json);
        pj.parseVehiculosJSON();
        CustomListVehiculos cl = new CustomListVehiculos(this, ParseVehiculosJSON.patente,ParseVehiculosJSON.marca, ParseVehiculosJSON.modelo, ParseVehiculosJSON.anno, ParseVehiculosJSON.nombre_duenno, ParseVehiculosJSON.kilometraje, ParseVehiculosJSON.revision_tecnica);
        listVehiculos.setAdapter(cl);
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