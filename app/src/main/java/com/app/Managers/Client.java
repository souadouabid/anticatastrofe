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
import java.security.NoSuchAlgorithmException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
    private static String url_message = "http://10.4.41.38:8080/message";
    private static String url_message_cord = "http://10.4.41.38:8080/messageWithCoordinates";

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
                        sb.append(line + "\n");
                    }
                    br.close();
                    Integer length = sb.length();
                    String aux = sb.substring(18, length - 3);
                    return aux;
            }

        } catch (IOException e) {
            return "false";
        }

        return "false";
    }

    private static JSONArray doSpecialGetRequest(String url, JSONObject json_parameters) throws IOException, JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StringBuilder query = new StringBuilder("?");
        if(json_parameters != null) {
            Iterator<String> keys = json_parameters.keys();
            for (int i = 0; i < json_parameters.length(); i++) {
                String key = keys.next();
                String value = json_parameters.getString(key);
                query.append(String.format("%1$s=%2$s", key, value));
                if (i < json_parameters.length()-1) query.append("&");
                //conn.setRequestProperty(key, value);
            }
        }
        URL u;
        if (json_parameters != null) u = new URL(url + query);
        else u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setRequestMethod("GET");

        //parameters

        try {
            conn.connect();
            int status = conn.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder("[");
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    sb.append("]");
                    String version = sb.toString();
                    JSONArray jsonArray =  new JSONArray(version);
                    return jsonArray;
                default:
                    JSONArray jsonArray1 = new JSONArray();
                    JSONObject obj = new JSONObject();
                    obj.put("login_success","false"); //No hauria de ser aixi, pero es una tirita
                    jsonArray1.put(obj);
                    return jsonArray1;
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

    private static JSONArray doGetRequest(String url, JSONObject json_parameters) throws IOException, JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StringBuilder query = new StringBuilder("?");
        if(json_parameters != null) {
            Iterator<String> keys = json_parameters.keys();
            for (int i = 0; i < json_parameters.length(); i++) {
                String key = keys.next();
                String value = json_parameters.getString(key);
                query.append(String.format("%1$s=%2$s", key, value));
                if (i < json_parameters.length()-1) query.append("&");
                //conn.setRequestProperty(key, value);
            }
        }
        URL u;
        if (json_parameters != null) u = new URL(url + query);
        else u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setRequestMethod("GET");

        //parameters

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
                default:
                    JSONArray jsonArray1 = new JSONArray();
                    JSONObject obj = new JSONObject();
                    obj.put("login_success","false"); //No hauria de ser aixi, pero es una tirita
                    jsonArray1.put(obj);
                    return jsonArray1;
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

    private static int doDeleteRequestJSON(JSONObject object, String url) throws IOException, JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StringBuilder query = new StringBuilder("?");
        if(object != null) {
            Iterator<String> keys = object.keys();
            for (int i = 0; i < object.length(); i++) {
                String key = keys.next();
                String value = object.getString(key);
                query.append(String.format("%1$s=%2$s", key, value));
                if (i < object.length()-1) query.append("&");
                //conn.setRequestProperty(key, value);
            }
        }
        URL u;
        if (object != null) u = new URL(url + query);
        else u = new URL(url);
        HttpURLConnection  conn = (HttpURLConnection) u.openConnection();
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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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

    //POST /user
    public static int CreateUser(String name,Integer phone_num, String email, String password) throws IOException, JSONException, NoSuchAlgorithmException {
        JSONObject json_user = new JSONObject();
        json_user.put("email", email);
        json_user.put("last_coordinate_x", 0.0);
        json_user.put("last_coordinate_y", 0.0);
        json_user.put("name",name);
        json_user.put("phone_num",phone_num);
        json_user.put("password",password);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        json_user.put("token", Arrays.toString(digest.digest((email + name).getBytes(StandardCharsets.UTF_8))));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return doPostRequestJson(json_user, url_user);
    }

    //DELETE /user
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

    //GET /user
    public static JSONArray getUsers() throws IOException, JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return doGetRequest(url_user,null);
    }

    public static JSONObject getUser(String email) throws Exception {
        JSONArray users = getUsers();
        for (int i = 0; i < users.length(); ++i) {
            JSONObject json = (JSONObject) users.get(i);
            String email_json = (String) json.get("email");
            if (email_json.equals(email)) return json;
        }
        throw new Exception("user_not_found");
    }

    //PUT /user
    public static void updateUser(String email, float x, float y) throws JSONException, IOException {
        JSONObject params = new JSONObject();
        params.put("email",email);
        params.put("last_coordinate_x",x);
        params.put("last_coordinate_y",y);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doPutRequestJSON(params,url_user);
    }

    //GET /person/userPasswordMatch
    public static Boolean userPasswordMatch(String email,String introduced_password) throws Exception {
        JSONObject json_parameters = new JSONObject();
        json_parameters.put("email", email);
        json_parameters.put("introduced_password", introduced_password);
        String url = url_person + "/userPasswordMatch";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //String result = GetPasswordMatchRequest(url,email, introduced_password);
        JSONArray res_array = doSpecialGetRequest(url,json_parameters);
        if (res_array.length() > 0) {
            JSONObject res_json = (JSONObject) res_array.get(0);
            String result = (String) res_json.get("login_success");
            return result.equals("true");
        }
        throw new JSONException("Bad credentials");
    }

    //GET /person/persons
    public static JSONArray getPersons() throws Exception {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return doGetRequest(url_person+"/persons",null);
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

    //PUT /person/resetToken
    public static void updatePersonToken(String email, String token) throws IOException, JSONException {
        JSONObject params = new JSONObject();
        params.put("email",email);
        params.put("introduced_token",token);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doPutRequestJSON(params,url_person+"/resetToken");
    }

    //GET /person/getToken
    public static String getToken(String email, String password) throws JSONException, IOException {
        JSONObject query = new JSONObject();
        query.put("email",email);
        query.put("introduced_password",password);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        JSONArray out = doSpecialGetRequest(url_person+"/getToken",query);
        if (out.length() > 0) {
            JSONObject token_json = (JSONObject) out.get(0);
            return (String) token_json.get("token");
        }
        throw new JSONException("Bad credentials");
    }

    //POST /tag
    public static void createTag(String name, String description, int color) throws Exception {
        JSONObject json_params = new JSONObject();
        json_params.put("name",name);
        json_params.put("description",description);
        json_params.put("color", color);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doPostRequestJson(json_params,url_tag);
    }

    //GET /tag
    public static JSONArray getTags() throws IOException, JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return doGetRequest(url_tag,null);
    }

    public static JSONObject getTag(String tag) throws IOException, JSONException {
        JSONArray tags = getTags();
        for (int i = 0; i < tags.length(); i++) {
            JSONObject json = (JSONObject) tags.get(i);
            String email_json = (String) json.get("name");
            if (email_json.equals(tag)) return json;
        }
        throw new JSONException("tag_not_found");
    }

    //DELETE /tag
    public static void deleteTag(String name) throws IOException, JSONException {
        JSONObject params = new JSONObject();
        params.put("name",name);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doDeleteRequestJSON(params,url_tag);
    }

    //GET /notification
    public static JSONArray getNotifications() throws IOException, JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return doGetRequest(url_notification,null);
    }

    //Si només vols un GET d'una notificació, seria més fàcil donar la seva ID o el seu landmark_id?

    //POST /notification
    public static int createNotification(int landmark, String tag,String description) throws Exception {
        Random rand = new Random();
        int id = rand.nextInt(1000000);
        JSONObject json_not = new JSONObject();
        json_not.put("id",id);
        json_not.put("landmark_id",landmark);
        json_not.put("tag",tag);
        json_not.put("description",description);
        int status = doPostRequestJson(json_not,url_notification);
        switch (status) {
            case 200:
                return id;
            default:
                return -1;
        }
    }

    //DELETE /notification
    public static void deleteNotification(int id) throws IOException, JSONException {
        JSONObject params = new JSONObject();
        params.put("id",id);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doDeleteRequestJSON(params,url_notification);
    }

    //GET /landmark
    public static JSONArray getAllLandmarks() throws Exception {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return doGetRequest(url_landmark,null);
    }

    //POST /landmark
    public static int createLandmark(float x, float y, String tag, String email, String title, String desc) throws JSONException, IOException {
        JSONObject landmark = new JSONObject();
        Random rand = new Random();
        int id = rand.nextInt(1000000000);
        landmark.put("id",id);
        landmark.put("coordinate_x",x);
        landmark.put("coordinate_y",y);
        landmark.put("tag_name",tag);
        landmark.put("creator_email",email);
        landmark.put("title",title);
        landmark.put("description",desc);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        int status = doPostRequestJson(landmark,url_landmark);
        switch (status) {
            case 200:
                return id;
            default:
                return -1;
        }
    }

    //DELETE /landmark
    public static void deleteLandmark(int id) throws IOException, JSONException {
        JSONObject params = new JSONObject();
        params.put("landmark_id",id);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doDeleteRequestJSON(params,url_landmark);
    }

    //Ja no s'hauria de necessitar
    public static void createLandmarkPep(int id, float coordinate_x, float coordinate_y, String title, String desc, String email ) throws Exception {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JSONObject json_landmark = new JSONObject();
        JSONObject json_landmark_tag = getTag(email);

        if (json_landmark_tag.length() == 0) {
            createTag(email, "adreça d'interès", 0);
        }

        json_landmark.put("id", id);
        json_landmark.put("coordinate_x",coordinate_x);
        json_landmark.put("coordinate_y",coordinate_y);
        json_landmark.put("tag_name",email);
        json_landmark.put("creator_email",email);
        json_landmark.put("title", title);
        json_landmark.put("description", desc);
        doPostRequestJson(json_landmark,url_landmark);
    }

    //GET /message
    public static JSONArray getMessages() throws IOException, JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return doGetRequest(url_message,null);
    }

    //POST /message (WIP)
    public static int createMessage(String content, String date, boolean seen, String sender_em, String recipient_em) throws JSONException, IOException {
        JSONObject message = new JSONObject();
        message.put("content",content);
        message.put("date_sent",date);
        message.put("seen",seen); //Al meu entendre, en el POST hauria de ser sempre fals, no?
        Random rand = new Random();
        int id = rand.nextInt(1000000000);
        message.put("id",id);
        message.put("sender_email",sender_em);
        message.put("recipient_email",recipient_em);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        int status = doPostRequestJson(message,url_message);
        switch (status) {
            case 200:
                return id;
            default:
                return -1;
        }
    }

    //PUT /message (VERY WIP, for the future)
    public static void checkMessage(int id) throws JSONException, IOException {
        JSONObject params = new JSONObject();
        params.put("id",id);
        params.put("seen",true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doPutRequestJSON(params,url_message);
    }

    //DELETE /message
    public static void deleteMessage(int id) throws IOException, JSONException {
        JSONObject params = new JSONObject();
        params.put("id",id);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doDeleteRequestJSON(params,url_message);
    }

    //GET /messageWithCoordinates
    public static JSONArray getMessageWithCoordinates() throws IOException, JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return doGetRequest(url_message_cord,null);
    }

    //POST /messageWithCoordinates
    public static int createMessageWithCoordinates(String content, String date, boolean seen, String sender_em, String recipient_em, int landmark, String tag, String creator) throws JSONException, IOException {
        JSONObject message = new JSONObject();
        message.put("content",content);
        message.put("date_sent",date);
        message.put("seen",seen); //Al meu entendre, en el POST hauria de ser sempre fals, no?
        Random rand = new Random();
        int id = rand.nextInt(1000000000);
        message.put("id",id);
        message.put("sender_email",sender_em);
        message.put("recipient_email",recipient_em);
        message.put("landmark_id",landmark);
        message.put("tag",tag);
        message.put("creator_email",creator);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        int status = doPostRequestJson(message,url_message_cord);
        switch (status) {
            case 200:
                return id;
            default:
                return -1;
        }
    }

    //PUT /messageWithCoordinates (VERY WIP, for the future)
    public static void checkMessageWithCoordinates(int id) throws IOException, JSONException {
        JSONObject params = new JSONObject();
        params.put("id",id);
        params.put("seen",true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doPutRequestJSON(params,url_message_cord);
    }

    //DELETE /messageWithCoordinates
    public static void deleteMessageWithCoordinates(int id) throws IOException, JSONException {
        JSONObject params = new JSONObject();
        params.put("id",id);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doDeleteRequestJSON(params,url_message_cord);
    }

    //GET /admin
    public static JSONArray getAdmins() throws IOException, JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return doGetRequest(url_admin,null);
    }

    public static JSONObject getAdmin(String email) throws IOException, JSONException {
        JSONArray users = getAdmins();
        for (int i = 0; i < users.length(); ++i) {
            JSONObject json = (JSONObject) users.get(i);
            String email_json = (String) json.get("email");
            if (email_json.equals(email)) return json;
        }
        throw new JSONException("user_not_found");
    }

    //POST /admin
    public static void createAdmin(String name, String regionality, Integer phone_num, String email, String password) throws IOException, JSONException, NoSuchAlgorithmException {
        JSONObject json_user = new JSONObject();
        json_user.put("email", email);
        json_user.put("regionality",regionality);
        json_user.put("name",name);
        json_user.put("phone_num",phone_num);
        json_user.put("password",password);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        json_user.put("token",digest.digest((email+name).getBytes(StandardCharsets.UTF_8)));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doPostRequestJson(json_user, url_user);
    }

    //DELETE /admin
    public static void deleteAdmin(String email) throws JSONException, IOException {
        JSONObject json_admin = new JSONObject();
        json_admin.put("email",email);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doDeleteRequestJSON(json_admin,url_admin);
    }

    //GET /additional_info
    public static JSONArray getAdditionalInfos() throws IOException, JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return doGetRequest(url_additional_info,null);
    }

    public static JSONObject getAdditionalInfo(String email) throws JSONException, IOException {
        JSONArray users = getAdditionalInfos();
        for (int i = 0; i < users.length(); ++i) {
            JSONObject json = (JSONObject) users.get(i);
            String email_json = (String) json.get("email");
            if (email_json.equals(email)) return json;
        }
        throw new JSONException("user_not_found");
    }

    //POST /additional_info
    public static void createAdditionalInfo(String street, String city, String state, String postal_code, String country, String blood, String birth, String email) throws IOException, JSONException {
        JSONObject info = new JSONObject();
        //DateTimeFormatter
        info.put("street",street);
        info.put("city",city);
        info.put("state",state);
        info.put("postal_code",postal_code);
        info.put("country",country);
        info.put("path_profile_pic","string");
        info.put("blood_type",blood);
        info.put("birth_date","2022-01-04T17:22:47.233Z");
        info.put("email",email);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doPostRequestJson(info,url_additional_info);
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

    public static void deleteAdditionalInfo(String email) throws IOException, JSONException {
        JSONObject params = new JSONObject();
        params.put("email",email);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        doDeleteRequestJSON(params,url_additional_info);
    }

    public static void main(String[] args) throws Exception {
        //getUserPassword("a","a");
        //CreateUser("holahola",123,"aaaaaaadsa","asdasfdfa");
        //getPerson("abc");
    }
}