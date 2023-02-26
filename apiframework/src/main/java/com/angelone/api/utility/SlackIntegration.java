package com.angelone.api.utility;

import java.io.File;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;


public class SlackIntegration {

	  private static String urlSlackWebHook =
	  "https://hooks.slack.com/services/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";
	  private static String channelName = "spark_automationresult"; 
	  private static String
	  botUserOAuthAccessToken ="xoxb-877123502210-1779504179653-yRRt6bQRKEiMrdBjzKcfvnCZ";
	 
	  private static String
	  UserOAuthToken = "xoxp-877123502210-1249813409616-4430698941559-fe7a9ed43a9ee3fc36f0ef29cab46576";
	
	
public void sendFileToslack(String messages,String filelocation){
	
	try {
	String url = "https://slack.com/api/files.upload";
	HttpClient httpclient = HttpClientBuilder.create().disableContentCompression().build();
	HttpPost httppost = new HttpPost(url);

	MultipartEntityBuilder builder = MultipartEntityBuilder.create();
	
	builder.addBinaryBody("file",new File(filelocation));
	builder.addTextBody("channels", channelName);
	builder.addTextBody("token", UserOAuthToken);
	builder.addTextBody("media", "file");
	builder.addTextBody("initial_comment", messages);
	httppost.setEntity((builder.build()));
	
	HttpResponse execute = httpclient.execute(httppost);
	//int result = execute.getStatusLine().getStatusCode();

}catch(Exception e){
	e.printStackTrace();
}

}

public void sendResultToslack(String messages){
	
	try {
	String url = "https://slack.com/api/files.upload";
	HttpClient httpclient = HttpClientBuilder.create().disableContentCompression().build();
	HttpPost httppost = new HttpPost(url);

	MultipartEntityBuilder builder = MultipartEntityBuilder.create();
	
//	builder.addBinaryBody("file",new File(filelocation));
	builder.addTextBody("channels", channelName);
	builder.addTextBody("token", UserOAuthToken);
	builder.addTextBody("media", "file");
	builder.addTextBody("initial_comment", messages);
	httppost.setEntity((builder.build()));
	
	HttpResponse execute = httpclient.execute(httppost);
	//int result = execute.getStatusLine().getStatusCode();

}catch(Exception e){
	e.printStackTrace();
}

}
}
