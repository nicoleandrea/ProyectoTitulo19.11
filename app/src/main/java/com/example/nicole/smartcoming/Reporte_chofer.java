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
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.widget.DatePicker;
import android.text.InputType;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import CustomsList.CustomListReporteChoferes;
import JSONParse.ParseReporteChoferJSON;

public class Reporte_chofer extends Activity implements View.OnClickListener, AppCompatCallback {

    public static final String JSON_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyReporteChoferes.php";
    public static final String KEY_RUT="rut";
    public static final String KEY_FECHA="fecha";

    private AppCompatDelegate delegate;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    private EditText editTextRut;
    private EditText editTextFecha;
    private Button buttonAceptar;
    private ListView listReporteChofer;

    private String rut;
    private String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_reporte_chofer);

        //Finally, let's add the Toolbar
        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar_reporte_chofer);
        delegate.setSupportActionBar(toolbar);

        editTextRut = (EditText) findViewById(R.id.txt_rutChoferReporte);
        editTextRut.requestFocus();
        editTextFecha = (EditText) findViewById(R.id.txt_fechaReporteChofer);

        buttonAceptar = (Button) findViewById(R.id.btn_aceptarChoferRep);
        buttonAceptar.setOnClickListener(this);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        findViewsById();
        setDateTimeField();

    }

    private void findViewsById() {
        editTextFecha = (EditText) findViewById(R.id.txt_fechaReporteChofer);
        editTextFecha.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        editTextFecha.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextFecha.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    private void sendRequest(){
        rut = editTextRut.getText().toString().trim();
        fecha = editTextFecha.getText().toString().trim();

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.text_input_rutChoferReporte);
        final TextInputLayout til2 = (TextInputLayout) findViewById(R.id.text_input_fechaReporteChofer);

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

        }if (editTextFecha.getText().length() == 0) {
            til2.setError("Debe ingresar fecha");

            editTextFecha.addTextChangedListener(new TextWatcher() {

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

        } else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("No se han encontrado registros")) {
                                Toast.makeText(Reporte_chofer.this, response, Toast.LENGTH_LONG).show();

                            } else {
                                showJSON(response);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Reporte_chofer.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(KEY_RUT, rut);
                    map.put(KEY_FECHA, fecha);
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Reporte_chofer.this);
            requestQueue.add(stringRequest);
        }
    }

    private void showJSON(String json){
        listReporteChofer = (ListView) findViewById(R.id.listViewReporteChofer);

        ParseReporteChoferJSON pj = new ParseReporteChoferJSON(json);
        pj.parseReporteChoferJSON();
        CustomListReporteChoferes cl = new CustomListReporteChoferes(this, ParseReporteChoferJSON.fecha_marcacion, ParseReporteChoferJSON.nombre,ParseReporteChoferJSON.rut, ParseReporteChoferJSON.tiempo_trabajado, ParseReporteChoferJSON.tiempo_descansado);
        listReporteChofer.setAdapter(cl);
    }

    @Override
    public void onClick(View view) {
        if(view == editTextFecha) {
            fromDatePickerDialog.show();
        }else {
            sendRequest();
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