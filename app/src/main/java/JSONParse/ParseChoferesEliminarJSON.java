package JSONParse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseChoferesEliminarJSON {
    public static String[] nombre;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_NOMBRE = "nombre";

    private JSONArray choferes = null;

    private String json;

    public ParseChoferesEliminarJSON(String json){
        this.json = json;
    }

    public void parseChoferesEliminarJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            choferes = jsonObject.getJSONArray(JSON_ARRAY);

            nombre = new String[choferes.length()];

            for(int i=0;i<choferes.length();i++){
                JSONObject jo = choferes.getJSONObject(i);
                nombre[i] = jo.getString(KEY_NOMBRE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
