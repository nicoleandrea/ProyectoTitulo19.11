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

public class Registrar_vehiculo extends Activity implements View.OnClickListener, AppCompatCallback {

    private AppCompatDelegate delegate;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    private static final String REGISTER_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyRegisterVehiculo.php";

    public static final String KEY_PATENTE = "patente";
    public static final String KEY_MARCA = "marca";
    public static final String KEY_MODELO = "modelo";
    public static final String KEY_ANNO = "anno";
    public static final String KEY_NOMBRE_DUENNO = "nombre_duenno";
    public static final String KEY_KILOMETRAJE = "kilometraje";
    public static final String KEY_REVISION_TECNICA = "revision_tecnica";

    private EditText editTextPatente;
    private EditText editTextMarca;
    private EditText editTextModelo;
    private EditText editTextAnno;
    private EditText editTextNombreDuenno;
    private EditText editTextKilometraje;
    private EditText editTextRevisionTecnica;
    private Button buttonRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_registrar_vehiculo);

        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar_registrar_vehiculo);
        delegate.setSupportActionBar(toolbar);
        //----------------------------------------------------------------------

        editTextPatente = (EditText) findViewById(R.id.txt_patente_vehiculo);
        editTextPatente.requestFocus();
        editTextMarca = (EditText) findViewById(R.id.txt_marca_vehiculo);
        editTextModelo= (EditText) findViewById(R.id.txt_modelo_vehiculo);
        editTextAnno= (EditText) findViewById(R.id.txt_anno_vehiculo);
        editTextNombreDuenno= (EditText) findViewById(R.id.txt_duenno_vehiculo);
        editTextKilometraje= (EditText) findViewById(R.id.txt_kilometraje_vehiculo);
        editTextRevisionTecnica= (EditText) findViewById(R.id.txt_revTecnica_vehiculo);

        buttonRegister = (Button) findViewById(R.id.btn_registrar_vehiculo);
        buttonRegister.setOnClickListener(this);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        findViewsById();
        setDateTimeField();
    }

    private void findViewsById() {
        editTextRevisionTecnica = (EditText) findViewById(R.id.txt_revTecnica_vehiculo);
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

    private void registerVehiculo() {
        final String patente = editTextPatente.getText().toString().trim().toUpperCase();
        final String marca = editTextMarca.getText().toString().trim();
        final String modelo = editTextModelo.getText().toString().trim();
        final String anno = editTextAnno.getText().toString().trim();
        final String nombre_duenno = editTextNombreDuenno.getText().toString().trim();
        final String kilometraje = editTextKilometraje.getText().toString().trim();
        final String revision_tecnica = editTextRevisionTecnica.getText().toString().trim();

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.text_input_patente_vehiculo);
        final TextInputLayout til2 = (TextInputLayout) findViewById(R.id.text_input_marca_vehiculo);
        final TextInputLayout til3 = (TextInputLayout) findViewById(R.id.text_input_modelo_vehiculo);
        final TextInputLayout til4 = (TextInputLayout) findViewById(R.id.text_input_anno_vehiculo);
        final TextInputLayout til5 = (TextInputLayout) findViewById(R.id.text_input_duenno_vehiculo);
        final TextInputLayout til6 = (TextInputLayout) findViewById(R.id.text_input_kilometraje_vehiculo);
        final TextInputLayout til7 = (TextInputLayout) findViewById(R.id.text_input_revTecnica_vehiculo);

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
        }

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

            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(Registrar_vehiculo.this, response, Toast.LENGTH_LONG).show();

                            editTextPatente.setText("");
                            editTextPatente.requestFocus();
                            editTextMarca.setText("");
                            editTextModelo.setText("");
                            editTextAnno.setText("");
                            editTextNombreDuenno.setText("");
                            editTextKilometraje.setText("");
                            editTextRevisionTecnica.setText("");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Registrar_vehiculo.this, error.toString(), Toast.LENGTH_LONG).show();
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

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerVehiculo();
        }else if(v == editTextRevisionTecnica){
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