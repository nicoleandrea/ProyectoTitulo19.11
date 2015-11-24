package com.example.nicole.smartcoming;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Modificar_usuario extends Activity implements View.OnClickListener, AppCompatCallback {

    private static final String SHOW_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyShowUsuarioUpdate.php?id_usuario=";
    private static final String UPDATE_CORREO_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyUpdateUsuarioCorreo.php?id_usuario=";
    private static final String UPDATE_PASS_URL = "http://smartcomingapp.ddns.net/aplicacion/volleyUpdateUsuarioPass.php?id_usuario=";
    private static final String UPLOAD_URL = "http://smartcomingapp.ddns.net/aplicacion/upload.php?id_usuario=";

    public static final String KEY_CORREO = "correo";
    public static final String KEY_CONTRASENA = "contrasena";
    protected static final int PICK_IMAGE_REQUEST = 1;

    String idUsuarioURL;
    String urlPhoto;
    RequestQueue requestQueue;

    private EditText editTextCorreo;
    private EditText editTextContrasena;
    private String correoVerificar;
    private String contrasenaVerificar;
    private TextView textViewUrlPhoto;
    private Boolean boton = false;
    private Button buttonModificar;
    private ImageButton buttonChoose;
    private ImageView imageView;
    private Bitmap bitmap;
    private String nombre;
    private String correo;
    private String contrasena;

    private String KEY_IMAGE = "photo";
    private String KEY_NAME = "nombre";

    private AppCompatDelegate delegate;

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
        delegate.setContentView(R.layout.activity_modificar_usuario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar_modificar_usuario);
        delegate.setSupportActionBar(toolbar);

        editTextCorreo = (EditText) findViewById(R.id.txt_correo3);
        editTextContrasena = (EditText) findViewById(R.id.txt_contrasena3);

        textViewUrlPhoto = (TextView) findViewById(R.id.TxtUrlImagenUpdate);
        textViewUrlPhoto.setText(urlPhoto);

        imageView = (ImageView) findViewById(R.id.imageView3);
        nombre = "pruebaUploadImage";

        buttonModificar = (Button) findViewById(R.id.btn_modificar);
        buttonModificar.setOnClickListener(this);

        buttonChoose = (ImageButton) findViewById(R.id.img_btn_uploadImagen);
        buttonChoose.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        sendRequest();

        if (textViewUrlPhoto.getText().toString().length() > 0) {
            imageView = (ImageView) findViewById(R.id.imageView3);
            new DescargarImagen().execute(imageView);
        } else {
            System.out.println("no hay imagen");
        }
    }

    class DescargarImagen extends AsyncTask<ImageView, Void, Bitmap> {

        ImageView imagen;
        Bitmap bitm;
        String url = textViewUrlPhoto.getText().toString();

        @Override
        protected Bitmap doInBackground(ImageView... params) {
            imagen = params[0];
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                bitm = BitmapFactory.decodeStream(conn.getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
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

                                String correo = usuario.optString("correo");
                                String contrasena = usuario.optString("contrasena");

                                editTextCorreo.setText(correo);
                                editTextContrasena.setText(contrasena);

                                contrasenaVerificar = editTextContrasena.getText().toString();
                                correoVerificar = editTextCorreo.getText().toString();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Modificar_usuario.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void updateUsuarioCorreo() {
        final String correo = editTextCorreo.getText().toString().trim();

        final TextInputLayout til2 = (TextInputLayout) findViewById(R.id.text_input_correo3);

        if (editTextCorreo.getText().length() == 0) {
            til2.setError("Debe ingresar correo");

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

                    til2.setError(null);
                }
            });

        } else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_CORREO_URL + idUsuarioURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("El correo ingresado ya existe")) {
                                Toast.makeText(Modificar_usuario.this, response, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Modificar_usuario.this, response, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Modificar_usuario.this, Inicio.class);
                                startActivity(intent);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Modificar_usuario.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_CORREO, correo);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    private void updateUsuarioPass() {
        final String contrasena = editTextContrasena.getText().toString().trim();

        final TextInputLayout til3 = (TextInputLayout) findViewById(R.id.text_input_contrasena3);

        if (editTextContrasena.getText().length() == 0) {
            til3.setError("Debe ingresar contraseña");

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

                    til3.setError(null);
                }
            });

        } else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_PASS_URL + idUsuarioURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(Modificar_usuario.this, response, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Modificar_usuario.this, Inicio.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Modificar_usuario.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_CONTRASENA, contrasena);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo imagen...", "Espere por favor...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL + idUsuarioURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        Toast.makeText(Modificar_usuario.this, s, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Modificar_usuario.this, Menu_usuario.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Modificar_usuario.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String foto = getStringImage(bitmap);
                String name = nombre;

                Map<String, String> params = new Hashtable<String, String>();

                params.put(KEY_IMAGE, foto);
                params.put(KEY_NAME, name);
                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS = 30000;

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showFileChooser() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.putExtra("crop", "true");
        photoPickerIntent.putExtra("return-data", true);
        photoPickerIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {

        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                if (resultCode == RESULT_OK) {
                    if (imageReturnedIntent != null) {
                        Bundle extras = imageReturnedIntent.getExtras();
                        bitmap = extras.getParcelable("data");
                        imageView.setImageBitmap(bitmap);
                    }
                }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            boton = true;
            showFileChooser();
        }

        if (v == buttonModificar) {

            if (boton == true) {
                uploadImage();
                boton = false;

            } else {
                System.out.println("La imagen no ha sido modificada");
            }

            correo = editTextCorreo.getText().toString();
            if (!correoVerificar.equals(correo)) {
                updateUsuarioCorreo();
            } else {
                System.out.println("El correo no ha sido modificado");
            }

            contrasena = editTextContrasena.getText().toString();
            if (!contrasenaVerificar.equals(contrasena)) {
                updateUsuarioPass();
            } else {
                System.out.println("La contrasena no ha sido modificada");
            }
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