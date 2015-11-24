package JSONParse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseVehiculosJSON {
    public static String[] patente;
    public static String[] marca;
    public static String[] modelo;
    public static String[] anno;
    public static String[] nombre_duenno;
    public static String[] kilometraje;
    public static String[] revision_tecnica;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_PATENTE = "patente";
    public static final String KEY_MARCA = "marca";
    public static final String KEY_MODELO = "modelo";
    public static final String KEY_ANNO = "anno";
    public static final String KEY_NOMBRE_DUENNO = "nombre_duenno";
    public static final String KEY_KILOMETRAJE = "kilometraje";
    public static final String KEY_REVISION_TECNICA = "revision_tecnica";

    private JSONArray vehiculos = null;

    private String json;

    public ParseVehiculosJSON(String json){
        this.json = json;
    }

    public void parseVehiculosJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            vehiculos = jsonObject.getJSONArray(JSON_ARRAY);

            patente = new String[vehiculos.length()];
            marca = new String[vehiculos.length()];
            modelo = new String[vehiculos.length()];
            anno = new String[vehiculos.length()];
            nombre_duenno = new String[vehiculos.length()];
            kilometraje = new String[vehiculos.length()];
            revision_tecnica = new String[vehiculos.length()];

            for(int i=0;i<vehiculos.length();i++){
                JSONObject jo = vehiculos.getJSONObject(i);
                patente[i] = jo.getString(KEY_PATENTE);
                marca[i] = jo.getString(KEY_MARCA);
                modelo[i] = jo.getString(KEY_MODELO);
                anno[i] = jo.getString(KEY_ANNO);
                nombre_duenno[i] = jo.getString(KEY_NOMBRE_DUENNO);
                kilometraje[i] = jo.getString(KEY_KILOMETRAJE);
                revision_tecnica[i] = jo.getString(KEY_REVISION_TECNICA);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}