package com.app.Managers;


import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;


public class Client {

    private static String url_person = "http://10.4.41.38:8080/person";
    private static String url_user = "http://10.4.41.38:8080/user";
    private static String url_tag = "http://10.4.41.38:8080/tag";
    private static String url_notification = "http://10.4.41.38:8080/notification";
    private static String url_landmark = "http://10.4.41.38:8080/landmark";
    private static String url_admin = "http://10.4.41.38:8080/admin";
    private static String url_additional_info = "http://10.4.41.38:8080/additional_info";

    private static int doPostRequestJson(JSONObject json, String url) throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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

    private static String GetPasswordMatchRequest(String url, String email, String password) throws IOException, JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL u = new URL("http://10.4.41.38:8080/person/userPasswordMatch?email="+email+"&introduced_password="+password);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setRequestMethod("GET");

        //conn.setRequestProperty("email",email);
        //conn.setRequestProperty("introduced_password", password);
        try {
            conn.connect();
            int status = conn.getResponseCode();

            switch (status) {
                case 200:
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    System.out.println(sb);
                    System.out.println(sb.substring(0,17));
                    Integer length = sb.length();
                    String aux = sb.substring(18,length-3);
                    return aux;
                case 404:
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    System.out.println(br2);
                    StringBuilder sb2 = new StringBuilder();
                    String line2;
                    while ((line2 = br2.readLine()) != null) {
                        sb2.append(line2+"\n");
                    }
                    br2.close();
                    System.out.println(sb2);
                    System.out.println(sb2.substring(0,17));
                    Integer length2 = sb2.length();
                    String aux2 = sb2.substring(18,length2-3);
                    return aux2;
            }

        } catch (IOException e) {
            System.out.println("hola");
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static JSONArray doGetRequest(String url, JSONObject json_parameters) throws IOException, JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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

    private static int doDeleteRequestJSON(JSONObject object, String url) throws IOException {
        URL u = new URL(url);
        HttpURLConnection  conn = (HttpURLConnection) u.openConnection();
        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestProperty("Accept","application/json");
        conn.setRequestMethod("DELETE");
        try {
            conn.connect();
            int status = conn.getResponseCode();
            return status;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int doPutRequestJSON(JSONObject change, String url) throws IOException {
        URL u = new URL(url);
        HttpURLConnection  conn = (HttpURLConnection) u.openConnection();
        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestProperty("Accept","application/json");
        conn.setRequestMethod("PUT");
        byte[] outputBytes = "{'value': 7.5}".getBytes("UTF-8");
        OutputStream os = conn.getOutputStream();
        os.write(change.toString().getBytes());
        os.close();
        try {
            conn.connect();
            int status = conn.getResponseCode();
            return status;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void CreateUser(String name,Integer phone_num, String email, String password) throws IOException, JSONException {
        JSONObject json_person = new JSONObject();
        json_person.put("name", name);
        json_person.put("phone_num", phone_num);
        json_person.put("email", email);
        json_person.put("password", password);
        json_person.put("token","String");
        json_person.put("landmark",JSONObject.NULL);

        JSONObject json_user = new JSONObject(); //no funciona el user pero person si
        json_user.put("email", email);
        json_user.put("last_coordinate_x", 0.0);
        json_user.put("last_coordinate_y", 0.0);
        json_user.put("last_coordinate_z", 0.0);
        json_user.put("person",json_person);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doPostRequestJson(json_person,url_person);
        doPostRequestJson(json_user, url_user);
    }

    public static boolean deleteUser(String email, String introduced_password) throws Exception {
        boolean authorised = userPasswordMatch(email,introduced_password);
        if (authorised) {
            JSONObject json_user = new JSONObject();
            json_user.put("email",email);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            doDeleteRequestJSON(json_user,url_user);
        }
        return authorised;
    }

    public static Boolean userPasswordMatch(String email,String introduced_password) throws Exception {
        JSONObject json_parameters = new JSONObject();
        json_parameters.put("email", email);
        json_parameters.put("introduced_password", introduced_password);
        String url = url_person + "/userPasswordMatch";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result = GetPasswordMatchRequest(url,email, introduced_password);
        /*for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = (JSONObject) jsonArray.get(i);
            String login_success = (String) json.get("login_success");
            return login_success.equals("true");
        }*/
        return result.equals("true");
    }

    public static String getUserPassword(String email,String introduced_password) throws Exception {
        //unsecure, must be changed
        JSONObject json_parameters = new JSONObject();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        json_parameters.put("introduced_password", digest.digest(introduced_password.getBytes(StandardCharsets.UTF_8)));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        JSONArray jsonArray = doGetRequest(url_person,json_parameters);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = (JSONObject) jsonArray.get(i);
            String email_json = (String) json.get("email");
            if (email_json.equals(email)) return (String) json.get("password");
        }
        throw new Exception("user_not_found");
    }

    public static JSONArray getPersons() throws Exception {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return doGetRequest(url_person,null);
    }

    public static JSONObject getPerson(String email) throws Exception {
        JSONArray persons = getPersons();
        for (int i = 0; i < persons.length(); i++) {
            JSONObject json = (JSONObject) persons.get(i);
            String email_json = (String) json.get("email");
            if (email_json.equals(email)) return json;
        }
        throw new Exception("user_not_found");
    }

    public static void setUserLocation(String email, float coordinate_x, float coordinate_y, float coordinate_z) throws Exception {
        JSONObject json_parameters = new JSONObject();
        json_parameters.put("email",email);
        json_parameters.put("last_coordinate_x",coordinate_x);
        json_parameters.put("last_coordinate_y",coordinate_y);
        json_parameters.put("last_coordinate_z",coordinate_z);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        int status = doPutRequestJSON(json_parameters,url_user);
        /*for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = (JSONObject) jsonArray.get(i);
            String email_json = (String) json.get("email");
            if (email_json.equals(email)) ;
        }*/
    }

    public static Vector<Float> getUserLocation(String email) throws Exception {
        JSONObject json_parameters = new JSONObject();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        JSONArray jsonArray = doGetRequest(url_person,json_parameters);
        JSONObject user = new JSONObject();
        boolean found = false;
        for (int i = 0; i < jsonArray.length() && !found; i++) {
            JSONObject json = (JSONObject) jsonArray.get(i);
            String email_json = (String) json.get("email");
            if (email_json.equals(email)) {
                user = json;
                found = true;
            }
        }
        if (found) {
            Vector<Float> coordinates = new Vector<Float>(3);
            for (int i = 0; i < user.length(); ++i) {
                float coordinate;
                switch (i) {
                    case 0:
                        coordinate = (float) user.get("last_coordinate_x");
                        break;
                    case 1:
                        coordinate = (float) user.get("last_coordinate_y");
                        break;
                    case 2:
                        coordinate = (float) user.get("last_coordinate_z");
                        break;
                    default:
                        coordinate = (float) 0.0;
                        break;
                }
                coordinates.set(i, coordinate);
            }
            return coordinates;
        }
        else throw new Exception("user_not_found");
    }

    public static void createTag(String name, String description) throws Exception {
        JSONObject json_params = new JSONObject();
        json_params.put("name",name);
        json_params.put("description",description);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doPostRequestJson(json_params,url_tag);
    }

    public static void createLandmark(float coordinate_x, float coordinate_y, String description, String email, String tag_name) throws Exception {
        Random rand = new Random();
        JSONObject json_landmark = new JSONObject();
        int id = rand.nextInt(1000000);
        json_landmark.put("id",id);
        json_landmark.put("coordinate_x",coordinate_x);
        json_landmark.put("coordinate_y",coordinate_y);
        json_landmark.put("description",description);
        JSONObject creator = getPerson(email);
        json_landmark.put("creator",creator);
        JSONObject json_tag = new JSONObject();
        json_tag.put("name",tag_name);
        json_tag.put("description",description);
        json_landmark.put("tag",json_tag);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (doPostRequestJson(json_landmark,url_landmark) == 424) {
            createTag(tag_name,description);
            json_tag.put("name",tag_name);
            json_tag.put("description",description);
            json_landmark.put("tag",json_tag);
            doPostRequestJson(json_landmark,url_landmark);
        }
    }

    public static void createLandmarkPep(int id, float coordinate_x, float coordinate_y, String title,String desc ) throws Exception {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        JSONObject json_landmark = new JSONObject();
        JSONObject json_landmark_creator = new JSONObject();
        JSONObject json_landmark_tag = new JSONObject();

        json_landmark_creator.put("email", "string");
        json_landmark_creator.put("name", "string");
        json_landmark_creator.put("phone_num", 0);
        json_landmark_creator.put("password", "string");
        json_landmark_creator.put("token", "string");
        json_landmark_creator.put("landmark", JSONObject.NULL);

        json_landmark_tag.put("name", title);
        json_landmark_tag.put("description",desc);
        createTag(title, desc);

        json_landmark.put("id", id);
        json_landmark.put("coordinate_x",coordinate_x);
        json_landmark.put("coordinate_y",coordinate_y);
        json_landmark.put("description", desc);
        json_landmark.put("creator",json_landmark_creator);
        json_landmark.put("tag",json_landmark_tag);
        doPostRequestJson(json_landmark,url_landmark);

    }

    public static JSONArray getAllLandmarks() throws Exception {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return doGetRequest(url_landmark,null);
    }

    public static JSONArray getUserLandmarks(String email) throws Exception {
        JSONObject json_params = new JSONObject();
        json_params.put("email",email);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return doGetRequest(url_landmark,json_params);
    }

    public static void createNotification(String description,String email) throws Exception {

    }

    public static JSONArray getUserNotifications(String email) throws Exception {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        JSONArray notifications = doGetRequest(url_notification,null);
        JSONArray user_notifications = new JSONArray();
        for (int i = 0; i < notifications.length(); ++i) {
            JSONObject noti = (JSONObject) notifications.get(i);
            String email_json = (String) noti.get("email");
            if (email_json.equals(email)) {
                user_notifications.put(noti);
            }
        }
        return user_notifications;
    }

    public static boolean deleteUserNotification(String email, int id) throws Exception {
        JSONObject json_params = new JSONObject();
        json_params.put("id",id);
        json_params.put("email",email);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doDeleteRequestJSON(json_params,url_notification);
        return true;
    }

    public static void main(String[] args) throws Exception {
        //getUserPassword("a","a");
        //CreateUser("holahola",123,"aaaaaaadsa","asdasfdfa");
    }
}