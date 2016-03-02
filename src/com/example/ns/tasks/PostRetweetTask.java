package com.example.ns.tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import oauth.signpost.OAuthConsumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class PostRetweetTask extends AsyncTask<String, Void, String> {
	private OAuthConsumer consumer;
	private String id_str;

	public PostRetweetTask(OAuthConsumer consumer, String id_str) {
		this.consumer = consumer;
		this.id_str = id_str;
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			// request url
			String url = "https://api.twitter.com/1.1/statuses/retweet/"+id_str+".json";

			// setting up the request
			Log.d("Post retweet", "Setting up the request");
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			// sign
			consumer.sign(httpPost);

			// Get the response content
			HttpResponse response = httpclient.execute(httpPost);
			String line = "";
			StringBuilder contentBuilder = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			while ((line = rd.readLine()) != null) {
				contentBuilder.append(line);
			}
			String content = contentBuilder.toString();
			Log.d("Response Retweet post", content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
