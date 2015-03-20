package com.example.anomcaller;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.util.List;


public interface ClientFormation {
	
	
	DefaultHttpClient client = new DefaultHttpClient();
	HttpContext context = new BasicHttpContext(); 
	List<Cookie> cookies = client.getCookieStore().getCookies();
	
	
 

}
