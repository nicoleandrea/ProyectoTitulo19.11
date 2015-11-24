package JSONParse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseVehiculosEliminarJSON {
    public static String[] vehiculo;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_VEHICULO= "vehiculo";

    private JSONArray vehiculos = null;

    private String json;

    public ParseVehiculosEliminarJSON(String json){
        this.json = json;
    }

    public void parseVehiculosEliminarJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            vehiculos = jsonObject.getJSONArray(JSON_ARRAY);

            vehiculo = new String[vehiculos.length()];

            for(int i=0;i<vehiculos.length();i++){
                JSONObject jo = vehiculos.getJSONObject(i);
                vehiculo[i] = jo.getString(KEY_VEHICULO);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}