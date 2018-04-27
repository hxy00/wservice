package com.emt.common.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class JSONHelper {
    public static List<Map<String, Object>> JSONArrayToListMap(JSONArray array) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < array.length(); i++) {
            Map map = new HashMap();
            try {
                JSONObject json = array.getJSONObject(i);
                Iterator keys = json.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    String value = json.get(key).toString();
                    if (value.startsWith("{") && value.endsWith("}")) {
                        map.put(key, parserToMap(value));
                    } else {
                        map.put(key, value);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            list.add(map);
        }
        return list;
    }

    public static List<String> JSONArrayToList(JSONArray array) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < array.length(); i++) {
            try {
                list.add(array.get(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static Map parserToMap(String s) {
        Map map = new HashMap();
        try {
            JSONObject json = new JSONObject(s);
            Iterator keys = json.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                String value = json.get(key).toString();
                if (value.startsWith("{") && value.endsWith("}")) {
                    map.put(key, parserToMap(value));
                } else {
                    map.put(key, value);
                }

            }
        } catch (Exception e) {

        }
        return map;
    }
}
