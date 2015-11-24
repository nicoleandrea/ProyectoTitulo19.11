package JSONParse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseChoferesJSON {
    public static String[] nombre;
    public static String[] rut;
    public static String[] fecha_nacimiento;
    public static String[] direccion;
    public static String[] telefono;
    public static String[] licencia;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_RUT = "rut";
    public static final String KEY_FECHA_NACIMIENTO = "fecha_nacimiento";
    public static final String KEY_DIRECCION = "direccion";
    public static final String KEY_TELEFONO = "telefono";
    public static final String KEY_LICENCIA = "licencia";

    private JSONArray choferes = null;

    private String json;

    public ParseChoferesJSON(String json){
        this.json = json;
    }

    public void parseChoferesJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            choferes = jsonObject.getJSONArray(JSON_ARRAY);

            nombre = new String[choferes.length()];
            rut = new String[choferes.length()];
            fecha_nacimiento = new String[choferes.length()];
            direccion = new String[choferes.length()];
            telefono = new String[choferes.length()];
            licencia = new String[choferes.length()];

            for(int i=0;i<choferes.length();i++){
                JSONObject jo = choferes.getJSONObject(i);
                nombre[i] = jo.getString(KEY_NOMBRE);
                rut[i] = jo.getString(KEY_RUT);
                fecha_nacimiento[i] = jo.getString(KEY_FECHA_NACIMIENTO);
                direccion[i] = jo.getString(KEY_DIRECCION);
                telefono[i] = jo.getString(KEY_TELEFONO);
                licencia[i] = jo.getString(KEY_LICENCIA);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
