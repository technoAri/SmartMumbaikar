package com.example.hppc.smartmum;

import android.app.Application;
import android.provider.Settings;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by HP PC on 4/7/2016.
 */
public class VollyController {

    public static String[] ids;
    public static String[] names;
    public static String[] admin_id;
    public static String[] time;

    public static final String JSON_ARRAY = "0";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADMIN_ID = "admin_id";
    public static final String KEY_TIME = "time";

    private JSONArray group = null;

    private String json;
    //private String json1=(json.substring(49)); // = "http://smartmumbaikar.com/sharingo/publicgrouplist.php?gid=2";

    public VollyController(String json){
        this.json = json;
    }
    protected void parseJSON(){

        JSONObject jsonObject=null;
        try {
            JSONArray jsonArray=new JSONArray(json);
            try {
                for(int i=0;i<jsonArray.length();i++)
                    jsonObject = jsonArray.getJSONObject(i);
            }catch (Exception e){
                System.out.println("Not Found");
            }
            if(jsonObject!=null){
                //group = jsonObject.getJSONArray(JSON_ARRAY);

                ids = new String[jsonArray.length()];
                names = new String[jsonArray.length()];
                admin_id = new String[jsonArray.length()];
                time = new String[jsonArray.length()];

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jo = jsonArray.getJSONObject(i);

                    ids[i] = jo.getString(KEY_ID);
                    names[i] = jo.getString(KEY_NAME);
                    admin_id[i] = jo.getString(KEY_ADMIN_ID);
                    time[i] = jo.getString(KEY_TIME);
                }}
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
