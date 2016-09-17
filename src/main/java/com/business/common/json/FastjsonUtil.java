/**  
* Filename:    FastjsonUtil.java  
* Description:   
* Copyright:   Copyright (c)2014  
* Company:    easy 
* @author:     Issac 
* @version:    1.0  
* Create at:   2014-8-15 下午06:52:56  
*  
* Modification History:  
* Date           Author       Version      Description  
* ------------------------------------------------------------------  
* 2014-8-15    Issac       1.0        1.0 Version  
*/

package com.business.common.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import java.util.*;

/** 
 * @ClassName: FastjsonUtil 
 * @Description: 阿里Json转换公共方法
 * @author yutong
 * @date 2016-03-07 下午06:52:56 
 *  
 */
@SuppressWarnings("unchecked")
public class FastjsonUtil {
	
	private static SerializeConfig mapping = new SerializeConfig(); static { 
        mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss")); 
	} 

	/** 
	 * @Title: main 
	 * @Description: TODO 
	 * @param @param args  
	 * @return void 
	 */

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "123");
		map.put("b", "123");
		map.put("c", "123");
		System.out.println("json:"+map2json(map));
		Map<String, String> mapJsonMap = json2Map(map2json(map));
		for (String str : mapJsonMap.keySet()){
			System.out.println(str);
		}
		Map<String, Object> rsMap = jsonChangeMap(map2json(map));
		System.out.println("rsMap:"+rsMap);
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		System.out.println(serialize(list));
		List<String> listJson = deserializeList(serialize(list), String.class);
		System.out.println(listJson);
		
		rsMap.put("List",list);
		System.out.println("rsMap:"+mapToJson(rsMap));
		System.out.println("rsMap:"+jsonChangeMap(mapToJson(rsMap)));
	}
	
	/**
      * 将java类型的对象转换为JSON格式的字符串
      * @param object java类型的对象
      * @return JSON格式的字符串
    */
    public static <T> String serialize(T object) {
        return JSON.toJSONString(object);
    }
    
    /**
     * 将JSON格式的字符串转换成任意Java类型的对象或者java数组类型的对象，不包括java集合类型
     * @param json JSON格式的字符串
     * @param clz java类型或者java数组类型，不包括java集合类型
     * @return java类型的对象或者java数组类型的对象，不包括java集合类型的对象
     */
    public static <T> T deserialize(String json, Class<T> clz) {
       return JSON.parseObject(json, clz);
    }
	
    /**
    * 将JSON格式的字符串转换成任意Java类型的对象
    * @param json JSON格式的字符串
    * @param type 任意Java类型
    * @return 任意Java类型的对象
    */
    public static <T> T deserializeAny(String json, TypeReference<T> type) {
       return JSON.parseObject(json, type);
    }
   
    /**
    * 将JSON格式的字符串转换成任意Java类型的对象
    * @param json JSON格式的字符串
    * @param clz 任意Java类型
    * @return 任意Java类型的对象
    */
    public static <T> List<T> deserializeList(String json, Class<T> clz) {
       return JSON.parseArray(json, clz);
    }
	
    /**
    * 将JSON格式的字符串转换为List<T>类型的对象
    * @param json JSON格式的字符串
    * @param clz 指定泛型集合里面的T类型
    * @return List<T>类型的对象
    */
    public static <T> List<T> json2List(String json, Class<T> clz){ 
        //JSON array -> List 
	   return JSON.parseArray(json, clz); 
    } 
	
    /**
     * 将JSON格式的字符串转换为Map类型的对象
     * @param json JSON格式的字符串
     * @return Map<String, String>类型的对象
     */
	public static Map<String, String> json2Map(String json){ 
		//JSON -> Map 
		return (Map<String, String>)JSON.parse(json); 
	} 
	
	/**
	 * 将JSON格式的字符串转换为Map类型的对象
	 * @param json JSON格式的字符串
	 * @return
	 */
	public static Map<String, Object> jsonChangeMap(String json){
		return JSON.parseObject(json);
	}
	
	/**
     * 将数组格式的转换为String类型的对象
     * @param obj 数组格式的字符串
     * @return String 类型的对象
     */
	public static String array2json(Object[] obj){ 
		return JSON.toJSONString(obj,true); 
	} 
	
	/**
     * 将Map格式的转换为String类型的对象
     * @param map 格式的字符串
     * @return String类型的对象
     */
	public static String map2json(Map<String, String> map){ 
	    return JSON.toJSONString(map); 
	} 
	
	/**
     * 将Map格式的转换为String类型的对象
     * @param map 格式的字符串
     * @return String类型的对象
     */
	public static String mapToJson(Map<String, Object> map){
		return JSON.toJSONString(map);
	}
	
	  public static String toJson(Object src)
	  {
	    return JSON.toJSONString(src);
	  }

	  public static Map<String, Object> jsonToMap(String src) {
	    JSONObject jsonObject = JSONObject.parseObject(src);
	    return (Map<String, Object>)JSONObject.toJavaObject(jsonObject, Map.class);
	  }

	  public static List<Map<String, Object>> jsonToList(String src) {
	    JSONArray jsonArray = JSONObject.parseArray(src);
	    return (List<Map<String, Object>>)JSONObject.toJavaObject(jsonArray, List.class);
	  }

	  public static String toJsonString(StringBuffer src) {
	    return JSON.toJSONString(src.toString());
	  }

	  public static void beginArray(StringBuffer sJson) {
	    sJson.append("[");
	  }

	  public static void endArray(StringBuffer sJson) {
	    sJson.append("]");
	  }

	  public static void makeArray(StringBuffer sJson, StringBuffer sValue) {
	    sJson.append(sValue);
	  }

	  public static void makeArray(StringBuffer sJson, StringBuffer sValue, String sComma) {
	    sJson.append(",");
	    sJson.append(sValue);
	  }

	  public static void beginMap(StringBuffer sJson) {
	    sJson.append("{");
	  }

	  public static void beginMap(StringBuffer sJson, String sComma) {
	    sJson.append(",");
	    sJson.append("{");
	  }

	  public static void endMap(StringBuffer sJson) {
	    sJson.append("}");
	  }

	  public static void makePair(StringBuffer sJson, String sKey, int nValue) {
	    sJson.append("\"" + sKey + "\"");
	    sJson.append(":");
	    sJson.append(nValue);
	  }

	  public static void makePair(StringBuffer sJson, String sKey, int nValue, String sComma) {
	    sJson.append(",");
	    sJson.append("\"" + sKey + "\"");
	    sJson.append(":");
	    sJson.append(nValue);
	  }

	  public static void makePair(StringBuffer sJson, String sKey, long nValue, String sComma) {
	    sJson.append(",");
	    sJson.append("\"" + sKey + "\"");
	    sJson.append(":");
	    sJson.append(nValue);
	  }

	  public static void makePair(StringBuffer sJson, String sKey, Object oValue) {
	    sJson.append("\"" + sKey + "\"");
	    sJson.append(":");
	    sJson.append(JSON.toJSONString(oValue));
	  }

	  public static void makePair(StringBuffer sJson, String sKey, Object oValue, String sComma) {
	    sJson.append(",");
	    sJson.append("\"" + sKey + "\"");
	    sJson.append(":");
	    sJson.append(JSON.toJSONString(oValue));
	  }

	  public static void makePair(StringBuffer sJson, String sKey, String sValue) {
	    sJson.append("\"" + sKey + "\"");
	    sJson.append(":");
	    sJson.append(JSON.toJSONString(sValue));
	  }

	  public static void makePair(StringBuffer sJson, String sKey, String sValue, String sComma) {
	    sJson.append(",");
	    sJson.append("\"" + sKey + "\"");
	    sJson.append(":");
	    sJson.append(JSON.toJSONString(sValue));
	  }

	  public static void makePair(StringBuffer sJson, String sKey, StringBuffer sValue) {
	    sJson.append("\"" + sKey + "\"");
	    sJson.append(":");
	    sJson.append(sValue);
	  }

	  public static void makePair(StringBuffer sJson, String sKey, StringBuffer sValue, String sComma) {
	    sJson.append(",");
	    sJson.append("\"" + sKey + "\"");
	    sJson.append(":");
	    sJson.append(sValue);
	  }
}
