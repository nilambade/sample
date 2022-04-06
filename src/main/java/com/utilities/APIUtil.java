package com.utilities;

import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIUtil {

	public static RequestSpecification request;
	public static JsonObject reqBodyParam;
	public static Response response;

	public RequestSpecification getRequest(String baseURI) {
		reqBodyParam = new JsonObject();
		RestAssured.baseURI = baseURI;
		return request = RestAssured.given();
	}

	public RequestSpecification getMethod(String path) {
		return null;
		// TODO: Add get method
	}

	public Response PostMethod(String path) {
		response = request.post(path);
		return response;
	}

	public RequestSpecification addHeader(String key, String value) {
		return request.header(key, value);
	}

	public JsonObject addBodyParam(String property, String value) {
		reqBodyParam.addProperty(property, value);
		return reqBodyParam;
	}

	public RequestSpecification addBody(JsonObject body) {
		return request.body(body.toString());
	}

	@Test
	public void getApi() {
		RestAssured.baseURI = "https://shoppeobject.ribbondev.com";
		RequestSpecification req = RestAssured.given();
		// Response res = req.request(Method.GET,"/api/user/login");
		req.header("Content-Type", "text/plain");
		req.header("Accept-Charset", "UTF-8");
		req.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36");
		reqBodyParam = new JsonObject();
		// JsonObject reqBodyParam = new JsonObject();
		reqBodyParam.addProperty("username", "sahilk.ssh.ar.ma@gmail.com");
		reqBodyParam.addProperty("password", "ShopOn@1234");

		req.body(reqBodyParam.toString());
		Response res = req.post("/api/user/login");

		System.out.println(res.getStatusCode());
		System.out.println(res.prettyPrint());
		String token = res.jsonPath().get("content.ctoken");
		String uid = res.jsonPath().get("content.id");
		System.out.println(token);
		
		req = RestAssured.given();
		req.filters(new RequestLoggingFilter());
		req.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36");
		req.header("Content-Type", "text/plain");
		req.header("Accept-Charset", "UTF-8");
		req.header("uid", uid);
		req.header("ctoken", "eyJraWQiOiJiQ0w3U3VHbUFrSklMaFhLOWErRnFZYWZqdFVLMVByaldVUkYzMnoycTVBPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI5MWM5MTNlMS02ZGUwLTRkYTMtOTQ1Mi04NTYxNzdkNDFkMzQiLCJldmVudF9pZCI6IjAxMmY3ZmYxLWRkYzMtNDFkZC1hOGU1LTQ3YzE0MjA3ODMzMCIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE2MDI5MzI1MTQsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX1VTaTFoaVNZQSIsImV4cCI6MTYwMjkzNjExNCwiaWF0IjoxNjAyOTMyNTE0LCJqdGkiOiI4ZDljNjU1Mi00N2Q0LTRiNzMtYWQ0Yy1lNDJkMjlmNDA5M2YiLCJjbGllbnRfaWQiOiIxZmlzbjJrYmh1ZzI0aGhhbms1ZnBibnNkZyIsInVzZXJuYW1lIjoiOTFjOTEzZTEtNmRlMC00ZGEzLTk0NTItODU2MTc3ZDQxZDM0In0.LrfGIiB7Dvb3bcsr8vDTmAi2HQ8D95Dn_V4yGt6qQBP9GR_FpaoCPQQ8X_m6osZpZAFHgZQomCULckEbdF6CbaFvl2n_HkHwNje5uL8H7G5ZOHUBcFfOFW2digxVuQWAzTQn-dw5s3a8cofyARu4dCm23pgb2gh_XoroqAEr9zwUa2EHRvpxVQMMCq_ReaBp1bsYfi8NYAm3gn1RaCeA8DafeKmqVUVEneZMR3IZ4D1CP4qZgJ-W_HCsSaL4WDB_szZwMnWF-XJCzdUv0xD9jV_yPPi69LGRgTGYk1XoyD0mD1x5_nOAM7c9ik4CNFS_nYp7zxKzJKyv_DCjLizLTg");
		reqBodyParam = new JsonObject();
		reqBodyParam.addProperty("vendorId", "5f5c72edfcb7a31ddc8e8422");
		req.body(reqBodyParam.toString());
		Response res1 = req.post("/api/product/getProductsByVendorId");
		System.out.println(res1.prettyPrint());

	}

}
