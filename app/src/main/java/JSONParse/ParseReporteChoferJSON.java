package JSONParse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseReporteChoferJSON {
    public static String[] fecha_marcacion;
    public static String[] nombre;
    public static String[] rut;
    public static String[] tiempo_trabajado;
    public static String[] tiempo_descansado;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_FECHA_MARCACION = "fecha_marcacion";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_RUT = "rut";
    public static final String KEY_TIEMPO_TRABAJADO = "tiempo_trabajado";
    public static final String KEY_TIEMPO_DESCANSADO = "tiempo_descansado";

    private JSONArray choferes = null;

    private String json;

    public ParseReporteChoferJSON(String json){
        this.json = json;
    }

    public void parseReporteChoferJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            choferes = jsonObject.getJSONArray(JSON_ARRAY);

            fecha_marcacion = new String[choferes.length()];
            nombre = new String[choferes.length()];
            rut = new String[choferes.length()];
            tiempo_trabajado = new String[choferes.length()];
            tiempo_descansado = new String[choferes.length()];

            for(int i=0;i<choferes.length();i++){
                JSONObject jo = choferes.getJSONObject(i);
                fecha_marcacion[i] = jo.getString(KEY_FECHA_MARCACION);
                nombre[i] = jo.getString(KEY_NOMBRE);
                rut[i] = jo.getString(KEY_RUT);
                tiempo_trabajado[i] = jo.getString(KEY_TIEMPO_TRABAJADO);
                tiempo_descansado[i] = jo.getString(KEY_TIEMPO_DESCANSADO);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
