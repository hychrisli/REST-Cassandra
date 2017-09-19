package cmpe.restapi.mapper;

import java.util.HashMap;
import java.util.Map;

public class JsonResponse extends HashMap<String, Object> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2577404131633745147L;
	
	public JsonResponse(){}
	
	public JsonResponse(String key, Object value){
		addPair(key, value);
	}
	
	public void addPair(String key, Object value){
		this.put(key, value);
	}
	
	public Map<String, Object> getResponseData() {
		return this;
	}
}
