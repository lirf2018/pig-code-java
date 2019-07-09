package com.yufan.data.util;

import com.alibaba.fastjson.JSONObject;
import com.yufan.data.pojo.TbVisitBusiness;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建人: lirf
 * 创建时间:  2019/4/15 11:59
 * 功能介绍:
 */
public class CommonMathod {

    /**
     * 处理结果生成sql脚本values
     *
     * @param path
     * @param colValues 额外追加列字段值
     * @return
     */
    public static String parseLogText2SQL(String path, String[] colValues) {
        try {
            FileInputStream fis = new FileInputStream(path);
            InputStreamReader isw = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isw);// 把filewriter的写法写成FileOutputStream形式

            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                //追加列
                if (null != colValues && colValues.length > 0) {
                    for (int i = 0; i < colValues.length; i++) {
                        line = line + "`" + colValues[i];
                    }
                }
                System.out.println(line);
                line = line.replace("None", "");
                line = line.replace("'", "''").replace("`", "','");
                line = "('" + line + "')" + ",";
                sb.append(line);// 追加
            }
            /*
             * 把最后一个,改成;
             */
            line = sb.toString();
            /**
             * 为了防止文本为空 eg.part-m-0000的文本是空文本
             */
            if (line == null || line.trim().isEmpty()) {
                return null;
            }
            line = line.substring(0, line.length() - 1) + ";";
            return line;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 处理结果生成sql脚本对象(类中属性顺序必须得与日志结果顺序一致,并且对应)
     *
     * @param path
     * @param colValues    额外追加列字段值
     * @param colValuesMap 额外追加列字段map值
     * @return
     */
    public static List parseLogText2Object(String path, String[] colValues, Map<String, String> colValuesMap, Class<?> c) {
        try {
            FileInputStream fis = new FileInputStream(path);
            InputStreamReader isw = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isw);// 把filewriter的写法写成FileOutputStream形式

            List result = new ArrayList<>();
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                //追加列
                if (null != colValues && colValues.length > 0) {
                    for (int i = 0; i < colValues.length; i++) {
                        line = line + "`" + colValues[i];
                    }
                }
                String lineArray[] = line.split("`");
                Field[] field = c.getDeclaredFields(); //返回所有的属性
                JSONObject jsonObject = new JSONObject();
                for (int i = 1; i < field.length; i++) {
                    String fieldName = field[i].getName();
                    jsonObject.put(fieldName, StringUtils.isEmpty(colValuesMap.get(fieldName)) ? lineArray[(i - 1)] : colValuesMap.get(fieldName));
                }
                Object obj = JSONObject.toJavaObject(jsonObject, c);
                result.add(obj);
            }
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
