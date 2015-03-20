package com.example.anomcaller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;

import java.util.concurrent.ExecutionException;

public class StartupActivity extends Activity  {
	
	TextView tv = null;
	ImageView iv_captcha = null;
	EditText captcha_input = null;
	String Captcha_input = null;
	PostRequest login_request = null;
	Dialog captcha_dialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);
		 tv = (TextView) findViewById(R.id.textView);
		 
		
		
		 		
					BasicNameValuePair[] login_parameters = new BasicNameValuePair[2];
					login_parameters[0]= new BasicNameValuePair("username", "9467694091");
					login_parameters[1]= new BasicNameValuePair("password", "kagg2919");
					
					
					pass_Parameters parameters = new pass_Parameters("http://www.site2sms.com/auth.asp", login_parameters);
					login_request = new PostRequest(StartupActivity.this);
					try {
					String result_got = login_request.execute(parameters).get();
					
					
						if(result_got.equals("OK")/*login_request.PostExecution("login").equals("OK")*/){
							
							
							
							captcha_dialog = new Dialog(StartupActivity.this);
							captcha_dialog.setTitle("Captcha Verification");
							captcha_dialog.setContentView(R.layout.dialog_captcha);
							captcha_dialog.setCancelable(false);
							iv_captcha = (ImageView) captcha_dialog.findViewById(R.id.imageView_captcha);
							captcha_input = (EditText) captcha_dialog.findViewById(R.id.editText_CaptchaEntry);
							Button proceed = (Button) captcha_dialog.findViewById(R.id.button_captchaProceed);
							GetRequest verification_request = new GetRequest();
							Log.d("login", "clear");
							
							if(verification_request.execute("http://site2sms.com/security/captcha.asp","image").get().equals("OK")){
							
								captcha_dialog.show();	
								
								
								iv_captcha.setImageBitmap(verification_request.bitmap);
								
							}
							
							else{
								
								tv.setText("Unable to connect");
								
							}
							
							proceed.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
								
									Captcha_input = captcha_input.getText().toString();
									if(Captcha_input.equals("")||(Captcha_input.length()<3)){
										Toast.makeText(getBaseContext(), "Wrong Input", Toast.LENGTH_SHORT).show();		
										
									}						
									
									else{
										
										captcha_dialog.dismiss();
										BasicNameValuePair[] captcha_parameters = new BasicNameValuePair[2];
										captcha_parameters[0]= new BasicNameValuePair("txtSource", "captcha");
										captcha_parameters[1] = new BasicNameValuePair("txtCaptchaCode", Captcha_input);
										
										
										if(login_request.PostExecution("http://www.site2sms.com/auth.asp").equals("OK")){
											
										Intent Main_activity = new Intent(StartupActivity.this,MainActivity.class);
										startActivity(Main_activity);
										finish();
	

										}
												
										
									}
								}
							});							
							
						}
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
			
	}		
				
			
			
		
	
		
	
	
	
	


	
	
}
