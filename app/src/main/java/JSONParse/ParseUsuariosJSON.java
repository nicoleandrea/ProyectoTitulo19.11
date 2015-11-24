package JSONParse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseUsuariosJSON {
    public static String[] nombre;
    public static String[] provincia;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_PROVINCIA = "provincia";

    private JSONArray users = null;

    private String json;

    public ParseUsuariosJSON(String json){
        this.json = json;
    }

    public void parseUsuariosJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            nombre = new String[users.length()];
            provincia = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                nombre[i] = jo.getString(KEY_NOMBRE);
                provincia[i] = jo.getString(KEY_PROVINCIA);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
