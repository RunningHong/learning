package com.hong.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Iterator;
import java.util.Set;

/**
 * @author create by hongzh.zhang on 2020-12-03
 */
public class JsonPhase {

    static String json = "";

    public static void main(String[] args) {

        // String 转 Json对象
        JSONObject jsonObject = JSONObject.parseObject(json);

        // 遍历key
        Set<String> keySet = jsonObject.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            JSONObject valueJsonObject = jsonObject.getJSONObject(key);
            String cardCode = valueJsonObject.getString("cardCode");
            String cardName = valueJsonObject.getString("cardName");

            JSONArray privilegeList = valueJsonObject.getJSONArray("privilegeList");
            for (int i = 0; i < privilegeList.size(); i++) {
                JSONObject jsonObject2 = JSONObject.parseObject(privilegeList.get(i).toString());
                String code = jsonObject2.getString("code");

                System.out.println(cardCode.trim() + "\t" + cardName.trim() + "\t" + code.trim());
            }
        }



    }


}
