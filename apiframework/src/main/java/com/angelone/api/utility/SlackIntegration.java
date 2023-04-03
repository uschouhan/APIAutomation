package com.angelone.api.utility;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;


public class SlackIntegration {
	
	         private static String urlSlackWebHook =
			  "https://hooks.slack.com/services/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";
			  private static String channelName = "spark-api-automation";
			 private static String
			  botUserOAuthAccessToken ="xoxb-877123502210-4878148251569-2KipBrxedVJiuM42zKqfkgra";
			 
			  private static String
			  UserOAuthToken = "xoxp-877123502210-1249813409616-4862703458165-fa40d029ec4322bc068ad52acca09c45";
	  		  
			 
	
public  void sendFileToslack(String messages,String filelocation){
	
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


public void sendExecutionStatusToSlack(String message) throws Exception {
	try {
		StringBuilder messageBuider = new StringBuilder();
		messageBuider.append(message);
		Payload payload = Payload.builder().channel(channelName).text(messageBuider.toString()).build();

		WebhookResponse webhookResponse = Slack.getInstance().send(urlSlackWebHook, payload);
		webhookResponse.getMessage();
	} catch (IOException e) {
		//System.out.println("Unexpected Error! WebHook:" + urlSlackWebHook);
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
