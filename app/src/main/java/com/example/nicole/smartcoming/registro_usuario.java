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

public class registro_usuario extends Activity implements View.OnClickListener, AppCompatCallback {

    private AppCompatDelegate delegate;

    private static final String REGISTER_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyRegister.php";

    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_APELLIDO = "apellido";
    public static final String KEY_CORREO = "correo";
    public static final String KEY_CONTRASENA = "contrasena";
    public static final String KEY_PROVINCIA = "provincia";

    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextCorreo;
    private EditText editTextContrasena;
    private Spinner spinnerProvincia;
    private TextView textViewErrorSpinner;
    private Button buttonRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_registro_usuario);

        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar_registrar_usuario);
        delegate.setSupportActionBar(toolbar);
        //----------------------------------------------------------------------

        editTextNombre = (EditText) findViewById(R.id.txt_nombre);
        editTextNombre.requestFocus();
        editTextApellido = (EditText) findViewById(R.id.txt_apellido);
        editTextCorreo = (EditText) findViewById(R.id.txt_correo);
        editTextContrasena = (EditText) findViewById(R.id.txt_contrasena);
        textViewErrorSpinner = (TextView) findViewById(R.id.lbl_errorSpinner1);
        spinnerProvincia = (Spinner) findViewById(R.id.spn_provincia);

        String[] opciones = {"Seleccione provincia", "Marga marga", "Quillota", "San Antonio", "Valparaíso"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, opciones);
        spinnerProvincia.setAdapter(adapter);

        buttonRegister = (Button) findViewById(R.id.btn_registrar);
        buttonRegister.setOnClickListener(this);
    }

    private void registerChofer() {
        final String nombre = editTextNombre.getText().toString().trim();
        final String apellido = editTextApellido.getText().toString().trim();
        final String correo = editTextCorreo.getText().toString().trim();
        final String contrasena = editTextContrasena.getText().toString().trim();
        final String provincia = spinnerProvincia.getSelectedItem().toString().trim();

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.text_input_nombre);
        final TextInputLayout til2 = (TextInputLayout) findViewById(R.id.text_input_apellido);
        final TextInputLayout til3 = (TextInputLayout) findViewById(R.id.text_input_correo);
        final TextInputLayout til4 = (TextInputLayout) findViewById(R.id.text_input_contrasena);

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


        } if (editTextCorreo.getText().length() == 0) {
            til3.setError("Debe ingresar correo");

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

                    til3.setError(null);
                }
            });

        }if (editTextContrasena.getText().length() == 0) {
            til4.setError("Debe ingresar contraseña");

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

                    til4.setError(null);
                }
            });

        }if (provincia.equals("Seleccione provincia")) {
            textViewErrorSpinner.setText("Debe seleccionar provincia");

            spinnerProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parent, View itemSelected,
                                           int selectedItemPosition, long selectedId) {

                    spinnerProvincia.setSelection(selectedItemPosition);
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
                            if (response.equals("El correo ingresado no es valido")) {
                                Toast.makeText(registro_usuario.this, response, Toast.LENGTH_LONG).show();
                                editTextCorreo.setText("");
                            } else {
                                Toast.makeText(registro_usuario.this, response, Toast.LENGTH_LONG).show();
                                editTextNombre.setText("");
                                editTextNombre.requestFocus();
                                editTextApellido.setText("");
                                editTextCorreo.setText("");
                                editTextContrasena.setText("");
                                String[] opciones = {"Seleccione provincia", "Marga marga", "Quillota", "San Antonio", "Valparaíso"};
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(registro_usuario.this, R.layout.spinner_item, opciones);
                                spinnerProvincia.setAdapter(adapter);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(registro_usuario.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_NOMBRE, nombre);
                    params.put(KEY_APELLIDO, apellido);
                    params.put(KEY_CORREO, correo);
                    params.put(KEY_CONTRASENA, contrasena);
                    params.put(KEY_PROVINCIA, provincia);
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
/*import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;

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

import java.util.HashMap;
import java.util.Map;

public class registro_usuario extends Activity implements View.OnClickListener, AppCompatCallback {

    private AppCompatDelegate delegate;

    private static final String REGISTER_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyRegister.php";

    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_APELLIDO = "apellido";
    public static final String KEY_CORREO = "correo";
    public static final String KEY_PASSWORD = "contrasena";
    public static final String KEY_PROVINCIA = "provincia";

    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextCorreo;
    private EditText editTextContrasena;
    private Spinner spinnerProvincia;
    private TextView textViewErrorSpinner;

    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //IMPLEMENTACION TOOLBAR
        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_registro_usuario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar_registrar_usuario);
        delegate.setSupportActionBar(toolbar);
        //----------------------------------------------------------------------

        editTextNombre = (EditText) findViewById(R.id.txt_nombre);
        editTextNombre.requestFocus();
        editTextApellido = (EditText) findViewById(R.id.txt_apellido);
        editTextCorreo = (EditText) findViewById(R.id.txt_correo);
        editTextContrasena = (EditText) findViewById(R.id.txt_contrasena);

        textViewErrorSpinner = (TextView) findViewById(R.id.lbl_errorSpinner1);

        spinnerProvincia = (Spinner) findViewById(R.id.spn_provincia);
        String[] opciones = {"Seleccione provincia", "Marga marga", "Quillota", "San Antonio", "Valparaíso"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, opciones);
        spinnerProvincia.setAdapter(adapter);

        buttonRegister = (Button) findViewById(R.id.btn_registrar);
        buttonRegister.setOnClickListener(this);
    }

    private void registerUser() {
        final String nombre = editTextNombre.getText().toString().trim();
        final String apellido = editTextApellido.getText().toString().trim();
        final String correo = editTextCorreo.getText().toString().trim();
        final String contrasena = editTextContrasena.getText().toString().trim();
        final String provincia = spinnerProvincia.getSelectedItem().toString().trim();

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.text_input_nombre);
        final TextInputLayout til2 = (TextInputLayout) findViewById(R.id.text_input_apellido);
        final TextInputLayout til3 = (TextInputLayout) findViewById(R.id.text_input_correo);
        final TextInputLayout til4 = (TextInputLayout) findViewById(R.id.text_input_contrasena);

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


        }
        if (editTextCorreo.getText().length() == 0) {
            til3.setError("Debe ingresar correo");

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

                    til3.setError(null);
                }
            });

        }
        if (editTextContrasena.getText().length() == 0) {
            til4.setError("Debe ingresar contraseña");

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

                    til4.setError(null);
                }
            });

        }
        if (provincia.equals("Seleccione provincia")) {
            textViewErrorSpinner.setText("Debe seleccionar provincia");

            spinnerProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parent, View itemSelected,
                                           int selectedItemPosition, long selectedId) {

                    spinnerProvincia.setSelection(selectedItemPosition);
                    textViewErrorSpinner.setText("");
                }

                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("El rut ingresado no es valido")) {
                                Toast.makeText(registro_usuario.this, response, Toast.LENGTH_LONG).show();
                                editTextCorreo.setText("");
                            } else {
                                Toast.makeText(registro_usuario.this, response, Toast.LENGTH_LONG).show();
                                editTextNombre.setText("");
                                editTextNombre.requestFocus();
                                editTextApellido.setText("");
                                editTextCorreo.setText("");
                                editTextContrasena.setText("");
                                String[] opciones = {"Seleccione provincia", "Marga marga", "Quillota", "San Antonio", "Valparaíso"};
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(registro_usuario.this, R.layout.spinner_item, opciones);
                                spinnerProvincia.setAdapter(adapter);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(registro_usuario.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_NOMBRE, nombre);
                    params.put(KEY_APELLIDO, apellido);
                    params.put(KEY_CORREO, correo);
                    params.put(KEY_PASSWORD, contrasena);
                    params.put(KEY_PROVINCIA, provincia);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerUser();
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
}*/