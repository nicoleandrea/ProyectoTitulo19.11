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
import android.widget.TextView;
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

import CustomsList.CustomListChoferes;
import CustomsList.CustomListChoferesEliminar;
import JSONParse.ParseChoferesEliminarJSON;
import JSONParse.ParseChoferesJSON;

public class Eliminar_chofer extends Activity implements View.OnClickListener, AppCompatCallback {

    private AppCompatDelegate delegate;

    public static final String ELIMINAR_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyEliminarChofer.php";
    public static final String VALIDAR_RUT_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyValidacionUpdateChofer.php";
    public static final String ACEPTAR_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyBuscarChoferesEliminar.php";

    public static final String KEY_RUT="rut";

    private EditText editTextRut;
    //private Button buttonEliminar;
    private Button buttonAceptar;
    private TextView textViewValidador;
    private ListView listEliminarChofer;
    private ImageButton imageButtonEliminar;

    private String rut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();

        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_eliminar_chofer);

        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar_eliminar_chofer);
        delegate.setSupportActionBar(toolbar);

        editTextRut = (EditText) findViewById(R.id.txt_rutChoferEliminar);

        buttonAceptar = (Button) findViewById(R.id.btn_aceptarChoferElim);
        buttonAceptar.setOnClickListener(this);

        imageButtonEliminar = (ImageButton)findViewById(R.id.img_btn_eliminarChofer);
        imageButtonEliminar.setOnClickListener(this);

        textViewValidador = (TextView)findViewById(R.id.TxtValidadorRutDelete);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //METODO PARA VALIDAR QUE EL RUT EXISTE EN LA BASE DE DATOS///////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*private boolean comprobarChofer(){

        final String rut = editTextRut.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, VALIDAR_RUT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("El rut ingresado no existe")) {
                            Toast.makeText(Eliminar_chofer.this, response, Toast.LENGTH_LONG).show();

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Eliminar_chofer.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_RUT, rut);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        return true;
    }*/

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void sendRequest() {
        rut = editTextRut.getText().toString().trim();

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.text_input_eliminarChofer_aceptar);

        if (editTextRut.getText().length() == 0) {
            til1.setError("Debe ingresar rut");

            editTextRut.addTextChangedListener(new TextWatcher() {

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
                            if (response.equals("El rut ingresado no existe")) {
                                Toast.makeText(Eliminar_chofer.this, response, Toast.LENGTH_LONG).show();

                            } else {
                                showJSON(response);
                                imageButtonEliminar.setVisibility(View.VISIBLE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Eliminar_chofer.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(KEY_RUT, rut);
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Eliminar_chofer.this);
            requestQueue.add(stringRequest);
        }
    }

    private void showJSON(String json){
        listEliminarChofer = (ListView) findViewById(R.id.listViewEliminarChofer);

        ParseChoferesEliminarJSON pj = new ParseChoferesEliminarJSON(json);
        pj.parseChoferesEliminarJSON();
        CustomListChoferesEliminar cl = new CustomListChoferesEliminar(this, ParseChoferesEliminarJSON.nombre);
        listEliminarChofer.setAdapter(cl);
    }



   private void eliminarChofer() {

       rut = editTextRut.getText().toString().trim();

       AlertDialog.Builder alert = new AlertDialog.Builder(Eliminar_chofer.this, R.style.DialogTheme);

       alert.setTitle("ELIMINAR");
       alert.setMessage("¿Está seguro que desea eliminar este chofer?");
       alert.setIcon(R.mipmap.ic_delete_black_48dp);
       alert.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {

           @Override
           public void onClick(DialogInterface dialog, int which) {

               StringRequest stringRequest = new StringRequest(Request.Method.POST, ELIMINAR_URL,
                       new Response.Listener<String>() {
                           @Override
                           public void onResponse(String response) {
                               Toast.makeText(Eliminar_chofer.this, response, Toast.LENGTH_LONG).show();
                               Intent intent = new Intent(Eliminar_chofer.this, Menu_choferes.class);
                               startActivity(intent);

                           }
                       },
                       new Response.ErrorListener() {
                           @Override
                           public void onErrorResponse(VolleyError error) {
                               Toast.makeText(Eliminar_chofer.this, error.toString(), Toast.LENGTH_LONG).show();
                           }
                       }) {
                   @Override
                   protected Map<String, String> getParams() throws AuthFailureError {
                       Map<String, String> map = new HashMap<String, String>();
                       map.put(KEY_RUT, rut);
                       return map;
                   }
               };

               RequestQueue requestQueue = Volley.newRequestQueue(Eliminar_chofer.this);
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
            eliminarChofer();
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
}