package com.example.anomcaller;

import android.content.Context;

import org.apache.http.message.BasicNameValuePair;

public class Activity_call {
	
	
	
	
	private Context calling_Context;
	private GetRequest page_request = new GetRequest();
	private String TXT_GROUP = "0";
	private String TXT_CATEGORY = "47";
	private String value =null;
	private PostRequest to_call; 
	private PostRequest call2 ;
	
	Activity_call(Context context){
	
	this.calling_Context = context;
	to_call = new PostRequest(calling_Context);
	call2 = new PostRequest(calling_Context);
		
	}
	
	
	public void get_parsingPage(String file_name){
		try{
		page_request.execute("http://site2sms.com/user/send_sms_voice.asp","text");
		StringBuilder page = new StringBuilder(page_request.content_text);
		
		int index = page.indexOf(file_name);
		
		value = page.substring(index-8, index-2);
		
		//Document page = Jsoup.parse(page_request.GetContent());
		//Elements value = page.select("option:contains(file_name)");
		
		
		
		
		System.out.println(value.toString());
		}
		
		
		catch(Exception ex){
			
		}
		
	}
	
	
	public String call_initiate(String number){
		
		try{
		BasicNameValuePair[] initiation_pair = new BasicNameValuePair[4];
		initiation_pair[0]= new BasicNameValuePair("txtMobileNo", number);
		initiation_pair[1]= new BasicNameValuePair("txtAudio", value);
		initiation_pair[2]= new BasicNameValuePair("txtGroup", TXT_GROUP);
		initiation_pair[3] = new BasicNameValuePair("txtCategory", TXT_CATEGORY);
		
		
		//to_call.PostExecution("http://site2sms.com/user/send_sms_voice_confirm.asp", initiation_pair);
		pass_Parameters parameters = new pass_Parameters("http://site2sms.com/user/send_sms_voice_confirm.asp", initiation_pair);
		if(to_call.execute().get().equals("OK"));
		{
			BasicNameValuePair[] call_params = new BasicNameValuePair[3];
			call_params[0]= initiation_pair[0];
			call_params[1]= initiation_pair[1];
			call_params[2]=initiation_pair[2];
			
			parameters = new pass_Parameters("http://site2sms.com/user/send_sms_voice_next.asp",call_params);
			call2.execute(parameters);
			StringBuilder Respon=new StringBuilder(call2.GetContent());
			StringBuilder ne = new StringBuilder(Respon.substring(Respon.indexOf("FlashSuccess")));
			
			return ne.toString();
		}
		
		}
		catch(Exception es){
			return null;
		}
	}
	

}
