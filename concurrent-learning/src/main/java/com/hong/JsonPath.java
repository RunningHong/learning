package com.hong;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.qunar.qa.robot.tools.JsonUtils;

import java.io.*;
import java.util.*;

/**
 * Created by guolong.su on 2017/3/16.
 */
public class JsonPath {

    private static List<String> result = new ArrayList<>();

    /**
     * 读取文件内容
     *
     * @param filePath
     * @return
     */
    public static void read(String filePath) {
        BufferedReader br = null;
        String line = "";

        try {
            // 根据文件路径创建缓冲输入流
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));

            int rowNum=1;
            // 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
            while ((line = br.readLine()) != null) {
                int idx = line.indexOf(",");
                if (idx != -1 && line.length() > idx + 1) {
                    String sysCode = line.substring(0, idx);
                    String jsonStr = line.substring(idx + 1).replace("\"{", "{").replace("}\"", "}").replace("\" {", "{").replace("} \"", "}").replace("\"\"", "\"");
                    if (!jsonStr.trim().equalsIgnoreCase("json")) {
//                        if(line.contains("SN01151114091206387413")){
//                            System.out.println("OK");
//                        }
                        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
                        parsePathInMap(sysCode, "", jsonObject);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                }
            }
        }
    }

    public static Map<String, Object> jsonMap = new HashMap<>();

    private final static String PathStr = "#";

    public static void parsePathInMap(String sysCode, String parentPath, Object obj) {
        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;
            Iterator<String> expectKeys = jsonObject.keySet().iterator();
            while (expectKeys.hasNext()) {
                String key = expectKeys.next();
                Object objTemp = jsonObject.get(key);
                String path = parentPath.trim().isEmpty() ? sysCode.trim() + PathStr + key.trim() : sysCode.trim() + PathStr + parentPath.trim() + PathStr + key.trim();
                if (objTemp instanceof JSONArray || objTemp instanceof JSONObject) {
                    parsePathInMap(sysCode, parentPath.isEmpty() ? key.trim() : parentPath + PathStr + key.trim(), objTemp);
                } else if (objTemp instanceof String) {
                    String objStr = (String) objTemp;
                    try {
                        JSONObject jsonObjectTmp = JSONObject.parseObject(objStr);
                        parsePathInMap(sysCode, path, jsonObjectTmp);
                    } catch (JSONException je) {
                        if (!jsonMap.containsKey(path) && !objStr.trim().isEmpty()) {
                            jsonMap.put(path, objStr);
                        }
                    }
                } else if (objTemp instanceof Integer) {
                    Integer objInt = (Integer) objTemp;
                    if (!jsonMap.containsKey(path)) {
                        jsonMap.put(path, objInt);
                    }
                } else if (objTemp != null) {
                    if (!jsonMap.containsKey(path)) {
                        jsonMap.put(path, objTemp.toString());
                    }
                }
            }
        } else if (obj instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) obj;

            if (!jsonArray.isEmpty() && jsonArray.size()==0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    Object objTmp = jsonArray.get(i);

                    if (objTmp instanceof Integer) {
                        if (!jsonMap.containsKey(sysCode.trim() + PathStr + parentPath)) {
                            jsonMap.put(sysCode.trim() + PathStr + parentPath, objTmp);
                        }
                    } else if (objTmp instanceof String) {
                        String objStr = (String) objTmp;
                        try {
                            JSONObject jsonObjectTmp = JSONObject.parseObject(objStr);
                            parsePathInMap(sysCode, parentPath, jsonObjectTmp);
                        } catch (JSONException je) {
                            if (!jsonMap.containsKey(sysCode.trim() + PathStr + parentPath)) {
                                jsonMap.put(sysCode.trim() + PathStr + parentPath, objStr);
                            }
                        }
                    } else if (objTmp instanceof JSONObject || objTmp instanceof JSONArray) {
                        parsePathInMap(sysCode, parentPath, objTmp);
                    } else {
                        System.out.println("---EXCEPTION------" + objTmp.toString());
                    }
                }
            }
        }
    }

    /**
     * 将内容回写到文件中
     *
     * @param filePath
     * @param content
     */

    public static void write(String filePath, String content, boolean isAppend) {
        BufferedWriter bw = null;

        try {
            // 根据文件路径创建缓冲输出流
            bw = new BufferedWriter(new FileWriter(filePath, isAppend));
            // 将内容写入文件中
            bw.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    bw = null;
                }
            }
        }
    }

    /**
     * 主方法
     */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        String filePath = "//Users/suguolong/Downloads/SysCodeJson_2020_02_09.csv"; // 文件路径
        String resultFilePath = filePath + ".result.txt"; // 文件路径
        JsonPath.read(filePath);
        JsonPath.write(resultFilePath, "sys_code\tjson", false);
        for (String key : jsonMap.keySet()) {
            write(resultFilePath, System.lineSeparator() + key + "\t" + jsonMap.get(key).toString(), true);
        }
        System.out.println("CostTime:::" + (System.currentTimeMillis() - startTime));
    }
}
