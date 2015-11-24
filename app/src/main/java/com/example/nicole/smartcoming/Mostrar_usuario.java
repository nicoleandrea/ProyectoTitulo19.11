package com.example.nicole.smartcoming;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import java.net.MalformedURLException;
import android.app.Activity;
import android.support.v7.view.ActionMode;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageButton;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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

public class Mostrar_usuario extends Activity implements AppCompatCallback, View.OnClickListener {

    private AppCompatDelegate delegate;
    private ImageView imageView;
    public static final String SHOW_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyShowUsuario.php?id_usuario=";
    public static final String ELIMINAR_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyEliminarUsuario.php?id_usuario=";
    private ImageButton buttonEliminar;

    private TextView textViewNombre;
    private TextView textViewCorreo;
    private TextView textViewUrlPhoto;

    String idUsuarioURL;
    String urlPhoto;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final ClaseGlobal globalVariable = (ClaseGlobal) getApplicationContext();
        final int id_usuario = globalVariable.getId_usuario();
        idUsuarioURL = Integer.toString(id_usuario);

        final String url_photo = globalVariable.getUrlPhoto();
        urlPhoto = url_photo;

        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_mostrar_usuario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar_mostrar_usuario);
        delegate.setSupportActionBar(toolbar);

        buttonEliminar = (ImageButton) findViewById(R.id.img_btn_eliminarCuenta);
        buttonEliminar.setOnClickListener(this);

        textViewNombre = (TextView) findViewById(R.id.lbl_nombre);
        textViewCorreo= (TextView) findViewById(R.id.lbl_correo);
        textViewUrlPhoto = (TextView)findViewById(R.id.TxtUrlPhoto);
        textViewUrlPhoto.setText(urlPhoto);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        sendRequest();


        if (textViewUrlPhoto.getText().toString().length()>0){
            imageView = (ImageView) findViewById(R.id.imageView2);
            new DescargarImagen().execute(imageView);
        }else{
            System.out.println("no hay imagen");
        }

    }


    class DescargarImagen extends AsyncTask<ImageView, Void, Bitmap>{

        ImageView imagen;
        Bitmap bitm;
        String url = textViewUrlPhoto.getText().toString();

        @Override
        protected Bitmap doInBackground(ImageView... params) {
            imagen = params[0];
            try{
                URL imageUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
                conn.connect();
                bitm = BitmapFactory.decodeStream(conn.getInputStream());
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return bitm;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imagen.setImageBitmap(result);
            super.onPostExecute(result);
        }
    }

    private void sendRequest() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, SHOW_URL + idUsuarioURL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray usuarios = response.getJSONArray("usuarios");

                            for (int i = 0; i < usuarios.length(); i++) {
                                JSONObject usuario = usuarios.getJSONObject(i);

                                String nombre = usuario.optString("nombre");
                                String correo = usuario.optString("correo");

                                textViewNombre.setText(nombre);
                                textViewCorreo.setText(correo);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Mostrar_usuario.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void eliminarUsuario() {

        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(Mostrar_usuario.this, R.style.DialogTheme);

        alert.setTitle("ELIMINAR");
        alert.setMessage("¿Está seguro que desea eliminar su cuenta?");
        alert.setIcon(R.mipmap.ic_delete_black_48dp);
        alert.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ELIMINAR_URL+idUsuarioURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(Mostrar_usuario.this, response, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Mostrar_usuario.this, Inicio.class);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Mostrar_usuario.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                        /*@Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put(KEY_RUT, rut);
                            return map;
                        }*/
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Mostrar_usuario.this);
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


    public void modificarDatos (View v){
        Intent act = new Intent(this, Modificar_usuario.class);
        startActivity(act);
    }

    @Override
    public void onClick(View v) {
        eliminarUsuario();
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