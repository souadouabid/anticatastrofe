package com.app.Managers;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;


public class Client {

    static String url_person = "http://10.4.41.38:8080/person";
    static String url_user = "http://10.4.41.38:8080/user";

    private static int doPostRequestJson(JSONObject json, String url) throws IOException {
        URL u = new URL(url);
        HttpURLConnection  conn = (HttpURLConnection) u.openConnection();
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod("POST");
        conn.connect();

        byte[] outputBytes = "{'value': 7.5}".getBytes("UTF-8");
        OutputStream os = conn.getOutputStream();
        os.write(json.toString().getBytes());

        os.close();
        try {
            conn.connect();
            int status = conn.getResponseCode();
            return status;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static JSONArray doGetRequest(String url, JSONObject json_parameters) throws IOException, JSONException {
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setRequestMethod("GET");

        //parameters
        if(json_parameters != null) {
            Iterator<String> keys = json_parameters.keys();
            for (int i = 0; i < json_parameters.length(); i++) {
                String key = keys.next();
                String value = json_parameters.getString(key);
                conn.setRequestProperty(key, value);

            }
        }
        try {
            conn.connect();
            int status = conn.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    JSONArray jsonArray =  new JSONArray(sb.toString());
                    return jsonArray;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void CreateUser(String name,Integer phone_num, String email, String password) throws IOException, JSONException {
        JSONObject json_person = new JSONObject();
        json_person.put("name", name);
        json_person.put("phone_num", phone_num);
        json_person.put("email", email);
        json_person.put("password", password);

        JSONObject json_user = new JSONObject(); //no funciona el user pero person si
        json_user.put("email", email);
        json_user.put("last_coordinate_x", 0.0);
        json_user.put("last_coordinate_y", 0.0);
        json_user.put("last_coordinate_z", 0.0);

        doPostRequestJson(json_person,url_person);
        doPostRequestJson(json_user, url_user);
    }

    public static Boolean userPasswordMatch(String email,String introduced_password) throws Exception {
        JSONObject json_parameters = new JSONObject();
        json_parameters.put("introduced_password", introduced_password);

        String url = url_person + "/userPasswordMatch";
        JSONArray jsonArray = doGetRequest(url,json_parameters);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = (JSONObject) jsonArray.get(i);
            String email_json = (String) json.get("email");
            if (email_json.equals(email)) {
                String stored_password = (String) json.get("password");
                return stored_password.equals(introduced_password);
            }
        }
        throw new Exception("user_not_found");
    }

    public static String getUserPassword(String email,String introduced_password) throws Exception {
        //unsecure, must be changed
        JSONObject json_parameters = new JSONObject();
        json_parameters.put("introduced_password", introduced_password);

        JSONArray jsonArray = doGetRequest(url_person,json_parameters);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = (JSONObject) jsonArray.get(i);
            String email_json = (String) json.get("email");
            if (email_json.equals(email)) return (String) json.get("password");
        }
        throw new Exception("user_not_found");
    }

    public static JSONArray getPersons(String email,String introduced_password) throws Exception {
        return doGetRequest(url_person,null);
    }

    public static void main(String[] args) throws Exception {
        getUserPassword("a","a");
//        CreateUser("holahola",123,"aaaaaaadsa","asdasfdfa");
    }
}
