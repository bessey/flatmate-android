package com.Ballin_Octo_Hipster.flatmate.connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class ServerConnection {

	private String auth_token = null;
	private Gson gson = new Gson();
	private String server = "http://whispering-plains-6470.herokuapp.com";

	//register a user and create a User object with all the details
	public User registerUser(String uname, String pword, String flat_id, String geo_lat, String geo_long, String fname, String lname, String phone_num) {
		String userInfo = null;
		String jsonResult;
		if (uname == null || pword == null) {
			System.err.println("Missing username/password");
			return null;
		}

		//setup JSON string for registering user, in same order as API
		userInfo = "user[email]=" + uname + "&user[password]=" + pword;
		if (flat_id != null) userInfo += "&flat_id=" + flat_id;
		if (geo_lat != null) userInfo += "&geocode_lat=" + geo_lat;
		if (geo_long != null) userInfo += "&geocode_long=" + geo_long;
		if (fname != null) userInfo += "&first_name=" + fname;
		if (lname != null) userInfo += "&last_name=" + lname;
		if (phone_num != null) userInfo += "&phone_number=" + phone_num;

		jsonResult = post(server+"/users.json", userInfo);

		//Check JSON string:
		// System.out.println(jsonResult);

		//class containing all objects returned during registration
		User reg = gson.fromJson(jsonResult, User.class);

		//Check parsed stoof
		// System.out.println(reg.email);

		return reg;
	}

	//login and receive auth_token
	public String login (String uname, String pword) {
		String info = "email=" + uname + "&password=" + pword;
		System.out.println(info);
		String jsonResult = post(server+"/tokens.json", info);
		if(jsonResult == "failed"){
			return jsonResult;
		}
		Login log = gson.fromJson(jsonResult, Login.class);

		auth_token = log.getToken();
		return auth_token;
	}
	
	public void setAuth(String at) {
		auth_token = at;
	}
	
	//logout by deleting auth_token from server
	public void logout() {
		try {
			delete(server+"/tokens/" + auth_token + ".json");
		} catch (IOException e) {
			System.err.println("Problem logging out");
			e.printStackTrace();
		}
	}
	
	//TODO Still needs debugging
	public User[] getUserList() {
		return gson.fromJson(get(server+"/users.json"), User[].class);
	}

	//post to server
	private String post(String targetURL, String urlParameters)
	{
		if (auth_token != null) targetURL += "?auth_token=" + auth_token;
		URL url;
		HttpURLConnection connection = null;  
		try {
			//Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", 
					"application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length", "" + 
					Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");  

			connection.setUseCaches (false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			//Send request
			DataOutputStream wr = new DataOutputStream (
					connection.getOutputStream ());
			wr.writeBytes (urlParameters);
			wr.flush ();
			wr.close ();

			//Get Response	
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer(); 
			while((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();

		} catch (Exception e) {
			System.err.println("Problem with POST request:" + e);
			return "failed";

		} finally {

			if(connection != null) {
				connection.disconnect(); 
			}
		}
	}

	//get from server
	private String get(String targetURL) {
		URL url;
		HttpURLConnection connection = null; 
		if (auth_token != null) targetURL += "?auth_token=" + auth_token;
		try {
			//Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");

			//Get Response	
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer(); 
			while((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();
		} catch (Exception e) {
			System.err.println("Problem with GET request" + e);
			return null;
		} finally {
			if(connection != null) {
				connection.disconnect(); 
			}
		}
	}

	//delete from server
	private void delete(String location) throws IOException {
		URL url = new URL("http://www.example.com/resource");
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestProperty(
				"Content-Type", "application/x-www-form-urlencoded" );
		httpCon.setRequestMethod("DELETE");
		httpCon.connect();
	}
}
