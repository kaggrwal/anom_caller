package com.example.anomcaller;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;



public class FileUpload extends AsyncTask<File, Integer, String>  implements ClientFormation {

	
	File file_to_sent = null;
	
	
	

	@Override
	protected String doInBackground(File... params) {
		// TODO Auto-generated method stub
		file_to_sent = params[0];
		
		 try {
				HttpPost httpPost = new HttpPost("http://site2sms.com/user/manage_sounds_upload_next.asp");
				
				String File_name = file_to_sent.getName();
				 MultipartEntity entityBuilder = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				 //ByteArrayOutputStream bos = new ByteArrayOutputStream();
				 entityBuilder.addPart("action",new StringBody("Upload_Audio"));
				 entityBuilder.addPart("txtName",new StringBody(File_name));
				 entityBuilder.addPart("file1", new FileBody(file_to_sent,"audio/mpeg"));
				 entityBuilder.addPart("filename",new StringBody(file_to_sent.getName()));

				 //entityBuilder.addPart("Submit",new StringBody("Upload Sound"));
				 httpPost.setEntity(entityBuilder);
				
				//	entityBuilder.addPart("filename", new StringBody(file_to_sent.getName()));
				
				 //byte[] data = bos.toByteArray();
				 
				 HttpResponse response = client.execute(httpPost,context);
				 
				 //
				 String respon = EntityUtils.toString(response.getEntity());
				 Log.e("upload", respon);
				 
				 return respon+"...."+response.getEntity().getContentType().toString();
				
				 } catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						return e.getMessage().toString();
					} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					return e.getMessage().toString();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					return e.getMessage().toString();
				}
	}
	
	
	}

	

	
		
	
		// TODO Auto-generated method stub
		
		
	
	

