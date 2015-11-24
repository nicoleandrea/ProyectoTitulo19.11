package JSONParse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseHorariosJSON {
    public static String[] hora;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_HORA = "hora";

    private JSONArray hours = null;

    private String json;

    public ParseHorariosJSON(String json){
        this.json = json;
    }

    public void parseHorariosJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            hours = jsonObject.getJSONArray(JSON_ARRAY);

            hora = new String[hours.length()];

            for(int i=0;i<hours.length();i++){
                JSONObject jo = hours.getJSONObject(i);
                hora[i] = jo.getString(KEY_HORA);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
