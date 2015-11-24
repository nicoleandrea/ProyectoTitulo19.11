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
import android.widget.LinearLayout;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Modificar_chofer extends Activity implements View.OnClickListener, AppCompatCallback {

    public static final String SHOW_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyShowChoferes.php?rut=";
    private static final String UPDATE_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyUpdateChoferes.php";
    public static final String VALIDAR_RUT_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyValidacionUpdateChofer.php";

    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    private String a = "A1";
    private String b = "A2";
    private String c = "A3";
    private String d = "A4";
    private String e = "A5";

    String[ ] tipoLicencia = {"A1", "A2", "A3", "A4", "A5"};

    public static final String KEY_RUT = "rut";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_APELLIDO = "apellido";
    public static final String KEY_FECHA_NACIMIENTO = "fecha_nacimiento";
    public static final String KEY_DIRECCION = "direccion";
    public static final String KEY_TELEFONO = "telefono";
    public static final String KEY_LICENCIA = "licencia";

    private AppCompatDelegate delegate;
    private EditText editTextRut;

    private EditText editTextRutDos;
    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextFechaNacimiento;
    private EditText editTextDireccion;
    private EditText editTextTelefono;
    private MaterialEditText editTextLicencia;
    private TextView textViewValidador;


    private Button buttonAceptar;
    private Button buttonUpdate;
    String rutURL;
    private String validador;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_modificar_chofer);

        //Finally, let's add the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar_modificar_chofer);
        delegate.setSupportActionBar(toolbar);

        buttonAceptar = (Button) findViewById(R.id.btn_aceptarChoferMod);
        buttonAceptar.setOnClickListener(this);

        buttonUpdate = (Button) findViewById(R.id.btn_modificarChoferMod);
        buttonUpdate.setOnClickListener(this);

        editTextRutDos = (EditText) findViewById(R.id.txt_rutChoferMod);
        editTextNombre = (EditText) findViewById(R.id.txt_nombreChoferMod);
        editTextApellido = (EditText) findViewById(R.id.txt_apellidoChoferMod);
        editTextFechaNacimiento = (EditText) findViewById(R.id.txt_fechaNacChoferMod);
        editTextDireccion = (EditText) findViewById(R.id.txt_direccionChoferMod);
        editTextTelefono = (EditText) findViewById(R.id.txt_telefonoChoferMod);
        editTextLicencia = (MaterialEditText) findViewById(R.id.txt_LicenciaChoferMod);
        textViewValidador = (TextView)findViewById(R.id.TxtValidadorRut);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        findViewsById();
        setDateTimeField();

        requestQueue = Volley.newRequestQueue(getApplicationContext());

    }

    private void findViewsById() {
        editTextFechaNacimiento = (EditText) findViewById(R.id.txt_fechaNacChoferMod);
        editTextFechaNacimiento.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        editTextFechaNacimiento.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextFechaNacimiento.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //METODO PARA DESPLEGAR LOS DATOS DEL CHOFER LUEGO DE INGRESAR EL RUT/////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void sendRequest() {

        final LinearLayout cuadroModificar= (LinearLayout)findViewById(R.id.LayoutModificarChofer);
        editTextRut = (EditText) findViewById(R.id.txt_rutChoferModificar);
        rutURL = editTextRut.getText().toString();

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.text_input_modificarChofer_aceptar);

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

        }else if(comprobarChofer()==true) {
            textViewValidador.setText("");

        } else {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, SHOW_URL+rutURL,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray choferes = response.getJSONArray("choferes");

                                for (int i = 0; i < choferes.length(); i++) {
                                    JSONObject chofer = choferes.getJSONObject(i);

                                    String rut = chofer.optString("rut");
                                    String nombre = chofer.optString("nombre");
                                    String apellido = chofer.optString("apellido");
                                    String fecha_nacimiento = chofer.optString("DATE_FORMAT(fecha_nacimiento, '%d-%m-%Y')");
                                    String direccion = chofer.optString("direccion");
                                    String telefono = chofer.optString("telefono");
                                    String licencia = chofer.optString("licencia");

                                    editTextRutDos.setText(rut);
                                    editTextNombre.setText(nombre);
                                    editTextApellido.setText(apellido);
                                    editTextFechaNacimiento.setText(fecha_nacimiento);
                                    editTextDireccion.setText(direccion);
                                    editTextTelefono.setText(telefono);
                                    editTextLicencia.setText(licencia);

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
                            Toast.makeText(Modificar_chofer.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            requestQueue.add(jsonObjectRequest);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //METODO PARA VALIDAR QUE EL RUT EXISTE EN LA BASE DE DATOS///////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean comprobarChofer(){

        final String rut = editTextRut.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, VALIDAR_RUT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("El rut ingresado no existe")) {
                            Toast.makeText(Modificar_chofer.this, response, Toast.LENGTH_LONG).show();
                            textViewValidador.setText("rut bad");
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Modificar_chofer.this, error.toString(), Toast.LENGTH_LONG).show();
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

        validador = textViewValidador.getText().toString();
        if(validador.equals("rut bad")){
            return true;
        }else{
            return false;

        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //METODO PARA VALIDAR EL TIPO DE LICENCIA INGRESADA, QUE SEA SOLO A1, A2, A3, A4 Y A5.////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean comprobarLicencia(String licencia){
        if(licencia.equals(a) || licencia.equals(b) || licencia.equals(c) || licencia.equals(d) || licencia.equals(e)){
            return true;

        }else{
            return false;
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //METODO PARA ACTUALIZAR LOS DATOS DEL CHOFER ////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void updateVehiculo() {
        final String rut = editTextRut.getText().toString().trim();
        final String nombre = editTextNombre.getText().toString().trim();
        final String apellido = editTextApellido.getText().toString().trim();
        final String fecha_nacimiento = editTextFechaNacimiento.getText().toString().trim();
        final String direccion = editTextDireccion.getText().toString().trim();
        final String telefono = editTextTelefono.getText().toString().trim();
        final String licencia = editTextLicencia.getText().toString().trim();

        final TextInputLayout til2 = (TextInputLayout) findViewById(R.id.text_input_nombreChoferMod);
        final TextInputLayout til3 = (TextInputLayout) findViewById(R.id.text_input_apellidoChoferMod);
        final TextInputLayout til4 = (TextInputLayout) findViewById(R.id.text_input_fechaNacChoferMod);
        final TextInputLayout til5 = (TextInputLayout) findViewById(R.id.text_input_direccionChoferMod);
        final TextInputLayout til6 = (TextInputLayout) findViewById(R.id.text_input_telefonoChoferMod);

        if (editTextNombre.getText().length() == 0) {
            til2.setError("Debe ingresar nombre");

            editTextNombre.addTextChangedListener(new TextWatcher() {

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
        if (editTextApellido.getText().length() == 0) {
            til3.setError("Debe ingresar apellido");

            editTextApellido.addTextChangedListener(new TextWatcher() {

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

        }
        if (editTextFechaNacimiento.getText().length() == 0) {
            til4.setError("Debe ingresar fecha nacimiento");

            editTextFechaNacimiento.addTextChangedListener(new TextWatcher() {

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

        if (editTextDireccion.getText().length() == 0) {
            til5.setError("Debe ingresar dirección");

            editTextDireccion.addTextChangedListener(new TextWatcher() {

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

        }
        if (editTextTelefono.getText().length() == 0) {
            til6.setError("Debe ingresar teléfono");

            editTextTelefono.addTextChangedListener(new TextWatcher() {

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

        }
        if (editTextLicencia.getText().length() == 0) {

            editTextLicencia.addValidator(new RegexpValidator("Debe ingresar licencia", "\\d+"));
            editTextLicencia.validate();
        }

        if (comprobarLicencia(licencia) == false) {

            editTextLicencia.addValidator(new RegexpValidator("Debe ingresar una licencia válida", "\\d+"));
            editTextLicencia.validate();

        } else {

            AlertDialog.Builder alert = new AlertDialog.Builder(Modificar_chofer.this, R.style.DialogTheme);

            alert.setTitle("MODIFICAR");
            alert.setMessage("¿Está seguro que desea modificar este chofer?");
            alert.setIcon(R.mipmap.ic_mode_edit_black_48dp);
            alert.setPositiveButton("MODIFICAR", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(Modificar_chofer.this, response, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Modificar_chofer.this, Menu_choferes.class);
                                    startActivity(intent);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //Toast.makeText(Modificar_chofer.this, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put(KEY_RUT, rut);
                            params.put(KEY_NOMBRE, nombre);
                            params.put(KEY_APELLIDO, apellido);
                            params.put(KEY_FECHA_NACIMIENTO, fecha_nacimiento);
                            params.put(KEY_DIRECCION, direccion);
                            params.put(KEY_TELEFONO, telefono);
                            params.put(KEY_LICENCIA, licencia);
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(Modificar_chofer.this);
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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void onClick(View v) {
        if(v == buttonUpdate){
            updateVehiculo();
        }else if(v == buttonAceptar){
            textViewValidador.setText("");
            sendRequest();
        }else if(v == editTextFechaNacimiento) {
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