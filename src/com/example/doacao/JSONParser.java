package com.example.doacao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParser() {

    }

    // function get json from url
    // by making HTTP POST or GET method
    public JSONObject makeHttpRequest(String url, String method,
            List<NameValuePair> params) {

        // Making HTTP request
        try {

            // check for request method
            if(method == "POST"){
                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            }else if(method == "GET"){
                // request method is GET
            	Log.d("CAIU NO","1");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                Log.d("CAIU NO","2");
                String paramString = URLEncodedUtils.format(params, "utf-8");
                Log.d("CAIU NO","3");
                url += "?" + paramString;
                Log.d("CAIU NO","4");
                HttpGet httpGet = new HttpGet(url);
                Log.d("chegou no",url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                Log.d("CAIU NO","5");
                HttpEntity httpEntity = httpResponse.getEntity();
                Log.d("CAIU NO","6");
                is = httpEntity.getContent();
            }           

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            int i=0;
            while((line = reader.readLine()) != null) 
            {
                sb.append(line + "\n");
                i++;
            }
            json = sb.toString();
            is.close();
            Log.d("ANOTACAO",json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
    	  try {
               jObj = new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
           } catch (JSONException e) {
               Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"] "+json);
           }

        // return JSON String
        return jObj;

    }
}