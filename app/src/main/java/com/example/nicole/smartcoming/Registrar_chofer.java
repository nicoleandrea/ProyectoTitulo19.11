package com.example.nicole.smartcoming;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Registrar_chofer extends Activity implements View.OnClickListener, AppCompatCallback {

    private AppCompatDelegate delegate;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    private static final String REGISTER_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyRegisterChofer.php";

    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_APELLIDO = "apellido";
    public static final String KEY_RUT = "rut";
    public static final String KEY_FECHA_NACIMIENTO = "fecha_nacimiento";
    public static final String KEY_DIRECCION = "direccion";
    public static final String KEY_TELEFONO = "telefono";
    public static final String KEY_LICENCIA = "licencia";

    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextRut;
    private EditText editTextFechaNacimiento;
    private EditText editTextDireccion;
    private EditText editTextTelefono;
    private Spinner spinnerLicencia;
    private TextView textViewErrorSpinner;
    private Button buttonRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_registrar_chofer);

        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar_registrar_chofer);
        delegate.setSupportActionBar(toolbar);
        //----------------------------------------------------------------------

        editTextNombre = (EditText) findViewById(R.id.txt_nombreChofer);
        editTextNombre.requestFocus();
        editTextApellido = (EditText) findViewById(R.id.txt_apellidoChofer);
        editTextRut = (EditText) findViewById(R.id.txt_rutChofer);
        editTextFechaNacimiento = (EditText) findViewById(R.id.txt_fechaNacChofer);
        editTextDireccion = (EditText) findViewById(R.id.txt_direccionChofer);
        editTextTelefono = (EditText) findViewById(R.id.txt_telefonoChofer);
        textViewErrorSpinner = (TextView) findViewById(R.id.lbl_errorSpinner2);
        spinnerLicencia = (Spinner) findViewById(R.id.spn_licenciaChofer);

        String[] opciones = {"Seleccione licencia", "A1", "A2", "A3", "A4", "A5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, opciones);
        spinnerLicencia.setAdapter(adapter);

        buttonRegister = (Button) findViewById(R.id.btn_registrarChofer);
        buttonRegister.setOnClickListener(this);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        findViewsById();
        setDateTimeField();
    }

    private void findViewsById() {
        editTextFechaNacimiento = (EditText) findViewById(R.id.txt_fechaNacChofer);
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

    private void registerChofer() {
        final String nombre = editTextNombre.getText().toString().trim();
        final String apellido = editTextApellido.getText().toString().trim();
        final String rut = editTextRut.getText().toString().trim();
        final String fecha_nacimiento = editTextFechaNacimiento.getText().toString().trim();
        final String direccion = editTextDireccion.getText().toString().trim();
        final String telefono = editTextTelefono.getText().toString().trim();
        final String licencia = spinnerLicencia.getSelectedItem().toString().trim();

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.text_input_nombreChofer);
        final TextInputLayout til2 = (TextInputLayout) findViewById(R.id.text_input_apellidoChofer);
        final TextInputLayout til3 = (TextInputLayout) findViewById(R.id.text_input_rutChofer);
        final TextInputLayout til4 = (TextInputLayout) findViewById(R.id.text_input_fechaNacChofer);
        final TextInputLayout til5 = (TextInputLayout) findViewById(R.id.text_input_direccionChofer);
        final TextInputLayout til6 = (TextInputLayout) findViewById(R.id.text_input_telefonoChofer);

        if (editTextNombre.getText().length() == 0) {
            til1.setError("Debe ingresar nombre");

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

                    til1.setError(null);
                }
            });
        }

        if (editTextApellido.getText().length() == 0) {
            til2.setError("Debe ingresar apellido");

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

                    til2.setError(null);
                }
            });


        } if (editTextRut.getText().length() == 0) {
            til3.setError("Debe ingresar rut");

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

                    til3.setError(null);
                }
            });

        }if (editTextFechaNacimiento.getText().length() == 0) {
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

        } if (editTextTelefono.getText().length() == 0) {
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

        }if (licencia.equals("Seleccione licencia")) {
            textViewErrorSpinner.setText("Debe seleccionar licencia");

            spinnerLicencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parent, View itemSelected,
                                           int selectedItemPosition, long selectedId) {

                    spinnerLicencia.setSelection(selectedItemPosition);
                    textViewErrorSpinner.setText("");
                }

                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });


        }else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("El rut ingresado no es valido")) {
                                Toast.makeText(Registrar_chofer.this, response, Toast.LENGTH_LONG).show();
                                editTextRut.setText("");
                            } else {
                                Toast.makeText(Registrar_chofer.this, response, Toast.LENGTH_LONG).show();
                                editTextNombre.setText("");
                                editTextNombre.requestFocus();
                                editTextApellido.setText("");
                                editTextRut.setText("");
                                editTextFechaNacimiento.setText("");
                                editTextDireccion.setText("");
                                editTextTelefono.setText("");
                                String[] opciones = {"Seleccione licencia", "A1", "A2", "A3", "A4", "A5"};
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Registrar_chofer.this, R.layout.spinner_item, opciones);
                                spinnerLicencia.setAdapter(adapter);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Registrar_chofer.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_NOMBRE, nombre);
                    params.put(KEY_APELLIDO, apellido);
                    params.put(KEY_RUT, rut);
                    params.put(KEY_FECHA_NACIMIENTO, fecha_nacimiento);
                    params.put(KEY_DIRECCION, direccion);
                    params.put(KEY_TELEFONO, telefono);
                    params.put(KEY_LICENCIA, licencia);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerChofer();
        }else if(v == editTextFechaNacimiento){
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