package com.app.Managers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Client {


    public static void CreateUser(String name,Integer phone_num, String email, String password) throws IOException, JSONException {
        JSONObject json = new JSONObject();
        json.put("name",name);
        json.put("phone_num",phone_num);
        json.put("email",email);
        json.put("password",password);
        System.out.println(json.toString());

//        StringEntity entity = new StringEntity(json,ContentType.APPLICATION_FORM_URLENCODED);///
//        HttpClient httpClient = HttpClientBuilder.create().build();
//        HttpPost request = new HttpPost("10.4.41.38:8080/person");
//        request.setEntity(entity);
//
//        HttpResponse response = httpClient.execute(request);
//        System.out.println(response.getStatusLine().getStatusCode());

        URL url = new URL("10.4.41.38:8080/person");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = json.toString();

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }

    public static String getUserPassword(String email) throws IOException, JSONException {
        URL url = new URL("10.4.41.38:8080/person");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        String msg = conn.getResponseMessage();
        System.out.println(msg);
        return "";
    }

    public static void main(String[] args) throws IOException, JSONException {
        CreateUser("a",3,"a3","a");
//        getUserPassword("a");
    }
}
