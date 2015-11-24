package JSONParse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseReporteVehiculoJSON {
    public static String[] fecha;
    public static String[] nombre;
    public static String[] kilometraje;
   /* public static String[] velocidad_maxima;
    public static String[] velocidad_minima;
    public static String[] velocidad_promedio;*/

    public static final String JSON_ARRAY = "result";
    public static final String KEY_FECHA = "fecha";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_KILOMETRAJE = "kilometraje";
    /*public static final String KEY_VELOCIDAD_MAXIMA = "velocidad_maxima";
    public static final String KEY_VELOCIDAD_MINIMA = "velocidad_minima";
    public static final String KEY_VELOCIDAD_PROMEDIO = "velocidad_promedio";*/

    private JSONArray vehiculos = null;

    private String json;

    public ParseReporteVehiculoJSON(String json){
        this.json = json;
    }

    public void parseReporteVehiculoJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            vehiculos = jsonObject.getJSONArray(JSON_ARRAY);

            fecha = new String[vehiculos.length()];
            nombre = new String[vehiculos.length()];
            kilometraje = new String[vehiculos.length()];
            /*velocidad_maxima = new String[vehiculos.length()];
            velocidad_minima = new String[vehiculos.length()];
            velocidad_promedio = new String[vehiculos.length()];*/

            for(int i=0;i<vehiculos.length();i++){
                JSONObject jo = vehiculos.getJSONObject(i);
                fecha[i] = jo.getString(KEY_FECHA);
                nombre[i] = jo.getString(KEY_NOMBRE);
                kilometraje[i] = jo.getString(KEY_KILOMETRAJE);
                /*velocidad_maxima[i] = jo.getString(KEY_VELOCIDAD_MAXIMA);
                velocidad_minima[i] = jo.getString(KEY_VELOCIDAD_MINIMA);
                velocidad_promedio[i] = jo.getString(KEY_VELOCIDAD_PROMEDIO);*/

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

