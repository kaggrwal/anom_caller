package com.example.anomcaller;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class pass_Parameters {
	
   String link;
   BasicNameValuePair[] parameters;
   
    pass_Parameters(String link,
    BasicNameValuePair[] params) {
        this.link = link;
        this.parameters = params;
        
    }
}

public class PostRequest extends AsyncTask<pass_Parameters, Integer, String> implements ClientFormation {

	HttpPost post = null;
	HttpEntity entity = null;
	List <NameValuePair> parameters = new ArrayList <NameValuePair>();
	String Result = null,content_text;
	Connectivity_check connectivity_check;
	//String Content = null;
	Context cncheck ;
	
PostRequest(Context context){
	this.cncheck = context;
	connectivity_check = new Connectivity_check(cncheck);
}
	
public String GetContent() throws ParseException, IOException{
	
	
	return null;
}
	
	
public String PostExecution(String command){
	
	
	return this.Result;}


//{android.os.Debug.waitForDebugger();}

@Override
protected String doInBackground(pass_Parameters... params) {
	
	//{android.os.Debug.waitForDebugger();}
	
	String link = params[0].link;
	BasicNameValuePair[] pair = params[0].parameters;
	
	try{
		
	     post = new HttpPost(link);
	     for(int i = 0; i<pair.length;i++){
	    	 parameters.add(pair[i]);
	     }
	     
	    
	     post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));	
	     HttpResponse response = client.execute(post,context);
	     entity=response.getEntity();
	     //Log.i("login",EntityUtils.toString(entity)+"..."+response.getStatusLine() );
	     if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK ){
	    	 content_text = EntityUtils.toString(entity);
	    	 return "OK";
	     }
	     else{
	    	 return response.getStatusLine().toString();
	     }
	     
		
		}
		catch(Exception es){
			
			return "Exception"+es.getMessage().toString();
		}
}


@Override
protected void onPreExecute() {
	// TODO Auto-generated method stub

	if(connectivity_check.isConnectingToInternet()){
		Toast.makeText(cncheck, "connected", Toast.LENGTH_LONG).show();		
	}
	else{
		Toast.makeText(cncheck, "not___connected", Toast.LENGTH_LONG).show();	
	}
	
}

@Override
protected void onPostExecute(String result) {
	// TODO Auto-generated method stub
	this.Result = result;
}


}



