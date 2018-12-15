package com.example.raphael.pettransport.api;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class ApiConnection {
    private static final String USER_AGENT = "Mozilla/5.0";

    public static String URL_BASE = "http://raspahub.xyz:6924";

    public String sendGet(String url) throws Exception {
        return this.sendHttp(new URL(URL_BASE + url), "GET", null);
    }

    public String sendPost(String url,  Map<String, String> parametros) throws Exception {
        return this.sendHttp(new URL(URL_BASE + url), "POST", parametros);
    }

    public String sendGet(String url, Map<String, String> parametros) throws Exception {
        boolean isFirst = true;
        for (String key : parametros.keySet()) {
            url += isFirst ? "?" : "&";
            url += key + "=" + parametros.get(key);
            isFirst = false;
        }
        return  this.sendHttp(new URL(URL_BASE + url), "GET", null);
    }

    private String sendHttp(URL obj, String method, Map<String, String> parametros) throws IOException {

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("User-Agent", USER_AGENT);

        if (method.equals("GET")) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            String response = "";
            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(parametros));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=con.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";
            }
            return response;
        }
    }
    private String getPostDataString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public JSONArray toJSON(String jsonString) throws JSONException {
        return new JSONArray(jsonString);
    }
}
