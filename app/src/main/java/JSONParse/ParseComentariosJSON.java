package JSONParse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseComentariosJSON {
    public static String[] nombre;
    public static String[] texto;
    public static String[] fecha;
    public static String[] fechaHora;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_TEXTO = "texto";
    public static final String KEY_FECHA = "fecha";
    public static final String KEY_FECHAHORA = "fechaHora";

    private JSONArray coments = null;

    private String json;

    public ParseComentariosJSON(String json){
        this.json = json;
    }

    public void parseComentariosJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            coments = jsonObject.getJSONArray(JSON_ARRAY);

            nombre = new String[coments.length()];
            texto = new String[coments.length()];
            fecha = new String[coments.length()];
            fechaHora = new String[coments.length()];

            for(int i=0;i<coments.length();i++){
                JSONObject jo = coments.getJSONObject(i);
                nombre[i] = jo.getString(KEY_NOMBRE);
                texto[i] = jo.getString(KEY_TEXTO);
                fecha[i] = jo.getString(KEY_FECHA);
                fechaHora[i] = jo.getString(KEY_FECHAHORA);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
