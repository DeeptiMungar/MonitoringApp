package com.paysafe.monitoring.app.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONUtils {
	private static Logger logger = LoggerFactory.getLogger(JSONUtils.class);
	
	public static String mapObject(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonValue = "";
		try {
			jsonValue = mapper.writeValueAsString(obj);
		} catch (JsonGenerationException ex) {
			logger.error("JsonGenerationException exception is   " +ex.getLocalizedMessage(), ex);
		} catch (JsonMappingException ex) {
			logger.error("JsonMappingException exception is   " +ex.getLocalizedMessage(), ex);
		} catch (IOException ex) {
			logger.error("IOException exception is   " +ex.getLocalizedMessage(), ex);
		}
		return jsonValue;
	}
	
	public static Object covertToJSON(String jsonString){
		try {
	        return new JSONObject(jsonString);
	    } catch (JSONException jsonException) {
	        try {
	            return new JSONArray(jsonString);
	        } catch (JSONException jsonException2) {
	            return null;
	        }
	    }
	}
}
