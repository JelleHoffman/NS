package com.example.ns.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.example.ns.model.User;

import android.os.AsyncTask;
import android.util.Log;

public class GetProfileTask extends AsyncTask<String, Void, User> {
	private OAuthConsumer consumer;

	public GetProfileTask(OAuthConsumer consumer) {
		this.consumer = consumer;
	}

	@Override
	protected User doInBackground(String... params) {
		try {
			// request url
			String url = "https://api.twitter.com/1.1/account/verify_credentials.json";

			// setting up the request
			Log.d("Profile request", "Setting up the request");
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);

			// sign
			consumer.sign(httpGet);

			// Get the response content
			HttpResponse response = httpclient.execute(httpGet);
			String line = "";
			StringBuilder contentBuilder = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			while ((line = rd.readLine()) != null) {
				contentBuilder.append(line);
			}
			String content = contentBuilder.toString();
			Log.d("Response Profile get", content);
			
			JSONObject o = new JSONObject(content);
			User user =  new User(
					o.getString("name"), 
					o.getString("screen_name"), 
					o.getString("profile_image_url"), 
					o.getString("id_str"), 
					o.getString("description"),
					o.getString("url")
					);
			
			return user;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
