package com.jobo.httplib.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import com.google.gson.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonUtil {

    private static Gson mGson;

    public final static Gson getGson(){
        if(mGson==null){
            synchronized (GsonUtil.class){
                if(mGson==null)
                mGson = new GsonBuilder().
                        registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                            @Override
                            public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                                if (src == src.longValue())
                                    return new JsonPrimitive(src.longValue());
                                return new JsonPrimitive(src);
                            }
                        }).create();
            }

        }

        return mGson;
    }
    public final static String getJson(Object o){
        return getGson().toJson(o);
    }
    public final static  <T> T  getJson(Object o, Class<T> classOfT){

        return getGson().fromJson(getGson().toJson(o), classOfT);
    }
    public final static  <T> T  fromJson(String json, Class<T> classOfT){

        return getGson().fromJson(json, classOfT);
    }

    public final static  <T> List<T> getJsonList(Object o, Class<T> classOfT){
        List list = getGson().fromJson(getGson().toJson(o), List.class);
        List<T> ts = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            ts.add(getJson(list.get(i), classOfT));
        }
        return ts;
    }


    /**
     * 获取Assets路径下的文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName) {
        String json = "";
        try {
            AssetManager s = context.getAssets();
            try {
                InputStream is = s.open(fileName);
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                json = new String(buffer, "utf-8");
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }

    //将传入的is一行一行解析读取出来出来
    public static String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is, "GB2312");
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        reader.close();
        return buffer.toString();//把读取的数据返回
    }

    //把读取出来的json数据进行解析
    public static boolean handleCitiesResponse(String response) {
        try {

            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String ss = jsonObject.getString("");//""内填写你要读取的数据
            }

            return true;

        } catch (Exception e) {
            Log.d("handleCitiesResponse", e.toString());
        }
        return false;
    }
}
