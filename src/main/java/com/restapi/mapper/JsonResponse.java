package com.restapi.mapper;

import java.util.Map;

public class JsonResponse {
	
	private int status;
	private String statMsg;
	private Map<String, Object> responseData;
	
	public JsonResponse(int status, String statMsg, Map<String, Object> responseData){
		this.status = status;
		this.statMsg = statMsg;
		this.responseData = responseData;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatMsg() {
		return statMsg;
	}
	public void setStatMsg(String statMsg) {
		this.statMsg = statMsg;
	}
	public Map<String, Object> getResponseData() {
		return responseData;
	}
	public void setResponseData(Map<String, Object> responseData) {
		this.responseData = responseData;
	}
}
