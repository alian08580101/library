package com.jd.a6i.common;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonConverter {
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static <T> String convertObjectToJson(T object) {
        return getGson().toJson(object);
    }
    
    public static <T> String convertObjectToJson(T object,Type type){
    	return getGson().toJson(object, type);
    }

    public static <T> T convertJsonToObject(String json, TypeToken<T> typeToken) {
        return getGson().fromJson(json, typeToken.getType());
    }
    
    public static <T> T convertJsonToObject(String json,Class<T> clazz){
    	return getGson().fromJson(json, clazz);
    }
    
    public static <T> T convertJsonToObject(String json, Type type) {
        return getGson().fromJson(json, type);
    }

    public static <T> String convertMapToJson(Map<String,Object> map){
    	return getGson().toJson(map, Map.class);
    }
    
    private static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.enableComplexMapKeySerialization();
        return gsonBuilder.setExclusionStrategies(new ExclusionStrategyImpl()).create();
    }
    
    public static JSONObject jsonStringToJSONObject(String jsonString){
    	try {
			return new JSONObject(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static <T> List<T> convertObjectListFromJson(String json, TypeToken<T> token) {
        List<T> list = new ArrayList<T>();

        if (StringUtils.isBlank(json)) {
            return list;
        }

        try {
            list = getGson().fromJson(json, token.getType());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }
    
    /**
	 * 根据给定实体及属性标识注解返回属性值Map对象
     * @param <T>
	 * @param entity
	 * @param clazz
	 * @return
	 */
	public static <T> Map<String,Object> getFieldValueMap(T entity){
		Map<String, Object> fieldValueMap = new HashMap<String, Object>();
		Field[] fields = entity.getClass().getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();// 实体类属性名
			field.setAccessible(true);
			try {
				Object value = field.get(entity);// 实体类属性对应的值
				fieldValueMap.put(name, value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		return fieldValueMap;
	}
	
	/***
	 * @param json
	 * @param type must be array type
	 * @return
	 */
	public static <T> List<T> convertObjectListFromJson(String json, Type type) {
		if (StringUtils.isBlank(json)) {
            return null;
        }
        T[] tArray = getGson().fromJson(json, type);
        List<T> list = Arrays.asList(tArray);
        return list;
    }
}