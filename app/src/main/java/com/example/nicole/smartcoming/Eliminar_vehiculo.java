package com.example.nicole.smartcoming;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import CustomsList.CustomListChoferesEliminar;
import CustomsList.CustomListVehiculosEliminar;
import JSONParse.ParseChoferesEliminarJSON;
import JSONParse.ParseVehiculosEliminarJSON;

public class Eliminar_vehiculo extends Activity implements View.OnClickListener, AppCompatCallback {

    private AppCompatDelegate delegate;

    public static final String ELIMINAR_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyEliminarVehiculo.php";
    public static final String ACEPTAR_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyBuscarVehiculosEliminar.php";

    public static final String KEY_PATENTE="patente";

    private EditText editTextPatente;
    private Button buttonAceptar;
    private ListView listEliminarVehiculo;
    private ImageButton imageButtonEliminar;
    private String patente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();

        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_eliminar_vehiculo);

        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar_eliminar_vehiculo);
        delegate.setSupportActionBar(toolbar);

        editTextPatente = (EditText) findViewById(R.id.txt_patenteVehiculoEliminar);

        buttonAceptar = (Button) findViewById(R.id.btn_aceptarVehiculoElim);
        buttonAceptar.setOnClickListener(this);

        imageButtonEliminar = (ImageButton)findViewById(R.id.img_btn_eliminarVehiculo);
        imageButtonEliminar.setOnClickListener(this);
    }

    private void sendRequest() {
        patente = editTextPatente.getText().toString().trim();

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.text_input_eliminarVehiculo_aceptar);

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


            StringRequest stringRequest = new StringRequest(Request.Method.POST, ACEPTAR_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("La patente ingresada no existe")) {
                                Toast.makeText(Eliminar_vehiculo.this, response, Toast.LENGTH_LONG).show();

                            } else {
                                showJSON(response);
                                imageButtonEliminar.setVisibility(View.VISIBLE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Eliminar_vehiculo.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(KEY_PATENTE, patente);
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Eliminar_vehiculo.this);
            requestQueue.add(stringRequest);
        }
    }

    private void showJSON(String json){
        listEliminarVehiculo = (ListView) findViewById(R.id.listViewEliminarVehiculo);

        ParseVehiculosEliminarJSON pj = new ParseVehiculosEliminarJSON(json);
        pj.parseVehiculosEliminarJSON();
        CustomListVehiculosEliminar cl = new CustomListVehiculosEliminar(this, ParseVehiculosEliminarJSON.vehiculo);
        listEliminarVehiculo.setAdapter(cl);
    }



    private void eliminarVehiculo() {

        patente = editTextPatente.getText().toString().trim();

        AlertDialog.Builder alert = new AlertDialog.Builder(Eliminar_vehiculo.this, R.style.DialogTheme);

        alert.setTitle("ELIMINAR");
        alert.setMessage("¿Está seguro que desea eliminar este vehículo?");
        alert.setIcon(R.mipmap.ic_delete_black_48dp);
        alert.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ELIMINAR_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(Eliminar_vehiculo.this, response, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Eliminar_vehiculo.this, Menu_vehiculos.class);
                                startActivity(intent);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Eliminar_vehiculo.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put(KEY_PATENTE, patente);
                        return map;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Eliminar_vehiculo.this);
                requestQueue.add(stringRequest);
            }

        });

        alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alert.show();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAceptar){
            sendRequest();
        }else if(v == imageButtonEliminar){
            //textViewValidador.setText("");
            eliminarVehiculo();
        }
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


    /*private void eliminarVehiculo() {
        patente = editTextPatente.getText().toString().trim();

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.text_input_eliminarVehiculo_aceptar);

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

            AlertDialog.Builder alert = new AlertDialog.Builder(Eliminar_vehiculo.this, R.style.DialogTheme);

            alert.setTitle("ELIMINAR");
            alert.setMessage("¿Está seguro que desea eliminar este vehículo?");
            alert.setIcon(R.mipmap.ic_delete_black_48dp);
            alert.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ELIMINAR_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.trim().equals("success")) {
                                        //openProfile();
                                    } else {
                                        Toast.makeText(Eliminar_vehiculo.this, response, Toast.LENGTH_LONG).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Eliminar_vehiculo.this, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put(KEY_PATENTE, patente);
                            return map;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(Eliminar_vehiculo.this);
                    requestQueue.add(stringRequest);
                }

            });

            alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            alert.show();

        }
    }

    @Override
    public void onClick(View v) {
        eliminarVehiculo();
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
    }*/
}