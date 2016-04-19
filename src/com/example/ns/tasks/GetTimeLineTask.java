package com.example.ns.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ns.R;
import com.example.ns.model.Model;
import com.example.ns.model.Model.Timeline;
import com.example.ns.model.Tweet;
import com.example.ns.model.User;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import adapter.TweetAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Spannable;
import android.util.Log;

public class GetTimeLineTask extends AsyncTask<String, Void, ArrayList<Tweet>> {
	private Model model = Model.getInstance();
	private OAuthConsumer consumer = model.getConsumer();
	private Timeline timeline;
	
	public GetTimeLineTask(Timeline timeline) {
		this.timeline = timeline;
	}

	@Override
	protected ArrayList<Tweet> doInBackground(String... params) {
		try {
			String header = "";

			// request url
			String url = "";
			switch (timeline) {
			case HOME:
				url = "https://api.twitter.com/1.1/statuses/home_timeline.json";
				Log.d("Timeline:", "HOME");
				break;
			case USER:
				url = "https://api.twitter.com/1.1/statuses/user_timeline.json";
				Log.d("Timeline:", "USER");
				break;
			case MENTION:
				url = "https://api.twitter.com/1.1/statuses/mentions_timeline.json";
				Log.d("Timeline:","MENTION");
				break;
			}

			// setting up the request
			Log.d("Timeline request", "Setting up the request");
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);

			// setting the headers

			// sign
			System.out.println(consumer.getConsumerKey());
			consumer.sign(httpGet);

			HttpResponse response = httpclient.execute(httpGet);

			// Get the response content
			String line = "";
			StringBuilder contentBuilder = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			while ((line = rd.readLine()) != null) {
				contentBuilder.append(line);
			}
			String content = contentBuilder.toString();
			Log.d("Response Timeline get", content);
			
			//json
			JSONArray a = new JSONArray(content);
			ArrayList<Tweet> tweets = new ArrayList<Tweet>();
			for(int i = 0;i<a.length();i++){
				JSONObject o = (JSONObject) a.get(i);
				
				//getting the user
				JSONObject u = o.getJSONObject("user");
				String name = u.getString("name");
				String screen_name = u.getString("screen_name");
				String id_strU = u.getString("id_str");
				String description = u.getString("description");
				String urlU = u.getString("url");
				String image_url = u.getString("profile_image_url");
				//get the bitmap TODO
				User user = new User(name, screen_name, image_url, id_strU, description,urlU);
				LoadImageFromUrl t = new LoadImageFromUrl(user,model.getContext());
				t.execute();
				//getting the tweet
				String id_strO = o.getString("id_str");
				String created_at = o.getString("created_at");
				String text = o.getString("text");
				int favorite_count = o.getInt("favorite_count");
				int retweet_count = o.getInt("retweet_count");
				
				
				Tweet tweet = new Tweet(id_strO, created_at, text, favorite_count, retweet_count, user);
				Log.d("Tweet "+i,tweet.toString());
				tweets.add(tweet);
			}
			
			return tweets;
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	protected void onPostExecute(ArrayList<Tweet> result) {
		super.onPostExecute(result);
		model.setTimeline(timeline,result);
		model.update();
	}

}
