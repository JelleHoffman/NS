package com.example.ns.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.ns.model.Model;
import com.example.ns.model.Tweet;
import com.example.ns.model.User;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SearchTask extends AsyncTask<String, Void, ArrayList<Tweet>> {
	private OAuthConsumer consumer;
	private String searchArgument;

	public SearchTask(String searchArgument, OAuthConsumer consumer) {
		this.searchArgument = searchArgument;
		this.consumer = consumer;
	}

	@Override
	protected ArrayList<Tweet> doInBackground(String... params) {
		String encoded = "";
		try {
			//encode the search
			encoded = URLEncoder.encode(searchArgument, "UTF-8");
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(
					"https://api.twitter.com/1.1/search/tweets.json?q="
							+ encoded);
			
			//setting the header
			consumer.sign(httpGet);

			// Get the response content
			HttpResponse response = client.execute(httpGet);
			String line = "";
			StringBuilder contentBuilder = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			while ((line = rd.readLine()) != null) {
				contentBuilder.append(line);
			}
			String content = contentBuilder.toString();
			Log.d("Response Search get", content);
			
			//json
			JSONObject o1 = new JSONObject(content);
			JSONArray a = o1.getJSONArray("statuses");
			ArrayList<Tweet> tweets = new ArrayList<Tweet>();
			for(int i = 0;i<a.length();i++){
				JSONObject o2 = (JSONObject) a.get(i);
				
				//getting the user
				JSONObject u = o2.getJSONObject("user");
				String name = u.getString("name");
				String screen_name = u.getString("screen_name");
				String id_strU = u.getString("id_str");
				String description = u.getString("description");
				String urlU = u.getString("url");
				String image_url = u.getString("profile_image_url");
				//get the bitmap TODO
				User user = new User(name, screen_name, image_url, id_strU, description,urlU);
				
				//getting the tweet
				String id_strO = o2.getString("id_str");
				String created_at = o2.getString("created_at");
				String text = o2.getString("text");
				int favorite_count = o2.getInt("favorite_count");
				int retweet_count = o2.getInt("retweet_count");
				
				
				Tweet tweet = new Tweet(id_strO, created_at, text, favorite_count, retweet_count, user);
				Log.d("Tweet "+i,tweet.toString());
				tweets.add(tweet);
			}
			
			return tweets;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
