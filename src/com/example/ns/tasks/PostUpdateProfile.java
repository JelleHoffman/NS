package com.example.ns.tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import oauth.signpost.OAuthConsumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.ns.model.Model;
import com.example.ns.model.User;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class PostUpdateProfile extends AsyncTask<Void, Void, String> {
	private Model model = Model.getInstance();
	private OAuthConsumer consumer = model.getConsumer();
	private User user;

	public PostUpdateProfile(User user) {
		this.user = user;
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
			// request url
			String url = "https://api.twitter.com/1.1/account/update_profile.json";

			// setting up the request
			Log.d("Post update profile", "Setting up the request");
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url + "?name="
					+ Uri.encode(user.getName()) + "&description="
					+ Uri.encode(user.getDescription()) + "&url="
					+ Uri.encode(user.getUrl()));

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
			Log.d("Response update profile post", content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		model.refresh();
		model.update();
	}
}
