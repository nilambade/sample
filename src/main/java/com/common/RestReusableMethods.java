package com.common;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RestReusableMethods {
	public static JsonPath rawToJson(Response r) 
	{
		String s=r.asString();
		JsonPath j = new JsonPath(s);
		return j;
	}
}
