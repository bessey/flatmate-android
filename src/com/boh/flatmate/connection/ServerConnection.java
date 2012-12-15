package com.boh.flatmate.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.boh.flatmate.data.Flat_data;
import com.google.gson.Gson;

public class ServerConnection {

	private String auth_token = null;
	private Gson gson = new Gson();
	private String server = "http://whispering-plains-6470.herokuapp.com";

	//*****************************************user stuff*****************************************

	//register a user and create a User object with all the details
	//note: must have an email and password or this won't work
	public  void registerUser(User newUser, String password) {
		String userInfo = null;
		@SuppressWarnings("unused")
		String jsonResult;

		//setup http string for registering user, in same order as API
		userInfo = newUser.toHTTPString();
		userInfo += "&user[password]=" + password;

		jsonResult = putOrPost(server + "/users", userInfo, false);

		//Check JSON string:
		// System.out.println(jsonResult);

		//class containing all objects returned during registration
		//can be used to check against newUser, make sure everything is registered correctly
		//User reg = gson.fromJson(jsonResult, User.class);

		//Check parsed stoof
		//System.out.println(reg.email);
	}

	//login and receive auth_token
	public  String login (String uname, String pword) {
		String info = "email=" + uname + "&password=" + pword;
		//System.out.println(info);
		String jsonResult = putOrPost(server+"/tokens", info, false);
		if(jsonResult == "failed"){
			return jsonResult;
		}
		Login log = gson.fromJson(jsonResult, Login.class);

		auth_token = log.getToken();
		try{
			  // Create file 
			  FileWriter fstream = new FileWriter("UserAuthCode.txt");
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write("auth_token");
			  //Close the output stream
			  out.close();
		}catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
		}
		return auth_token;
	}

	//set authentication code
	public  void setAuth(String at) {
		auth_token = at;
	}

	//logout by deleting auth_token from server
	public  void logout() {
		delete(server+"/tokens/" + auth_token);
	}

	//delete user by id
	public  void deleteUser(String id) {
		delete(server + "/users/" + id);
	}

	//get list of all users
	public  User[] getUserList() {
		System.out.println(server+"/users");
		return gson.fromJson(get(server + "/users"), User[].class);
	}

	//get a single user by id
	public  User getSingleUser(String id) {
		return gson.fromJson(get(server + "/users/" + id), User.class);
	}

	//edit user given by user
	public  void updateUser(User user) {
		String userInfo = user.toHTTPString();
		//System.out.println(userInfo);
		putOrPost(server + "/users/" + user.getId(), userInfo, true);
	}

	//change password, note: not 100% sure this is how to do it
	public  void changePassword(int id, String password) {
		putOrPost(server + "/users/" + id, "user[password]=" + password, true);
	}

	//*****************************************flat stuff*****************************************

	//get a single flat by id
	public  Flat_data getFlat(int flat_id) {
		return gson.fromJson(get(server + "/flats/" + flat_id), Flat_data.class);
	}

	//get whole list of flats
	public  Flat_data[] getFlats() {
		return gson.fromJson(get(server + "/flats"), Flat_data[].class);
	}

	//*****************************************message stuff*****************************************

	//get all messages for flat with given id
	public  Message[] getFlatMessages(int flat_id) {
		return gson.fromJson(get(server + "/flats/" + flat_id + "/messages"), Message[].class);
	}

	//get all message for user with given id
	public  Message[] getUserMessages(int user_id) {
		Message[] messages = gson.fromJson(get(server + "/users/" + user_id + "/messages"), Message[].class);
		for (Message m : messages) {
			//put(server + "/users/" + m.g)
		}
		return messages;
	}

	//delete a message with given flat and message ids
	public  void deleteFlatMessage(int flat_id, int message_id) {
		delete(server + "/flats/" + flat_id + "/messages/" + message_id);
	}

	//send message m
	public  void postMessage(Message m) {
		if (m.getTo_id() > 0 && m.getFlat_id() > 0) {
			//HAZ TEMPER TANTRUM
		} else if (m.getTo_id() > 0) {
			String messageDetails = "message[from_id]=" + m.getFrom_id();
			messageDetails += "&message[to_id]=" + m.getTo_id();
			messageDetails += "&message[contents]=" + m.getContents();
			putOrPost(server + "/users/" + m.getTo_id() + "/messages", messageDetails, false);
		} else if (m.getFlat_id() > 0) {
			String messageDetails = "message[from_id]=" + m.getFrom_id();
			messageDetails += "&message[flat_id]=" + m.getFlat_id();
			messageDetails += "&message[contents]=" + m.getContents();
			putOrPost(server + "/flats/" + m.getTo_id() + "/messages", messageDetails, false);
		}
	}

	//*****************************************shopping stuff*****************************************

	//add item to flats shopping list given by item
	public void addItem(ShopItem item) {
		String itemInfo = item.toHTTPString();
		String jsonResult = putOrPost(server + "/flats/" + item.getFlat_Id() + "/shop_items/", itemInfo, false);
	}
	
	//update item with given item
	public void updateItem(ShopItem item) {
		String itemInfo = item.toHTTPString();
		putOrPost(server + "/flats/" + item.getFlat_Id() + "/shop_items/" + item.getId(), itemInfo, true);
	}
	
	//delete item given by flat id (fid) and item id (iid)
	public void deleteItem(String fid, String iid) {
		delete(server + "/flats/" + fid + "/shop_items/" + iid);
	}
	
	//get all shopping items for flat with given id
	public  ShopItem[] getFlatShoppingList(int flat_id) {
		return gson.fromJson(get(server + "/flats/" + flat_id + "/shop_items"), ShopItem[].class);		
	}

	//*****************************************connection stuff*****************************************

	//put/post to server
	//note: true is put, false is post
	private  String putOrPost(String targetURL, String urlParameters, boolean put) {
		if (auth_token != null) targetURL += "?auth_token=" + auth_token;
		URL url;
		HttpURLConnection connection = null;  
		try {
			//Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection)url.openConnection();
			if (put) connection.setRequestMethod("PUT");
			else connection.setRequestMethod("POST");
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
			wr.writeBytes(urlParameters);
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
	private  String get(String targetURL) {
		System.out.println("Get Command:");
		URL url;
		HttpURLConnection connection = null; 
		if (auth_token != null) targetURL += "?auth_token=" + auth_token;
		try {
			System.out.println(targetURL);
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
			System.out.println();
			return response.toString();
		} catch (Exception e) {
			System.err.println("Problem with GET request " + e);
			return null;
		} finally {
			if(connection != null) {
				connection.disconnect(); 
			}
		}
	}

	//delete from server
	private  void delete(String targetURL) {
		System.out.println("Delete Command:");
		if (auth_token != null) targetURL += "?auth_token=" + auth_token;
		URL url;
		System.out.println(targetURL);
		try {
			url = new URL(targetURL);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty(
					"Content-Type", "application/x-www-form-urlencoded" );
			httpCon.setRequestMethod("DELETE");
			httpCon.connect();
			int responseCode = httpCon.getResponseCode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
	}

}
