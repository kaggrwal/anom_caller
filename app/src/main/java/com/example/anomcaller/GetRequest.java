package com.example.anomcaller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

public class GetRequest extends AsyncTask<String, Void, String> implements ClientFormation  {
	
	

	private HttpGet get = null;
	private HttpEntity entity = null;
	private InputStream captcha_stream = null;
	 Bitmap bitmap;
	String content_text;
	 
	public String Get_execution(String link){
		return null;	
	}
	
	public String GetContent() throws ParseException, IOException{
		
		
		return null;
	}
	
	
	public void Get_image(){
		
		try {
		
        byte[] bytes = EntityUtils.toByteArray(entity);
		

        bitmap = BitmapFactory.decodeByteArray(bytes, 0,
                 bytes.length);
         
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		
		
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		String link = params[0];
		String requirement = params[1];
		try{
			get = new HttpGet(link);
			HttpResponse response = client.execute(get,context);
			entity=response.getEntity();
			if(requirement.equals("text")){
			
			//captcha_stream = response.getEntity().getContent();
			 if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK ){
		    	 content_text = EntityUtils.toString(entity);
				 return "OK";
		     }
		     else{
		    	 return response.getStatusLine().toString();
		     }
			}
			else if(requirement.equals("image")){
				Get_image();
				return "OK";
			}
			else{
				return response.getStatusLine().toString();
			}
		}
		
		
		catch(Exception getException){
		
			return "Exception in get :"+getException.getMessage().toString();
		}
		
	}
	
	
}
