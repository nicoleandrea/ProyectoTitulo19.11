package com.example.nicole.smartcoming;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Modificar_vehiculo extends Activity implements View.OnClickListener, AppCompatCallback {

    public static final String SHOW_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyShowVehiculos.php?patente=";
    private static final String UPDATE_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyUpdateVehiculo.php";
    public static final String VALIDAR_PATENTE_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyValidacionUpdateVehiculo.php";

    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    public static final String KEY_PATENTE = "patente";
    public static final String KEY_MARCA = "marca";
    public static final String KEY_MODELO = "modelo";
    public static final String KEY_ANNO = "anno";
    public static final String KEY_NOMBRE_DUENNO = "nombre_duenno";
    public static final String KEY_KILOMETRAJE = "kilometraje";
    public static final String KEY_REVISION_TECNICA = "revision_tecnica";

    private AppCompatDelegate delegate;

    private EditText editTextPatente;

    private EditText editTextPatenteDos;
    private EditText editTextMarca;
    private EditText editTextModelo;
    private EditText editTextAnno;
    private EditText editTextNombreDuenno;
    private EditText editTextKilometraje;
    private EditText editTextRevisionTecnica;

    private TextView textViewValidador;

    private Button buttonAceptar;
    private Button buttonUpdate;
    String patenteURL;
    private String validador;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_modificar_vehiculo);

        //Finally, let's add the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar_modificar_vehiculo);
        delegate.setSupportActionBar(toolbar);

        buttonAceptar = (Button) findViewById(R.id.btn_aceptarVehiculoMod);
        buttonAceptar.setOnClickListener(this);

        buttonUpdate = (Button) findViewById(R.id.btn_modificarVehiculoMod);
        buttonUpdate.setOnClickListener(this);

        editTextPatenteDos = (EditText) findViewById(R.id.txt_patenteVehiculoMod);
        editTextMarca = (EditText) findViewById(R.id.txt_marcaVehiculoMod);
        editTextModelo = (EditText) findViewById(R.id.txt_modeloVehiculoMod);
        editTextAnno = (EditText) findViewById(R.id.txt_annoVehiculoMod);
        editTextNombreDuenno = (EditText) findViewById(R.id.txt_duennoVehiculoMod);
        editTextKilometraje = (EditText) findViewById(R.id.txt_kilometrajeVehiculoMod);
        editTextRevisionTecnica = (EditText) findViewById(R.id.txt_RevisionTecnicaMod);
        textViewValidador = (TextView)findViewById(R.id.TxtValidadorPatente);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        findViewsById();
        setDateTimeField();

        requestQueue = Volley.newRequestQueue(getApplicationContext());

    }

    private void findViewsById() {
        editTextRevisionTecnica = (EditText) findViewById(R.id.txt_RevisionTecnicaMod);
        editTextRevisionTecnica.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        editTextRevisionTecnica.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextRevisionTecnica.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //METODO PARA VALIDAR QUE LA PATENTE EXISTE EN LA BASE DE DATOS///////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean comprobarVehiculo(){

        final String patente = editTextPatente.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, VALIDAR_PATENTE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("La patente ingresada no existe")) {
                            Toast.makeText(Modificar_vehiculo.this, response, Toast.LENGTH_LONG).show();
                            textViewValidador.setText("patente bad");
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Modificar_vehiculo.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_PATENTE, patente);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        validador = textViewValidador.getText().toString();
        if(validador.equals("patente bad")){
            return true;
        }else{
            return false;
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void sendRequest() {
        final LinearLayout cuadroModificar= (LinearLayout)findViewById(R.id.LayoutModificarVehiculo);
        editTextPatente = (EditText) findViewById(R.id.txt_patenteVehiculoModificar);
        patenteURL = editTextPatente.getText().toString();

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.text_input_patenteVehiculoModificar);

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

        }else if (comprobarVehiculo()==true) {
            textViewValidador.setText("");

        } else {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, SHOW_URL+patenteURL,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray vehiculos = response.getJSONArray("vehiculos");

                                for (int i = 0; i < vehiculos.length(); i++) {
                                    JSONObject vehiculo = vehiculos.getJSONObject(i);

                                    String patente = vehiculo.optString("patente");
                                    String marca = vehiculo.optString("marca");
                                    String modelo = vehiculo.optString("modelo");
                                    String anno = vehiculo.optString("anno");
                                    String nombre_duenno = vehiculo.optString("nombre_duenno");
                                    String kilometraje = vehiculo.optString("kilometraje");
                                    String revision_tecnica = vehiculo.optString("DATE_FORMAT(revision_tecnica, '%d-%m-%Y')");

                                    editTextPatenteDos.setText(patente);
                                    editTextMarca.setText(marca);
                                    editTextModelo.setText(modelo);
                                    editTextAnno.setText(anno);
                                    editTextNombreDuenno.setText(nombre_duenno);
                                    editTextKilometraje.setText(kilometraje);
                                    editTextRevisionTecnica.setText(revision_tecnica);

                                    cuadroModificar.setVisibility(View.VISIBLE);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Modificar_vehiculo.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            requestQueue.add(jsonObjectRequest);

        }
    }



    private void updateVehiculo() {
        final String patente = editTextPatente.getText().toString().trim();
        final String marca = editTextMarca.getText().toString().trim();
        final String modelo = editTextModelo.getText().toString().trim();
        final String anno = editTextAnno.getText().toString().trim();
        final String nombre_duenno = editTextNombreDuenno.getText().toString().trim();
        final String kilometraje = editTextKilometraje.getText().toString().trim();
        final String revision_tecnica = editTextRevisionTecnica.getText().toString().trim();

        final TextInputLayout til2 = (TextInputLayout) findViewById(R.id.text_input_marcaVehiculoMod);
        final TextInputLayout til3 = (TextInputLayout) findViewById(R.id.text_input_modeloVehiculoMod);
        final TextInputLayout til4 = (TextInputLayout) findViewById(R.id.text_input_annoVehiculoMod);
        final TextInputLayout til5 = (TextInputLayout) findViewById(R.id.text_input_duennoVehiculoMod);
        final TextInputLayout til6 = (TextInputLayout) findViewById(R.id.text_input_kilometrajeVehiculoMod);
        final TextInputLayout til7 = (TextInputLayout) findViewById(R.id.text_input_RevisionTecnicaMod);

        if (editTextMarca.getText().length() == 0) {
            til2.setError("Debe ingresar marca");

            editTextMarca.addTextChangedListener(new TextWatcher() {

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


        } if (editTextModelo.getText().length() == 0) {
            til3.setError("Debe ingresar modelo");

            editTextModelo.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    til3.setError(null);
                }
            });

        }if (editTextAnno.getText().length() == 0) {
            til4.setError("Debe ingresar año");

            editTextAnno.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    til4.setError(null);
                }
            });

        }

        if (editTextNombreDuenno.getText().length() == 0) {
            til5.setError("Debe ingresar dueño");

            editTextNombreDuenno.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    til5.setError(null);
                }
            });

        } if (editTextKilometraje.getText().length() == 0) {
            til6.setError("Debe ingresar kilometraje");

            editTextKilometraje.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    til6.setError(null);
                }
            });

        }if (editTextRevisionTecnica.getText().length() == 0) {
            til7.setError("Debe ingresar revisión técnica");

            editTextRevisionTecnica.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    til7.setError(null);
                }
            });

        }else {

            AlertDialog.Builder alert = new AlertDialog.Builder(Modificar_vehiculo.this, R.style.DialogTheme);

            alert.setTitle("MODIFICAR");
            alert.setMessage("¿Está seguro que desea modificar este vehículo?");
            alert.setIcon(R.mipmap.ic_mode_edit_black_48dp);
            alert.setPositiveButton("MODIFICAR", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(Modificar_vehiculo.this, response, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Modificar_vehiculo.this, Menu_vehiculos.class);
                                    startActivity(intent);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Modificar_vehiculo.this, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put(KEY_PATENTE, patente);
                            params.put(KEY_MARCA, marca);
                            params.put(KEY_MODELO, modelo);
                            params.put(KEY_ANNO, anno);
                            params.put(KEY_NOMBRE_DUENNO, nombre_duenno);
                            params.put(KEY_KILOMETRAJE, kilometraje);
                            params.put(KEY_REVISION_TECNICA, revision_tecnica);
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(Modificar_vehiculo.this);
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
        if(v == buttonUpdate){
            updateVehiculo();
        }else if(v == buttonAceptar){
            textViewValidador.setText("");
            sendRequest();
        }else if(v == editTextRevisionTecnica) {
            fromDatePickerDialog.show();
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