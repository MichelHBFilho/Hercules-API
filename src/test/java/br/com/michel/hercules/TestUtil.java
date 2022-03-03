package br.com.michel.hercules;

import java.net.URI;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class TestUtil {
	
	private static JSONParser parser = new JSONParser();
	
	public static String auth(String email, String password, MockMvc mmvc) throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", email);
		json.put("password", password);
		MvcResult result = mmvc.perform(MockMvcRequestBuilders.post(new URI("/api/auth"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString()))
			.andReturn();
		return (String) ((JSONObject)parser.parse(result.getResponse().getContentAsString())).get("token");
	}
	
}
