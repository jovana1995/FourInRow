package rpcproducer;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JovanaStamenkovic
 */
public class HTTPRequests {
    public  JSONArray getAllData(String u) throws IOException
    {
         JSONArray array=new JSONArray();
         try {

		URL url = new URL(u/*"http://192.168.56.1:45455/api/Player"*/);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
                int responseCode = conn.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
                
               InputStream input=conn.getInputStream();
		
	        JSONParser parser = new JSONParser();
                
                array = (JSONArray)parser.parse(response.toString());
                System.out.println(array);
                }

		conn.disconnect();
               

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }
         catch (ParseException e) {

		e.printStackTrace();

	  }
         return array;
}

public  JSONObject GetData(String u) throws IOException
    {
         JSONObject array=new JSONObject();
         try {

		URL url = new URL(u);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
                int responseCode = conn.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
                
               InputStream input=conn.getInputStream();
		
	        JSONParser parser = new JSONParser();
                
                 array = (JSONObject)parser.parse(response.toString());
                 System.out.println(array);
                }

		conn.disconnect();
        
	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }
         catch (ParseException e) {

		e.printStackTrace();

	  }
         return array;
}
public void  Add(JSONObject obj, String u)
{
    try {

		URL url = new URL(u);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
                
                conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		conn.setRequestProperty("Content-Type", "application/json");

		String input = obj.toString();
                System.out.println(input);
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(input);
                wr.flush();
                 wr.close();
                
                /*
		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}*/

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	 }

}
public void  Update(String obj,String u)
{
    try {

		URL url = new URL(u);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
                
                conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		conn.setRequestProperty("Content-Type", "application/json");

		String input = obj;
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(input);
                wr.flush();
                wr.close();
                
                /*
		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}*/

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	 }

}
public void  Delete(String u)
{
    
         try {

		URL url = new URL(u);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("DELETE");
		conn.setRequestProperty("Accept", "application/json");
                int responseCode = conn.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			              
                
                }
		conn.disconnect();
               

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }
        
         

}
}