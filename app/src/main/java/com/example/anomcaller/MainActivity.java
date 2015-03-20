package com.example.anomcaller;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends Activity implements OnClickListener {

	private static final int REQ_CODE_PICK_SOUNDFILE = 0;
	Button upload,send = null;
	TextView fileName= null;
	EditText Number = null;
	private String file_name = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		upload = (Button) findViewById(R.id.button_Upload);
		fileName = (TextView) findViewById(R.id.textView_filename);
		send = (Button) findViewById(R.id.button_Send);
		Number = (EditText) findViewById(R.id.editText_ContactNo);
		send.setOnClickListener(this);
		upload.setOnClickListener(this);
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
		if(v.getId() == R.id.button_Upload){
			
			
			Intent intent;
			intent = new Intent();
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.setData(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
			intent.setType("audio/*");
			startActivityForResult(Intent.createChooser(intent, "Select Audio"), REQ_CODE_PICK_SOUNDFILE);
		}
		
		else if (v.getId()== R.id.button_Send){
			
			
			Activity_call toCall = new Activity_call(MainActivity.this);
			toCall.get_parsingPage(file_name);
			fileName.setText(toCall.call_initiate(Number.getText().toString()));
			
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_PICK_SOUNDFILE && resultCode == Activity.RESULT_OK){
	        if ((data != null) && (data.getData() != null)){
	        	
	            Uri audioFileUri = data.getData();
	            //fileName.setText(audioFileUri.getPath());
	            
	            Log.d("upload","done");
	            File to_be_send = null;
				//to_be_send = new File(getRealPathFromURI(audioFileUri));
				//file_name = to_be_send.getName();
	            //FileUpload upload = new FileUpload();
	           
                //String respom = upload.execute(to_be_send).get();
         
                	fileName.setText(getRealPathFromURI(audioFileUri));
						
					
				
	            }
	        }
	
	}

	
	public String getRealPathFromURI(Uri contentUri) 
	{
	     String[] proj = { MediaStore.Audio.Media.DATA };
	     Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
	     int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
	     cursor.moveToFirst();
	     return cursor.getString(column_index);
	}
}
